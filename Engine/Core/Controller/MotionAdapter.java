package Engine.Core.Controller;

import java.awt.Rectangle;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import Engine.Entities.GameObject;
import Engine.Entities.TileMap.Tile;
import Engine.Entities.TileMap.TileMap;

public class MotionAdapter implements MouseMotionListener {
    private Controller controller;

    public MotionAdapter(Controller c) {
        this.controller = c;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        // unimplemented
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        handleBoundEvents(e);
    }

    private void handleBoundEvents(MouseEvent e) {
        List<GameObject> targetObjects = new ArrayList<>();

        // Collect objects that should receive the event
        for (GameObject obj : controller.scene.components) {
            // Check if is target object
            if (obj.getBounds().contains(e.getPoint())) {
                targetObjects.add(obj);
            }
        }

        for (GameObject obj : targetObjects) {
            // check if is target object
            if (obj.getBounds().contains(e.getPoint())) {
                if (obj instanceof TileMap) {
                    // if obj is tile map send event to tiles
                    TileMap mapObj = (TileMap)obj;
                    forwardBoundEventToTiles(mapObj, e);
                }

                if (obj.cursorIn)
                    continue;
                
                obj.cursorIn = true; 
                obj.input(generateMouseEntered(e, obj));
                
            } else if (obj.cursorIn) {
                if (obj instanceof TileMap) {
                    // if obj is tile map send event to tiles
                    TileMap mapObj = (TileMap)obj;
                    forwardBoundEventToTiles(mapObj, e);
                }

                obj.cursorIn = false;
                obj.input(generateMouseExited(e, obj));
            }
        }
    }

    // sends mouse event to concerned tile in a tile map
    private void forwardBoundEventToTiles(TileMap mapObj, MouseEvent e) {
        for (int l = 0; l < mapObj.mapDimension.height; l++) {
            for (int c = 0; c < mapObj.mapDimension.width; c++) {
                if (mapObj.gridmap[l][c] == null)
                    continue;
                
                if (getTileGlobalBounds(mapObj.gridmap[l][c]).contains(e.getPoint())) {
                    if (mapObj.gridmap[l][c].cursorIn)
                        continue;

                    mapObj.gridmap[l][c].cursorIn = true;
                    mapObj.gridmap[l][c].input(generateMouseEntered(e, mapObj.gridmap[l][c]));
                } else {
                    mapObj.gridmap[l][c].cursorIn = false;
                    mapObj.gridmap[l][c].input(generateMouseExited(e, mapObj.gridmap[l][c]));
                }
            }
        }
    }
    

    private MouseEvent generateMouseEntered(MouseEvent e, GameObject obj) {
        return new MouseEvent(
            obj, 
            MouseEvent.MOUSE_ENTERED,
            e.getWhen(),
            0,
            e.getX(),
            e.getY(),
            0,
            false);
    }

    private MouseEvent generateMouseExited(MouseEvent e, GameObject obj) {
        return new MouseEvent(
            obj, 
            MouseEvent.MOUSE_EXITED,
            e.getWhen(),
            0,
            e.getX(),
            e.getY(),
            0,
            false);
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
}
