package WaffleGame.src.Scenes.GameScene.Entities;

import java.awt.event.KeyEvent;
import java.util.Stack;

import Engine.Entities.GameObject;
import Engine.Global.Settings;
import Engine.Global.Util;
import WaffleGame.src.Scenes.GameScene.GameScene;

/**
 * The History class tracks the history of game state changes,
 * allowing for undo and redo functionality.
 */
public class History extends GameObject {
    private Stack<boolean[][]> undo; // Stack to store previous game states for undo
    private Stack<boolean[][]> redo; // Stack to store undone game states for redo

    /**
     * Constructs a new History object.
     * Initializes the undo and redo stacks.
     */
    public History() {
        undo = new Stack<>();
        redo = new Stack<>();
    }

    /**
     * Adds the current game state to the undo stack.
     * Clears the redo stack as a new action is added.
     */
    public void addAction() {
        undo.push(getGameState());
        redo.clear();
    }

    /**
     * Checks if there are actions available to undo.
     * 
     * @return true if there are actions to undo, false otherwise.
     */
    public boolean canUndoAction() {
        return !undo.isEmpty();
    }

    /**
     * Checks if there are actions available to redo.
     * 
     * @return true if there are actions to redo, false otherwise.
     */
    public boolean canRedoAction() {
        return !redo.isEmpty();
    }

    /**
     * Undoes the last action by restoring the previous game state.
     */
    public void undoAction() {
        redo.push(getGameState());
        boolean[][] action = undo.pop();
        restoreGameState(action);
        GameScene.map.next_player = true; // Advance to the next player
    }

    /**
     * Redoes the previously undone action by restoring the game state.
     */
    public void redoAction() {
        undo.push(getGameState());
        boolean[][] action = redo.pop();
        restoreGameState(action);
        GameScene.map.next_player = true; // Advance to the next player
    }

    /**
     * Clears all recorded actions from the undo and redo stacks.
     */
    public void clearRecords() {
        undo.clear();
        redo.clear();
    }

    /**
     * Retrieves the current game state.
     * 
     * @return a 2D boolean array representing the current game state.
     */
    private boolean[][] getGameState() {
        boolean[][] gameState = new boolean[GameScene.map.mapDimension.height][GameScene.map.mapDimension.width];

        for(int l = 0; l < GameScene.map.mapDimension.height; l++) {
            for (int c = 0; c < GameScene.map.mapDimension.width; c++) {
               if (GameScene.map.gridmap[l][c] != null) {
                    gameState[l][c] = true;
               } 
            }
        }

        return gameState;
    }

    /**
     * Restores the game state based on the provided state.
     * 
     * @param state a 2D boolean array representing the game state to restore.
     */
    private void restoreGameState(boolean[][] state) {
        WaffleTile tile;
        for(int l = 0; l < GameScene.map.mapDimension.height; l++) {
            for (int c = 0; c < GameScene.map.mapDimension.width; c++) {
               if (state[l][c]) {
                    if (l == 0 && c == 0) {
                        tile = new WaffleTile(GameScene.map, l, c, GameScene.poisonSprite); // Create poison waffle tile
                    } else {
                        tile = new WaffleTile(GameScene.map, l, c, GameScene.waffleSprite); // Create regular waffle tile
                    }
                    GameScene.map.addTile(l, c, tile); // Add tile to the map
               }  else if (GameScene.map.gridmap[l][c] != null) {
                    GameScene.map.removeTile(l, c);
               }
            }
        }
    }

    @Override
    public void input(KeyEvent e) {
        // Ignore input if game over or if animation is being processed
        if (GameScene.game.gameOver || (GameScene.map.animating))
            return;

        if (e.getID() == KeyEvent.KEY_PRESSED) {
            if (e.getKeyCode() == Settings.undo_key) {
                if (canUndoAction()) {
                    System.out.println("undoing action");
                    undoAction();
                } else {
                    Util.printWarning("Can't undo because history is empty");
                }
            } else if (e.getKeyCode() == Settings.redo_key) {
                if (canRedoAction()) {
                    System.out.println("redoing action");
                    redoAction();
                } else {
                    Util.printWarning("Can't redo because no action was undone");
                }
            }
        }
    }
}