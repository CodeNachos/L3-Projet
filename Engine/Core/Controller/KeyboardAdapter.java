package Engine.Core.Controller;

import java.awt.event.*;

import Engine.Entities.GameObject;
import Engine.Entities.TileMap.TileMap;
import Engine.Global.Settings;

public class KeyboardAdapter implements KeyListener {
    Controller controller;

    public KeyboardAdapter(Controller c) {
        this.controller = c;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        forwardKeyEvent(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == Settings.fullscreen_key) {
            Settings.fullscreen = !Settings.fullscreen;
        } else {
            forwardKeyEvent(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        forwardKeyEvent(e);
    }

    private void forwardKeyEvent(KeyEvent e) {
        for (GameObject obj : controller.scene.components) {
            if (obj instanceof TileMap) {
                TileMap mapObj = (TileMap)obj;
                forwardKeyEventToTiles(mapObj, e);
            
            // else default treatment
            } else {
                obj.input(e);
            }
        }
    }

    private void forwardKeyEventToTiles(TileMap mapObj, KeyEvent e) {

    }
}
