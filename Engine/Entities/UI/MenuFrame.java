package Engine.Entities.UI;

import java.awt.*;
import javax.swing.*;

import Engine.Structures.Vector2D;

/**
 * The MenuFrame class represents a frame for menu interfaces.
 * It extends UIObject to provide customized appearance and behavior.
 */
public class MenuFrame extends UIObject {

    int borderWidth = 3;

    private Color mainColor = new Color(255, 255, 255, 255); // Main color of the menu frame
    private Color accentColor = new Color(255, 255, 255, 255); // Accent color of the menu frame (border color)
    private Dimension curvature = new Dimension(0, 0); // Curvature of the menu frame corners

    /**
     * Constructs a new MenuFrame instance with the specified area and offset.
     * @param area The area size of the menu frame
     * @param offset The offset position of the menu frame
     */
    public MenuFrame(Dimension area, Vector2D offset) {
        super(area, offset); // Call superclass constructor with area and offset
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Set layout to vertical BoxLayout
    }

    /**
     * Constructs a new MenuFrame instance with the specified area.
     * @param area The area size of the menu frame
     */
    public MenuFrame(Dimension area) {
        super(area); // Call superclass constructor with area
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Set layout to vertical BoxLayout
    }

    /**
     * Sets the main color of the menu frame.
     * @param color The main color to set
     */
    public void setMainColor(Color color) {
        mainColor = color; // Set main color of the menu frame
    }

    /**
     * Sets the accent color of the menu frame (border color).
     * @param color The accent color to set
     */
    public void setAccentColor(Color color) {
        accentColor = color; // Set accent color of the menu frame
    }

    /**
     * Sets the curvature (arc width and arc height) of the menu frame corners.
     * @param arcWidth The arc width of the curvature
     * @param arcHeight The arc height of the curvature
     */
    public void setCurvature(int arcWidth, int arcHeight) {
        curvature.width = arcWidth; // Set arc width of the curvature
        curvature.height = arcHeight; // Set arc height of the curvature
    }

    /**
     * Sets the border with value.
     * @param width Desired border width
     */
    public void setBorderWidth(int width) {
        borderWidth = width;
    }

    /**
     * Overrides the paintComponent method to customize the menu frame's appearance.
     * @param g The Graphics context used for painting
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Call superclass's paintComponent method

        // set stroke
        Graphics2D g2d = (Graphics2D) g;
        Stroke defaultStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(borderWidth));

        // paint background
        g2d.setColor(mainColor);
        g2d.fillRoundRect(0, 0, getWidth() - borderWidth/2, getHeight() - borderWidth/2, curvature.width, curvature.height);

        // paint borders in accent color
        g.setColor(accentColor);
        g2d.drawRoundRect(borderWidth/2, borderWidth/2, getWidth() - borderWidth, getHeight() - borderWidth, curvature.width, curvature.height);

        // restore stroke
        g2d.setStroke(defaultStroke);
    }
}
