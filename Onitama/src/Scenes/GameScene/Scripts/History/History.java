package Onitama.src.Scenes.GameScene.Scripts.History;

import java.io.Serializable;
import java.util.Stack;

import Engine.Global.Util;
import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Scripts.States.State;
import Onitama.src.Main;

public class History implements Serializable {
    // stack of played moves
    private Stack<State> undo;

    // stack of undone moves. It is cleared when we make a move.
    private Stack<State> redo;

    private State initialGameState = null;

    public History() {
        undo = new Stack<>();
        redo = new Stack<>();
    }

    public void setInitialGameState(State s) {
        initialGameState = s;
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
        redo.push(Main.gameScene.getGameState());

        Main.gameScene.loadGameState(pastState);
        
    }

    public void redo() {
        if (!canRedo()) {
            Util.printWarning("Nothing to redo");
            return;
        }

        State futureState = redo.pop();
        undo.push(Main.gameScene.getGameState());

        Main.gameScene.loadGameState(futureState);
    }

    public void addState(State s) {
        if (undo.isEmpty() && initialGameState == null) {
            setInitialGameState(Main.gameScene.getGameState());
        }
        undo.push(s);
        redo.clear();
    }

    public void clearRecord() {
        undo.clear();
        redo.clear();
    }

    public void resetGame() {
        if (initialGameState == null) {
            return;
        }
        Main.gameScene.loadGameState(initialGameState);
        clearRecord();
    }

}
