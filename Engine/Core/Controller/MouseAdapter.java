package Engine.Core.Controller;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import Engine.Entities.GameObject;
import Engine.Entities.TileMap.Tile;
import Engine.Entities.TileMap.TileMap;

public class MouseAdapter implements MouseListener {
    private Controller controller;
    
    public MouseAdapter(Controller c) {
        this.controller = c;
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
        List<GameObject> targetObjects = new ArrayList<>();

        // Collect objects that should receive the event
        for (GameObject obj : controller.scene.components) {
            // Check if is target object
            if (obj.getBounds().contains(e.getPoint())) {
                targetObjects.add(obj);
            }
        }

        // Forward the event to the collected objects
        for (GameObject obj : targetObjects) {
            if (obj instanceof TileMap) {
                TileMap mapObj = (TileMap) obj;
                forwardMouseEventToTiles(mapObj, e);
            } else {
                obj.input(e);
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

}
