package Engine.Entities.UI;

import java.awt.Color;

import Engine.Entities.GameObject;
import Engine.Global.Settings;
import Engine.Structures.Vector2D;

public class ColorBackground extends GameObject {
    public Color color = Color.white;
    public Vector2D offset = new Vector2D(0,0);
    public GameObject frame;

    public ColorBackground(Color color, GameObject frame, Vector2D offset) {
        this.color = color;
        this.frame = frame;
        this.offset = offset;
        init();
    }

    public ColorBackground(Color color, GameObject frame) {
        this.color = color;
        this.frame = frame;
        init();
    }

    public ColorBackground(GameObject frame) {
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
