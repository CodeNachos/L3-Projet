package Engine.Entities.UI;

import java.awt.Dimension;

import Engine.Entities.GameObject;
import Engine.Global.Settings;
import Engine.Global.Util;
import Engine.Structures.Sprite;
import Engine.Structures.Vector2D;

/**
 * The UIObject class represents a user interface element in the game.
 * It inherits properties and functionalities from GameObject.
 */
public abstract class UIObject extends GameObject {

    // DO NOT CHANGE AFTER CONSTRUCTOR INITIALIZATION
    protected Dimension initialArea = null; // Initial area size of the UI object

    /**
     * Constructs a new UIObject instance with the specified area and position.
     * @param area The area size of the UI object
     * @param position The position of the UI object
     */
    public UIObject(Dimension area, Vector2D position) {
        super(position); // Call superclass constructor with position

        // DO NOT CHANGE VALUE
        initialArea = area; // Store initial area size of the UI object

        updateSize(); // Update size based on scale
    }

    /**
     * Constructs a new UIObject instance with the specified area.
     * @param area The area size of the UI object
     */
    public UIObject(Dimension area) {
        super(); // Call superclass constructor

        // DO NOT CHANGE VALUE
        initialArea = area; // Store initial area size of the UI object

        updateSize(); // Update size based on scale
    }

    @Override
    public void setScale(Vector2D newscale) {
        scale.x = newscale.x; // Set scale x-coordinate
        scale.y = newscale.y; // Set scale y-coordinate
        updateSize(); // Update size based on scale
    }

    /**
     * Sets the sprite of the UI object.
     * @param sprite The sprite image to set
     */
    public void setSprite(Sprite sprite) {
        Util.printError("Unsupported operation: Updates to come.");
    }

    /**
     * Updates the size of the UI object based on its initial area and scale.
     */
    protected void updateSize() {
        if (initialArea != null) { // If initial area is set
            this.setSize(
                (int)(initialArea.width * scale.x), // Set width based on initial area width and scale
                (int)(initialArea.height * scale.y) // Set height based on initial area height and scale
            );
        } else { // If initial area is not set
            this.setSize(Settings.resolution); // Set size based on default resolution
        }
        setLocation((int)position.x, (int)position.y); // Set location of the UI object
    }
}
