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
    List<Turn> winners;
    Random random;
    int difficulty;
    int player;

    public SmartAI(int difficulty, int player) {
        this.winners = new ArrayList<>();
        this.random = new Random();
        this.difficulty = difficulty;
        this.player = player;
    }

    @Override
    public Turn play() {
        winners.clear();
        minmax(GameScene.game, true, difficulty, 
               Integer.MIN_VALUE, Integer.MAX_VALUE);
        return winners.get(random.nextInt(winners.size()));
    }

    private int minmax(GameConfiguration config, boolean isMaximizing, 
                                       int depth, int alpha, int beta) {
        if (config.gameOver()) {
            if (config.getCurrentPlayer() == player)
                return Integer.MIN_VALUE;
            else
                return Integer.MAX_VALUE;
        } else if (depth == 0)
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
        List<Vector2D> allies = config.allyPositions();
        List<Vector2D> enemies = config.enemyPositions(); 
        int pieceNumber = allies.size() - enemies.size();
        int throneDistance = distance(config.allyKing(), config.allyGoal()) - distance(config.enemyGoal(), config.enemyKing());
        int eval = pieceNumber + throneDistance; 
        if (config.getCurrentPlayer() == player)
            return eval;
        else
            return -eval;
    }

    private int distance(Vector2D first, Vector2D second) {
        return abs(first.getIntX() - second.getIntX()) + abs(first.getIntY() - second.getIntY());
    }
}