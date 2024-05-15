package Onitama.src.Scenes.GameScene.Scripts.AI;

import static java.lang.Math.min;
import static java.lang.Math.max;

import Engine.Structures.Vector2D;
import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Scripts.GameConfiguration;
import Onitama.src.Scenes.GameScene.Scripts.Turn;

/**
 * SmartAI
 */
public class SmartAI extends AI {
    private static final double minusINF = Double.NEGATIVE_INFINITY;
    private static final double plusINF = Double.POSITIVE_INFINITY;
    private static final Vector2D center = new Vector2D(2, 2);
    private static final int NB_METHOD = 4;
    private static final int PN = 0;
    private static final int KS = 0;
    private static final int TD = 0;
    private static final int CD = 0;

    Turn bestMove;
    int difficulty, selfID;
    double[] weights;


    public SmartAI(int difficulty, int player) {
        this.difficulty = difficulty;
        this.selfID = player;
        this.weights = new double[NB_METHOD];
        this.weights[PN] = 4;
        this.weights[KS] = 3;
        this.weights[TD] = 2; 
        this.weights[CD] = 1;
    }


    @Override
    public Turn play() {
        double eval = minmax(GameScene.game, true, difficulty, 
                             minusINF, plusINF);
        System.err.println("Best score found: " + eval);
        return bestMove;
    }

    private double minmax(GameConfiguration config, boolean isMaximizing, 
                                    int depth, double alpha, double beta) {
        if (config.isGameOver() || depth == 0) 
            return heuristic(config, depth);

        double eval, maxEval, minEval;

        if (isMaximizing) {
            maxEval = minusINF;
            for (Turn turn : possibleTurns(config)) {
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


    private double heuristic(GameConfiguration config, int depth) {
        double eval; // evaluation for AI

        if (config.isGameOver()) // AI lost
            eval = -(1000000 + depth);
        else // ran out of depth, give an estimate
            eval =  weights[PN] * pieceNumber(config) +
                    weights[KS] * kingSafety(config) + 
                    weights[TD] * throneDistance(config) +
                    weights[CD] * centerDistance(config);

        if (config.getCurrentPlayer() == selfID)
            return eval;
        else // enemy is playing, so return the opposite
            return -eval;
    }


    /* Methods to evaluate a state of the game  */

    private double pieceNumber(GameConfiguration config) {
        return config.allyPositions().size() - config.enemyPositions().size();
    }

    private double kingSafety(GameConfiguration config) {
        int allyDanger = 0;
        for (Vector2D enemy : config.enemyPositions())
            allyDanger += distance(config.allyKing(), enemy);
        int enemyDanger = 0;
        for (Vector2D ally : config.allyPositions())
            enemyDanger += distance(config.enemyKing(), ally);
        return enemyDanger - allyDanger;        
    }

    private double throneDistance(GameConfiguration config) {
        return distance(config.allyKing(), config.allyGoal()) - 
               distance(config.enemyGoal(), config.enemyKing());
    }

    private double centerDistance(GameConfiguration config) {
        int allyDist = 0;
        for (Vector2D ally : config.allyPositions())
            allyDist += distance(ally, center);
        int enemyDist = 0;
        for (Vector2D enemy : config.enemyPositions())
            enemyDist += distance(enemy, center);
        return allyDist - enemyDist;
    }

    /* End of evaluation methods */
}