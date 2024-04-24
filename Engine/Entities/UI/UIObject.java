package Engine.Entities.UI;

import java.awt.Dimension;
import java.awt.Image;

import Engine.Entities.GameObject;
import Engine.Global.Settings;
import Engine.Global.Util;
import Engine.Structures.Vector2D;

public class UIObject extends GameObject {

    // DO NOT CHANGE AFTER CONTRUCTOR INITIALIZATION
    private Dimension initialArea = null;

    public UIObject(Dimension area, Vector2D position) {
        super(position);

        // DO NOT CHANGE VALUE
        initialArea = area;
        
        updateSize();
    }

    public UIObject(Dimension area) {   
        super();

        // DO NOT CHANGE VALUE
        initialArea = area;
        
        updateSize();
    }

    @Override
    public void setScale(Vector2D newscale) {
        scale.x = newscale.x;
        scale.y = newscale.y;
        updateSize();
    }

    public void setSprite(Image sprite) {
        Util.printError("Unsupported operation: Updates to come.");
    }

    private void updateSize() {
        if (initialArea != null) {
            this.setSize(
                (int)(initialArea.width*scale.x), 
                (int)(initialArea.height*scale.y)
            );
        } else {
            this.setSize(Settings.resolution);
        }
        setLocation((int)position.x, (int)position.y);
    }
}
