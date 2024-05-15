package Onitama.src.Scenes.GameScene.Scripts.AI;

import java.util.*;

import static java.lang.Math.min;
import static java.lang.Math.max;

import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Scripts.GameConfiguration;
import Onitama.src.Scenes.GameScene.Scripts.Turn;

/**
 * SmartAI
 */
public class SmartAI extends AI {
    private static final int minusINF = Integer.MIN_VALUE;
    private static final int plusINF = Integer.MAX_VALUE;
    List<Turn> bestMoves;
    Random random;
    int difficulty;
    int selfID;


    public SmartAI(int difficulty, int player) {
        bestMoves = new ArrayList<>();
        this.random = new Random();
        this.difficulty = difficulty;
        this.selfID = player;
    }


    @Override
    public Turn play() {
        bestMoves.clear();
        int eval = minmax(GameScene.game, true, difficulty, 
                          minusINF, plusINF);
        System.err.println("Best score found: " + eval);
        return bestMoves.get(random.nextInt(bestMoves.size()));
    }

    private int minmax(GameConfiguration config, boolean isMaximizing, 
                                       int depth, int alpha, int beta) {
        if (config.isGameOver() || depth == 0) 
            return heuristic(config, depth);

        int eval, maxEval, minEval;

        if (isMaximizing) {
            maxEval = minusINF;
            for (Turn turn : possibleTurns(config)) {
                eval = minmax(config.nextConfig(turn), false, 
                              depth-1, alpha, beta);
                if (depth == difficulty)
                    if (eval >= maxEval) {
                        if (eval > maxEval)
                            bestMoves.clear();
                        bestMoves.add(turn);
                    }
                maxEval = max(maxEval, eval);
                alpha = max(alpha, eval);
                if (beta <= alpha)
                    break;
            }
            return maxEval;
        } 
        
        else { // minimizing
            minEval = plusINF;
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


    private int heuristic(GameConfiguration config, int depth) {
        int eval; // evaluation for AI

        if (config.isGameOver()) // AI lost
            eval = -(1000000 + depth);
        else // ran out of depth, give an estimate
            eval = pieceNumber(config) + throneDistance(config); 

        if (config.getCurrentPlayer() == selfID)
            return eval;
        else // enemy is playing, so return the opposite
            return -eval;
    }


    /* Methods to evaluate a state of the game  */

    private int pieceNumber(GameConfiguration config) {
        return config.allyPositions().size() - config.enemyPositions().size();
    }

    private int throneDistance(GameConfiguration config) {
        return distance(config.allyKing(), config.allyGoal()) - 
               distance(config.enemyGoal(), config.enemyKing());
    }

    /* End of evaluation methods */
}