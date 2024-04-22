package Engine.Entities.UI;

import java.awt.Image;

import Engine.Entities.GameObject;
import Engine.Global.Settings;
import Engine.Global.Util;
import Engine.Structures.Vector2D;

public class UIObject extends GameObject {
    public Vector2D position = new Vector2D(0,0);
    public Vector2D scale = new Vector2D(1,1);
    public GameObject frame;

    public UIObject(UIFrame frame, Vector2D position) {
        this.frame = frame;
        this.position = position;
        updateVisuals();
    }

    public UIObject(GameObject frame) {
        this.frame = frame;
        updateVisuals();
    }

    public Vector2D getPos() {
        return position.clone();
    }

    public Vector2D getScale() {
        return scale.clone();
    }

    public void setPos(Vector2D newpos) {
        position.x = newpos.x;
        position.y = newpos.y;
        updateVisuals();
    }

    public void setScale(Vector2D newscale) {
        if (frame != null) {
            Util.printWarning("Can't scale framed UIObject, try to scale frame instead.");
        }
        scale.x = newscale.x;
        scale.y = newscale.y;
    }

    public void setSprite(Image sprite) {
        Util.printError("Unsupported operation: Updates to come.");
    }

    private void updateVisuals() {
        if (frame != null) {
            this.setScale(frame.scale);
            this.setSize(frame.getSize());
            if (position != null) 
                setPos(frame.getPos().add(position));
            else
                setPos(frame.getPos());
        } else {
            this.setSize(Settings.resolution);
            setLocation((int)position.x, (int)position.y);
        }
    }
}
