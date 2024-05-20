package Onitama.src.Scenes.GameScene.Scripts.History;

import java.util.Stack;

import Engine.Global.Util;
import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Scripts.States.State;

public class History {
    // stack of played moves
    private Stack<State> undo;

    // stack of undone moves. It is cleared when we make a move.
    private Stack<State> redo;

    private State initialGameState;

    public History() {
        undo = new Stack<>();
        redo = new Stack<>();
        initialGameState = GameScene.getGameState();
    }

    public boolean canRedo() {
        return !redo.isEmpty();
    }

    public boolean canUndo() {
        return !undo.isEmpty();
    }

    public void undo() {
        if (!canUndo()) {
            Util.printWarning("Nothing to undo");
            return;
        }

        State pastState = undo.pop();
        redo.push(GameScene.getGameState());

        GameScene.loadGameState(pastState);
        
    }

    public void redo() {
        if (!canRedo()) {
            Util.printWarning("Nothing to redo");
            return;
        }

        State futureState = redo.pop();
        undo.push(GameScene.getGameState());

        GameScene.loadGameState(futureState);
    }

    public void addState(State s) {
        undo.push(s);
        redo.clear();
    }

    public void clearRecord() {
        undo.clear();
        redo.clear();
    }

    public void resetGame() {
        GameScene.loadGameState(initialGameState);
        clearRecord();
    }

}
