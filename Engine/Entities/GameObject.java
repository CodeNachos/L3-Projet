package Engine.Entities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.JComponent;

import Engine.Structures.Vector2D;

/**
 * The GameObject class represents the most basic object within the game world.
 * It provides functionalities for position, scaling, sprite handling, and event handling.
 */
public abstract class GameObject extends JComponent {

    // Object attributes
    public Vector2D position = new Vector2D(0, 0); // Position of the object
    public Vector2D scale = new Vector2D(1, 1); // Scale of the object
    public Image sprite = null; // Sprite of the object

    /**
     * Constructs a new GameObject instance with default attributes.
     */
    public GameObject() {
        updateVisuals(); // Update visuals based on attributes
    }

    /**
     * Constructs a new GameObject instance with the given position.
     * @param position The position of the object
     */
    public GameObject(Vector2D position) {
        this.position = position.clone(); // Set position
        updateVisuals(); // Update visuals based on attributes
    }

    /**
     * Constructs a new GameObject instance with the given position and scale.
     * @param position The position of the object
     * @param scale The scale of the object
     */
    public GameObject(Vector2D position, Vector2D scale) {
        this.position = position.clone(); // Set position
        this.scale = scale.clone(); // Set scale
        updateVisuals(); // Update visuals based on attributes
    }

    /**
     * Constructs a new GameObject instance with the given position, scale, and sprite.
     * @param position The position of the object
     * @param scale The scale of the object
     * @param sprite The sprite of the object
     */
    public GameObject(Vector2D position, Vector2D scale, Image sprite) {
        this.position = position.clone(); // Set position
        this.scale = scale.clone(); // Set scale
        this.sprite = sprite; // Set sprite
        updateVisuals(); // Update visuals based on attributes
    }

    /**
     * Gets the position of the object.
     * @return The position of the object
     */
    public Vector2D getPos() {
        return position.clone(); // Return a clone of the position to prevent modification
    }

    /**
     * Gets the scale of the object.
     * @return The scale of the object
     */
    public Vector2D getScale() {
        return scale.clone(); // Return a clone of the scale to prevent modification
    }

    /**
     * Sets the position of the object.
     * @param newpos The new position to set
     */
    public void setPos(Vector2D newpos) {
        position.x = newpos.x; // Set position x-coordinate
        position.y = newpos.y; // Set position y-coordinate
        this.setLocation((int)position.x, (int)position.y); // Set component location
    }

    /**
     * Sets the scale of the object.
     * @param newscale The new scale to set
     */
    public void setScale(Vector2D newscale) {
        scale.x = newscale.x; // Set scale x-coordinate
        scale.y = newscale.y; // Set scale y-coordinate
        updateSize(); // Update component size based on scale
    }

    /**
     * Sets the sprite of the object.
     * @param sprite The sprite to set
     */
    public void setSprite(Image sprite) {
        this.sprite = sprite; // Set sprite
        updateSize(); // Update component size based on sprite
    }

    /**
     * Gets the sprite of the object.
     * @return The sprite of the object
     */
    public Image getSprite() {
        return sprite; // Return the sprite of the object
    }

    /**
     * Runs at every frame to update the object's state.
     * 
     * @param delta The time since the last update in seconds
     */
    public void process(double delta) {
        // To be implemented in subclasses
    }

    /**
     * Handles keyboard input events.
     * @param e The KeyEvent representing the input event
     */
    public void input(KeyEvent e) {
        // To be implemented in subclasses
    }

    /**
     * Handles mouse input events.
     * @param e The MouseEvent representing the input event
     */
    public void input(MouseEvent e) {
        // To be implemented in subclasses
    }

    /**
     * Updates the size of the component based on the sprite and scale.
     */
    private void updateSize() {
        if (sprite != null) { // If a sprite is set
            this.setSize(
                (int)Math.ceil(sprite.getWidth(null) * scale.x), // Set width based on sprite width and scale
                (int)Math.ceil(sprite.getHeight(null) * scale.y) // Set height based on sprite height and scale
            );
        } else {
            this.setSize(0, 0); // Set size to zero if no sprite is set
        }
    }

    /**
     * Updates the visual properties of the object.
     */
    private void updateVisuals() {
        setLocation((int)position.x, (int)position.y); // Set component location
        updateSize(); // Update component size
    }

    /**
     * Overrides the paintComponent method to render the object's sprite.
     * @param g The Graphics context used for painting
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Call superclass's paintComponent method
        
        if (sprite != null) // If a sprite is set
            g.drawImage(sprite, getLocation().x, getLocation().y, getSize().width, getSize().height, null); // Draw the sprite
    }

    /**
     * Resizes the object based on the given resize ratio.
     * @param ratio The ratio by which to resize the object
     */
    public void resize(Vector2D ratio) {
        Vector2D updatedValues = new Vector2D();

        // Set new position relative to the resize ratio
        updatedValues.setCoord(position.x * ratio.x, position.y * ratio.y);
        setPos(updatedValues);

        // Set new scale relative to the resize ratio
        updatedValues.setCoord(scale.x * ratio.x, scale.y * ratio.y);
        setScale(updatedValues);
    }
}
