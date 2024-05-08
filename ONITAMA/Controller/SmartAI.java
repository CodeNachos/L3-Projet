package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Model.Engine;
import Model.GameConfiguration;
import Model.Turn;
import static java.lang.Math.min;
import static java.lang.Math.max;

/**
 * SmartAI
 */
public class SmartAI implements Player {
    List<Turn> winners;
    Random random;
    Engine engine;
    int difficulty;

    public SmartAI(Engine engine, int difficulty) {
        this.winners = new ArrayList<>();
        this.random = new Random();
        this.engine = engine;
        this.difficulty = difficulty;
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
        return 0;
    }

    private List<Turn> possibleTurns(GameConfiguration config) {
        return null;
    }

    //private int distance(Position first, Position second) {
    //    return abs(first.getI() - second.getI()) + abs(first.getJ() - second.getJ());
    //}
}