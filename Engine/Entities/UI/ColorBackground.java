package Engine.Entities.UI;

import java.awt.Color;

import Engine.Entities.GameObject;
import Engine.Global.Settings;
import Engine.Structures.Vector2D;

public class ColorBackground extends UIObject {
    public Color color = Color.white;

    public ColorBackground(Color color, GameObject frame, Vector2D offset) {
        super(frame, offset);
        this.color = color;
    }

    public ColorBackground(Color color, GameObject frame) {
        super(frame);
        this.color = color;
    }

    public ColorBackground(GameObject frame) {
        super(frame);
    }
}
