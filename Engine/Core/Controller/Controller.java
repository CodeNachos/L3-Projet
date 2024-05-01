package Engine.Core.Controller;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import Engine.Core.Renderer.Scene;
import Engine.Entities.GameObject;
import Engine.Entities.TileMap.Tile;
import Engine.Entities.TileMap.TileMap;
import Engine.Global.Settings;

public class Controller implements MouseListener, KeyListener {
    // Controller scene
    Scene scene;


    public Controller(Scene scn) {
        this.scene = scn;
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

    @Override
    public void mouseClicked(MouseEvent e) {
        forwardMouseEvent(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        forwardMouseEvent(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        forwardMouseEvent(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        forwardMouseEvent(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        forwardMouseEvent(e);
    }


    // sends mouse event to concerned game object in current scene
    private void forwardMouseEvent(MouseEvent e) {
        for (GameObject obj : scene.components) {
            // check if is target object
            if (obj.getBounds().contains(e.getPoint())) {
                // if obj is tile map send event to tiles
                if (obj instanceof TileMap) {
                    TileMap mapObj = (TileMap)obj;
                    forwardMouseEventToTiles(mapObj, e);
                
                // else default treatment
                } else {
                    obj.input(e);
                }
            }
        }
    }


    // sends mouse event to concerned tile in a tile map
    private void forwardMouseEventToTiles(TileMap mapObj, MouseEvent e) {
        for (int l = 0; l < mapObj.mapDimension.height; l++) {
            for (int c = 0; c < mapObj.mapDimension.width; c++) {
                if (mapObj.gridmap[l][c] != null && getTileGlobalBounds(mapObj.gridmap[l][c]).contains(e.getPoint())) {
                    mapObj.gridmap[l][c].input(e);
                }
            }
        }
    }

    private Rectangle getTileGlobalBounds(Tile t) {
        Rectangle globalBounds;

        globalBounds = new Rectangle(t.parentMap.getPos().getIntX() + t.getPos().getIntX(),
            t.parentMap.getPos().getIntY() + t.getPos().getIntY(), 
            t.getBounds().width,
            t.getBounds().height
        );

        return globalBounds;
    }

    private void forwardKeyEvent(KeyEvent e) {
        for (GameObject obj : scene.components) {
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