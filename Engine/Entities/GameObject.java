package Engine.Entities;

import java.awt.Image;
import java.awt.event.*;

import javax.swing.JComponent;

import Engine.Structures.Vector2D;

public class GameObject extends JComponent {
    // Object attributes
    public Vector2D position;
    public Vector2D scale;
    public Image sprite;
    
    public GameObject() {
        position = new Vector2D(0,0);
        scale = new Vector2D(1,1);
        sprite = null;
        updateVisuals();
    }

    public GameObject(Vector2D position, Vector2D scale, Image sprite) {
        this.position = position.clone();
        this.scale = scale.clone();
        this.sprite = sprite;
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
        this.setLocation((int)position.x, (int)position.y);
    }

    public void setScale(Vector2D newscale) {
        scale.x = newscale.x;
        scale.y = newscale.y;
        updateSize();
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
        updateSize();
    }

    public Image getSprite() {
        return sprite;
    }

    // run at every frame
    public void process() {
       
    }

    // treats keyboard inputs
    public void input(KeyEvent e) {
        
    }

    // treats mouse inputs
    public void input(MouseEvent e) {

    }

    private void updateSize() {
        if (sprite != null) {
            this.setSize(
                (int)Math.ceil(sprite.getWidth(null)*scale.x), 
                (int)Math.ceil(sprite.getHeight(null)*scale.y)
            );
        } else {
            this.setSize(0,0);
        }
    }
    
    private void updateVisuals() {
        setLocation((int)position.x, (int)position.y);
        updateSize();
    }
}