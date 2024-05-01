package WaffleGame.src.Scenes.GameScene.Entities.AI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import Engine.Structures.Vector2D;
import WaffleGame.src.Scenes.GameScene.GameScene;
import static java.lang.Math.min;

/**
 * SmartAI
 */
public class SmartAI implements AI {
    int height, width;
    HashMap<String, Boolean> memoizationAI;
    HashMap<String, Boolean> memoizationEnemy;
    HashMap<String, List<Vector2D>> winningMoves;
    Random random;
    
    public SmartAI() {
        height = GameScene.waffleDimension.height;
        width = GameScene.waffleDimension.width;
        memoizationAI = new HashMap<>();
        memoizationEnemy = new HashMap<>();
        winningMoves = new HashMap<>();
        random = new Random();
    }

    @Override
    public Vector2D getAction() {
        int[] currentState = getCurrentState();
        if (evaluateAI(currentState))
            return randomWinningMove(currentState);
        else
            return minimumMove(currentState);
    }

    private boolean evaluateAI(int[] state) {
        String hash = hashOfState(state);
        if (memoizationAI.get(hash) != null)
            return memoizationAI.get(hash);

        if (state[0] == 0) {
            memoizationAI.put(hash, true);
            return true;
        }

        boolean result = false;
        List<Vector2D> winners = new ArrayList<>();
        for (Vector2D move : possibleMoves(state))
            if (evaluateEnemy(updateState(state, move))) {
                result = true;
                winners.add(move);
            }

        memoizationAI.put(hash, result);
        winningMoves.put(hash, winners);
        return result;
    }

    private boolean evaluateEnemy(int[] state) {
        String hash = hashOfState(state);
        if (memoizationEnemy.get(hash) != null)
            return memoizationEnemy.get(hash);

        if (state[0] == 0) {
            memoizationEnemy.put(hash, false);
            return false;
        }

        boolean result = true;
        for (Vector2D move : possibleMoves(state))
            result = result && evaluateAI(updateState(state, move));

        memoizationEnemy.put(hash, result);
        return result;
    }

    private Vector2D randomWinningMove(int[] state) {
        List<Vector2D> winners = winningMoves.get(hashOfState(state));
        return winners.get(random.nextInt(winners.size()));
    }

    private Vector2D minimumMove(int[] state) {
        for (int i = height-1; i >= 0; i--)
            if (state[i] > 0)
                return new Vector2D(i, state[i]-1);
        return null;
    }

    private List<Vector2D> possibleMoves(int[] state) {
        List<Vector2D> moves = new ArrayList<>();
        for (int i = 0; i < height; i++)
            for (int j = 0; j < state[i]; j++)
                moves.add(new Vector2D(i, j));
        return moves;
    }

    private int[] updateState(int[] state, Vector2D action) {
        int[] newState = new int[height];
        for (int i = 0; i < height; i++) {
            if (i < action.getIntX())
                newState[i] = state[i];
            else
                newState[i] = min(action.getIntY(), state[i]);
        }
        return newState;
    }

    private int[] getCurrentState() {
        int[] state = new int[height];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                if (GameScene.map.gridmap[i][j] != null)
                    state[i]++;
        return state;
    }

    private String hashOfState(int[] state) {
        String hash = "";
        for (int i = 0; i < height; i++)
            hash += state[i] + " ";
        return hash;
    }
}