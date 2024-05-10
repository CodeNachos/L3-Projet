package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Model.Card;
import Model.Engine;
import Model.GameConfiguration;
import Model.Piece;
import Model.Position;
import Model.Turn;
import static java.lang.Math.min;
import static java.lang.Math.max;
import static java.lang.Math.abs;

/**
 * SmartAI
 */
public class SmartAI implements Player {
    List<Turn> winners;
    Random random;
    Engine engine;
    int difficulty;
    int player;

    public SmartAI(Engine engine, int difficulty, int player) {
        this.winners = new ArrayList<>();
        this.random = new Random();
        this.engine = engine;
        this.difficulty = difficulty;
        this.player = player;
    }

    @Override
    public Turn play() {
        minmax(engine.getGameConfiguration(), true, difficulty, 
               Integer.MIN_VALUE, Integer.MAX_VALUE);
        return winners.get(random.nextInt(winners.size()));
    }

    private int minmax(GameConfiguration config, boolean isMaximizing, 
                                       int depth, int alpha, int beta) {
        if (config.gameOver() || depth == 0)
            return heuristic(config);

        int eval, maxEval, minEval;

        if (isMaximizing) {
            maxEval = Integer.MIN_VALUE;
            for (Turn turn : possibleTurns(config)) {
                eval = minmax(config.nextConfig(turn), false, 
                              depth-1, alpha, beta);
                // if at root, track winning moves
                if (depth == difficulty) { 
                    if (eval >= maxEval) {
                        if (eval > maxEval)
                            winners.clear();
                        winners.add(turn);
                    }
                }
                maxEval = max(maxEval, eval);
                alpha = max(alpha, eval);
                if (beta <= alpha)
                    break;
            }
            return maxEval;
        } 
        
        else { // minimizing
            minEval = Integer.MAX_VALUE;
            for (Turn turn : possibleTurns(config)) {
                eval = minmax(config.nextConfig(turn), true,
                              depth-1, alpha, beta);
                minEval = min(minEval, eval);
                beta = min(beta, eval);
                if (beta <= alpha)
                    break;
            }
            return minEval;
        }
    }

    private int heuristic(GameConfiguration config) {
        List<Position> allies = config.allyPositions();
        List<Position> enemies = config.enemyPositions(); 
        int pieceNumber = allies.size() - enemies.size();
        int throneDistance = distance(config.allyKing(), config.allyGoal()) - distance(config.enemyGoal(), config.enemyKing());
        int eval = pieceNumber + throneDistance; 
        if (config.getCurrentPlayer() == player)
            return eval;
        else
            return -eval;
    }

    private List<Turn> possibleTurns(GameConfiguration config) {
        List<Turn> result = new ArrayList<>();
        for (Card card : config.availableCards())
            for (Piece piece : config.allyPieces())
                for (Position position : config.possiblePositions(piece.getPosition(), card)) {
                    Piece move = new Piece(piece.getType(), position);
                    result.add(new Turn(card, piece, move));
                }
        return result;
    }

    private int distance(Position first, Position second) {
        return abs(first.getI() - second.getI()) + abs(first.getJ() - second.getJ());
    }
}