package Engine.Entities.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import Engine.Structures.Vector2D;

/**
 * The ColorArea class represents a colored area in the user interface.
 * It inherits properties and functionalities from UIObject.
 */
public class ColorArea extends UIObject {

    public Color color = Color.white; // Color of the area

    /**
     * Constructs a new ColorArea instance with the specified color, area, and offset.
     * @param color The color of the area
     * @param area The area size of the color area
     * @param offset The offset position of the color area
     */
    public ColorArea(Color color, Dimension area, Vector2D offset) {
        super(area, offset); // Call superclass constructor with area and offset
        this.color = color; // Set color of the area
    }

    /**
     * Constructs a new ColorArea instance with the specified color and area.
     * @param color The color of the area
     * @param area The area size of the color area
     */
    public ColorArea(Color color, Dimension area) {
        super(area); // Call superclass constructor with area
        this.color = color; // Set color of the area
    }

    /**
     * Constructs a new ColorArea instance with the specified area.
     * @param area The area size of the color area
     */
    public ColorArea(Dimension area) {
        super(area); // Call superclass constructor with area
    }

    /**
     * Overrides the paintComponent method to render the colored area.
     * @param g The Graphics context used for painting
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Call superclass's paintComponent method

        g.setColor(color); // Set color for drawing
        g.fillRect(position.getIntX(), position.getIntY(), getWidth(), getHeight()); // Draw filled rectangle
    }
}
