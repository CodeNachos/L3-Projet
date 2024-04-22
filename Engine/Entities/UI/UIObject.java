package Engine.Entities.UI;

import Engine.Entities.GameObject;
import Engine.Global.Settings;
import Engine.Structures.Vector2D;

public class UIObject extends GameObject {
    public Vector2D offset = new Vector2D(0,0);
    public GameObject frame;

    public UIObject(GameObject frame, Vector2D offset) {
        this.frame = frame;
        this.offset = offset;
        init();
    }

    public UIObject(GameObject frame) {
        this.frame = frame;
       init();
    }

    private void init() {
        if (frame != null) {
            this.setSize(frame.getSize());
            if (offset != null) 
                setPos(frame.getPos().add(offset));
            else
                setPos(frame.getPos());
        } else {
            this.setSize(Settings.resolution);
        }
    }
}
