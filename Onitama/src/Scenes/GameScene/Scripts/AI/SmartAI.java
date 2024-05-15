package Onitama.src.Scenes.GameScene.Scripts.AI;

import java.util.*;

import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Scripts.States.Action;
import Onitama.src.Scenes.GameScene.Scripts.States.State;

import static java.lang.Math.min;
import static java.lang.Math.max;

/**
 * SmartAI
 */
public class SmartAI extends AI {
    private static final int minusINF = Integer.MIN_VALUE;
    private static final int plusINF = Integer.MAX_VALUE;
    Action bestMove;
    Random random;
    int difficulty;
    int selfID;


    public SmartAI(int difficulty, int player) {
        this.random = new Random();
        this.difficulty = difficulty;
        this.selfID = player;
    }


    @Override
    public Action play() {
        int eval = minmax(GameScene.getGameState(), true, difficulty, 
                          minusINF, plusINF);
        System.err.println("Best score found: " + eval);
        return bestMove;
    }

    private int minmax(State config, boolean isMaximizing, 
                                       int depth, int alpha, int beta) {
        if (config.isGameOver() || depth == 0) 
            return heuristic(config, depth);

        int eval, maxEval, minEval;

        if (isMaximizing) {
            maxEval = minusINF;
            for (Action turn : possibleActions(config)) {
                eval = minmax(config.nextConfig(turn), false, 
                              depth-1, alpha, beta);
                if (depth == difficulty && eval > alpha)
                    bestMove = turn;
                maxEval = max(maxEval, eval);
                alpha = max(alpha, eval);
                if (beta <= alpha)
                    break;
            }
            return maxEval;
        } 
        
        else { // minimizing
            minEval = plusINF;
            for (Action turn : possibleActions(config)) {
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


    private int heuristic(State config, int depth) {
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

    private int pieceNumber(State config) {
        return config.allyPositions().size() - config.enemyPositions().size();
    }

    private int throneDistance(State config) {
        return distance(config.allyKing(), config.allyGoal()) - 
               distance(config.enemyGoal(), config.enemyKing());
    }

    /* End of evaluation methods */
}