package Engine.Core.Controller;

import java.awt.event.*;

import Engine.Entities.GameObject;
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
        for (GameObject obj : controller.scene.components) {
            // check if is target object
            if (obj.getBounds().contains(e.getPoint())) {
                if (obj.cursorIn)
                    continue;
                
                obj.cursorIn = true;
                // if obj is tile map send event to tiles
                if (obj instanceof TileMap) {
                    TileMap mapObj = (TileMap)obj;
                    forwardBoundEventToTiles(mapObj, e);
                
                // else default treatment
                } else {
                    obj.input(generateMouseEntered(e, obj));
                }
            } else if (obj.cursorIn) {
                obj.cursorIn = false;
                obj.input(generateMouseExited(e, obj));
            }
        }
    }

    private void forwardBoundEventToTiles(TileMap mapObj, MouseEvent e) {
        
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
}
