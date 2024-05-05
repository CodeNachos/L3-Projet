package Engine.Entities.UI;

import javax.swing.*;

import Engine.Global.Util;

import java.awt.*;

/**
 * The FlatButton class represents a flat-style button in the user interface.
 * It extends JButton to provide customized appearance and behavior.
 */
public class FlatButton extends JButton {

    private Color mainColor = new Color(255, 255, 255, 255); // Main color of the button
    private Color accentColor = new Color(255, 255, 255, 255); // Accent color of the button (border color)
    private Color textColor = new Color(0, 0, 0, 255); // Text color of the button

    private Dimension curvature = new Dimension(0, 0); // Curvature of the button corners

    /**
     * Constructs a new FlatButton instance with the specified text.
     * @param text The text displayed on the button
     */
    public FlatButton(String text) {
        super(text); // Call superclass constructor with text
        setContentAreaFilled(false); // Make the background transparent
        setBorder(BorderFactory.createLineBorder(accentColor)); // Set the border color
        setForeground(textColor); // Set text color to border color
    }

    /**
     * Sets the main color of the button.
     * @param color The main color to set
     */
    public void setMainColor(Color color) {
        if (color == null) {
            Util.printError("null reference to color");
            return;
        }
        mainColor = color; // Set main color of the button
    }

    /**
     * Sets the accent color of the button (border color).
     * @param color The accent color to set
     */
    public void setAccentColor(Color color) {
        if (color == null) {
            Util.printError("null reference to color");
            return;
        }
        accentColor = color; // Set accent color of the button
    }

    /**
     * Sets the text color of the button.
     * @param color The text color to set
     */
    public void setTextColor(Color color) {
        if (color == null) {
            Util.printError("null reference to color");
            return;
        }
        textColor = color; // Set text color of the button
        setForeground(textColor); // Set text color to border color
    }

    /**
     * Sets the curvature (arc width and arc height) of the button corners.
     * @param arcWidth The arc width of the curvature
     * @param arcHeight The arc height of the curvature
     */
    public void setCurvature(int arcWidth, int arcHeight) {
        curvature.width = arcWidth; // Set arc width of the curvature
        curvature.height = arcHeight; // Set arc height of the curvature
    }

    /**
     * Overrides the paintComponent method to customize the button's appearance.
     * @param g The Graphics context used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Draw background
        if (getModel().isPressed()) {
            g.setColor(mainColor.darker()); // Darken the main color when pressed
        } else if (getModel().isRollover()) {
            g.setColor(mainColor.brighter()); // Brighten the main color when hovered
        } else {
            g.setColor(mainColor); // Use the main color
        }
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, curvature.width, curvature.height); // Draw rounded rectangle

        // Let the superclass paint the button text
        super.paintComponent(g);
    }

    /**
     * Overrides the paintBorder method to customize the button's border.
     * @param g The Graphics context used for painting
     */
    @Override
    protected void paintBorder(Graphics g) {
        // set stroke
        Graphics2D g2d = (Graphics2D) g;
        Stroke defaultStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(3));
        // Draw border
        g.setColor(accentColor); // Use the accent color for border
        g.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, curvature.width, curvature.height); // Draw rounded rectangle border
        // restore stroke
        g2d.setStroke(defaultStroke);
    }
}
