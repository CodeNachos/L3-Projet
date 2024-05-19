package Onitama.src.Scenes.GameScene.Scripts.AI;

import static java.lang.Math.min;
import static java.lang.Math.max;
import java.util.function.Function;

import Engine.Structures.Vector2D;
import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Scripts.GameConfiguration;
import Onitama.src.Scenes.GameScene.Scripts.Turn;
import Onitama.src.Scenes.GameScene.Scripts.Card.CardInfo;

/**
 * SmartAI
 */
import java.util.ArrayList;
import java.util.List;

public class SmartAI {
    private static final double NEG_INF = Double.NEGATIVE_INFINITY;
    private static final double POS_INF = Double.POSITIVE_INFINITY;
    private static final Vector2D CENTER = new Vector2D(2, 2);
    private static final double[] DEFAULT_WEIGHTS = {4, 3, 2, 1};
    private static final int NB_EVAL_METHODS = 5;

    // Table des fonctions d'évaluation
    private static final Function<GameConfiguration, Double>[] EVALUATIONS = new Function[NB_EVAL_METHODS];
    static {
        EVALUATIONS[0] = SmartAI::pieceNumber;
        EVALUATIONS[1] = SmartAI::kingSafety;
        EVALUATIONS[2] = SmartAI::throneDistance;
        EVALUATIONS[3] = SmartAI::centerDistance;
        EVALUATIONS[4] = SmartAI::pieceMobility;
    }

    private Turn bestMove;
    private int difficulty;
    private int selfID;
    private double[] weights;

    // Constructeur pour initialiser l'IA
    public SmartAI(int difficulty, int player) {
        this(difficulty, player, DEFAULT_WEIGHTS);
    }

    // Constructeur avec poids personnalisés
    public SmartAI(int difficulty, int player, double[] weights) {
        this.difficulty = difficulty;
        this.selfID = player;
        this.weights = weights;
    }

    // Joue le meilleur coup trouvé
    public Turn play() {
        double eval = minimax(GameScene.game, true, difficulty, NEG_INF, POS_INF);
        System.err.println("Best score found: " + eval);
        return bestMove;
    }

    // Algorithme Minimax avec optimisation Alpha-Beta
    private double minimax(GameConfiguration config, boolean isMaximizing, int depth, double alpha, double beta) {
        if (config.isGameOver() || depth == 0) {
            return heuristic(config, depth);
        }

        double eval, maxEval = NEG_INF, minEval = POS_INF;

        if (isMaximizing) {
            for (Turn turn : possibleTurns(config)) {
                eval = minimax(config.nextConfig(turn), false, depth - 1, alpha, beta);
                if (depth == difficulty && eval > alpha) {
                    bestMove = turn;
                }
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break;
            }
            return maxEval;
        } else {
            for (Turn turn : possibleTurns(config)) {
                eval = minimax(config.nextConfig(turn), true, depth - 1, alpha, beta);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }

    // Fonction heuristique pour évaluer l'état du jeu
    private double heuristic(GameConfiguration config, int depth) {
        double eval = 0;
        if (config.isGameOver()) {
            eval -= (1000000 + depth);
        } else {
            for (int i = 0; i < NB_EVAL_METHODS; i++) {
                eval += weights[i] * EVALUATIONS[i].apply(config);
            }
        }
        return (config.getCurrentPlayer() == selfID) ? eval : -eval;
    }

    // Obtenir tous les coups possibles
    private List<Turn> possibleTurns(GameConfiguration config) {
        List<Turn> turns = new ArrayList<>();
        for (Vector2D piecePosition : config.allyPositions()) {
            for (Turn turn : possibleMoves(config, piecePosition)) {
                turns.add(turn);
            }
        }
        return turns;
    }

    // Évaluation du nombre de pièces
    private static double pieceNumber(GameConfiguration config) {
        return config.allyPositions().size() - config.enemyPositions().size();
    }

    // Évaluation de la sécurité des rois
    private static double kingSafety(GameConfiguration config) {
        int allyDanger = 0, enemyDanger = 0;
        for (Vector2D enemy : config.enemyPositions()) {
            allyDanger += distance(config.allyKing(), enemy);
        }
        for (Vector2D ally : config.allyPositions()) {
            enemyDanger += distance(config.enemyKing(), ally);
        }
        return enemyDanger - allyDanger;
    }

    // Évaluation de la distance au trône
    private static double throneDistance(GameConfiguration config) {
        return distance(config.allyKing(), config.allyGoal()) - distance(config.enemyGoal(), config.enemyKing());
    }

    // Évaluation de la distance au centre
    private static double centerDistance(GameConfiguration config) {
        int allyDist = 0, enemyDist = 0;
        for (Vector2D ally : config.allyPositions()) {
            allyDist += distance(ally, CENTER);
        }
        for (Vector2D enemy : config.enemyPositions()) {
            enemyDist += distance(enemy, CENTER);
        }
        return allyDist - enemyDist;
    }

    // Évaluation de la mobilité des pièces
    private static double pieceMobility(GameConfiguration config) {
        int allyMobility = 0, enemyMobility = 0;
        for (Vector2D ally : config.allyPositions()) {
            allyMobility += possibleMoves(config, ally).size();
        }
        for (Vector2D enemy : config.enemyPositions()) {
            enemyMobility += possibleMoves(config, enemy).size();
        }
        return allyMobility - enemyMobility;
    }

    // Obtenir les mouvements possibles pour une pièce
    private static List<Turn> possibleMoves(GameConfiguration config, Vector2D position) {
        List<Turn> moves = new ArrayList<>();
        for (CardInfo card : config.availableCards()) {
            for (Vector2D move : card.getMoves()) {
                Vector2D newPosition = position.add(move);
                if (isValidMove(config, position, newPosition)) {
                    moves.add(new Turn(card, position, newPosition));
                }
            }
        }
        return moves;
    }

    // Vérifier si un mouvement est valide
    private static boolean isValidMove(GameConfiguration config, Vector2D from, Vector2D to) {
        return config.isWithinBounds(to) && !config.allyPositions().contains(to);
    }

    // Calculer la distance entre deux positions
    private static int distance(Vector2D pos1, Vector2D pos2) {
        return (int) (Math.abs(pos1.getX() - pos2.getX()) + Math.abs(pos1.getY() - pos2.getY()));
    }
}