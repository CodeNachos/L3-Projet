package Engine.Entities.UI;

import javax.swing.*;

import Engine.Global.Util;

import java.awt.*;

public class FlatButton extends JButton {
    private Color mainColor = new Color(255,255,255,255);
    private Color accentColor = new Color(255,255,255,255);
    private Color textColor = new Color(0,0,0,255); 

    private Dimension curvature = new Dimension(0,0);

    public FlatButton(String text) {
        super(text);
        setContentAreaFilled(false); // Make the background transparent
        setBorder(BorderFactory.createLineBorder(accentColor)); // Set the border color
        setForeground(textColor); // Set text color to border color
    }

    public void setMainColor(Color color) {
        if (color == null) {
            Util.printError("null reference to color");
            return;
        }
        mainColor = color;
    }

    public void setAccentColor(Color color) {
        if (color == null) {
            Util.printError("null reference to color");
            return;
        }
        setBorder(BorderFactory.createLineBorder(accentColor));
        accentColor = color;
    }

    public void setTextColor(Color color) {
        if (color == null) {
            Util.printError("null reference to color");
            return;
        }
        textColor = color;
        setForeground(textColor); // Set text color to border color
    }

    public void setCurvature(int arcWidth, int arcHeight) {
        curvature.width = arcWidth;
        curvature.height = arcHeight;
    }



    @Override
    protected void paintComponent(Graphics g) {
        // Draw background
        if (getModel().isPressed()) {
            g.setColor(mainColor.darker());
        } else if (getModel().isRollover()) {
            g.setColor(mainColor.brighter());
        } else {
            g.setColor(mainColor);
        }
        g.fillRoundRect(0, 0, getWidth(), getHeight(), curvature.width, curvature.height);

        // Let the superclass paint the button text
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Draw border
        g.setColor(accentColor);
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1,curvature.width, curvature.height);
    }
}
