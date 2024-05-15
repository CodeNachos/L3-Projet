package Onitama.src.Scenes.GameScene.Scripts.AI;

import java.util.*;

import Engine.Structures.Vector2D;

import static java.lang.Math.min;
import static java.lang.Math.max;
import static java.lang.Math.abs;


import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Scripts.GameConfiguration;
import Onitama.src.Scenes.GameScene.Scripts.Turn;

/**
 * SmartAI
 */
public class SmartAI extends AI {
    private static final int minusINF = Integer.MIN_VALUE;
    private static final int plusINF = Integer.MAX_VALUE;
    List<Turn> winners;
    Random random;
    int difficulty;
    int selfID;

    public SmartAI(int difficulty, int player) {
        this.winners = new ArrayList<>();
        this.random = new Random();
        this.difficulty = difficulty;
        this.selfID = player;
    }

    @Override
    public Turn play() {
        winners.clear();
        minmax(GameScene.game, true, difficulty, 
               minusINF, plusINF);
        return winners.get(random.nextInt(winners.size()));
    }

    private int minmax(GameConfiguration config, boolean isMaximizing, 
                                       int depth, int alpha, int beta) {
        if (config.isGameOver()) {
            if (config.getCurrentPlayer() == selfID) // gameover for the AI
                return minusINF;
            else // gameover for the enemy
                return plusINF;
        } else if (depth == 0)
            return heuristic(config);

        int eval, maxEval, minEval;

        if (isMaximizing) {
            maxEval = minusINF;
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

    /* 
    private int heuristic1(GameConfiguration config) {
        List<Vector2D> allies = config.allyPositions();
        List<Vector2D> enemies = config.enemyPositions(); 
        int pieceNumber = allies.size() - enemies.size();
        int throneDistance = distance(config.allyKing(), config.allyGoal()) - 
                             distance(config.enemyGoal(), config.enemyKing());
        int eval = pieceNumber + throneDistance; 
        if (config.getCurrentPlayer() == selfID)
            return eval;
        else
            return -eval;
    }

    private int distance(Vector2D first, Vector2D second) {
        return abs(first.getIntX() - second.getIntX()) + abs(first.getIntY() - second.getIntY());
    }
    */

    private int heuristic(GameConfiguration config) {
        List<Vector2D> allies = config.allyPositions();
        List<Vector2D> enemies = config.enemyPositions();
        
        // Nombre de pièces alliées et ennemies
        int pieceNumber = allies.size() - enemies.size();
        
        // Distance entre le roi allié et son trône, et entre le roi ennemi et son trône
        int throneDistance = distance(config.allyKing(), config.allyGoal()) - distance(config.enemyKing(), config.enemyGoal());
    
        // facteurs d'évaluation 
        int kingSafety = 0;
        Vector2D allyKing = config.allyKing();
        for (Vector2D enemy : enemies) {
            kingSafety -= distance(allyKing, enemy); // Plus les ennemis sont proches du roi allié, plus la valeur est négative
        }
    
        int eval = pieceNumber + throneDistance + kingSafety;
    
        if (config.getCurrentPlayer() == selfID)
            return eval;
        else
            return -eval;
    }
    private int distance(Vector2D first, Vector2D second) {
        return abs(first.getIntX() - second.getIntX()) + abs(first.getIntY() - second.getIntY());
    }
}