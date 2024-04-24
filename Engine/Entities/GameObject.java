package Engine.Entities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.JComponent;

import Engine.Structures.Vector2D;

public abstract class GameObject extends JComponent {
    // Object attributes
    public Vector2D position = new Vector2D(0,0);
    public Vector2D scale = new Vector2D(1,1);
    public Image sprite = null;
    
    public GameObject() {
        updateVisuals();
    }

    public GameObject(Vector2D position) {
        this.position = position.clone();
        updateVisuals();
    }

    public GameObject(Vector2D position, Vector2D scale) {
        this.position = position.clone();
        this.scale = scale.clone();
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (sprite != null)
            g.drawImage(sprite, getLocation().x, getLocation().y, getSize().width, getSize().height,null);
    }

    public void resize(Vector2D ratio) {
        Vector2D updatedValues = new Vector2D();
        // set relative position
        updatedValues.setCoord(position.x * ratio.x, position.y * ratio.y);
        setPos(updatedValues);
        // set relative scaling
        updatedValues.setCoord(scale.x * ratio.x, scale.y * ratio.y);
        setScale(updatedValues);
    }
}