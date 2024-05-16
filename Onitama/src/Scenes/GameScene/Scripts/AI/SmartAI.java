package Onitama.src.Scenes.GameScene.Scripts.AI;

import static java.lang.Math.min;
import static java.lang.Math.max;
import java.util.function.Function;

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
    private static final double[] defaultWeights = {4, 3, 2, 1};
    public static final int NB_METHOD = 4;

    @SuppressWarnings("unchecked")
    private static final Function<GameConfiguration, Double>[] evaluations = new Function[NB_METHOD];
    static {
        evaluations[0] = config -> pieceNumber(config);
        evaluations[1] = config -> kingSafety(config);
        evaluations[2] = config -> throneDistance(config);
        evaluations[3] = config -> centerDistance(config);
    }

    Turn bestMove;
    int difficulty, selfID;
    double[] weights;


    public SmartAI(int difficulty, int player) {
        this(difficulty, player, defaultWeights);
    }

    public SmartAI(int difficulty, int player, double[] weights) {
        this.difficulty = difficulty;
        this.selfID = player;
        this.weights = weights;
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
        double eval = 0; // evaluation for AI

        if (config.isGameOver()) // AI lost
            eval -= (1000000 + depth);
        else // ran out of depth, give an estimate
            for (int i = 0; i < evaluations.length; i++)
                eval += weights[i] * evaluations[i].apply(config);

        if (config.getCurrentPlayer() == selfID)
            return eval;
        else // enemy is playing, so return the opposite
            return -eval;
    }


    /* Methods to evaluate a state of the game  */

    private static double pieceNumber(GameConfiguration config) {
        return config.allyPositions().size() - config.enemyPositions().size();
    }

    private static double kingSafety(GameConfiguration config) {
        int allyDanger = 0;
        for (Vector2D enemy : config.enemyPositions())
            allyDanger += distance(config.allyKing(), enemy);
        int enemyDanger = 0;
        for (Vector2D ally : config.allyPositions())
            enemyDanger += distance(config.enemyKing(), ally);
        return enemyDanger - allyDanger;        
    }

    private static double throneDistance(GameConfiguration config) {
        return distance(config.allyKing(), config.allyGoal()) - 
               distance(config.enemyGoal(), config.enemyKing());
    }

    private static double centerDistance(GameConfiguration config) {
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