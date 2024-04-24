package Engine.Entities.UI;

import java.awt.*;

import javax.swing.*;

import Engine.Structures.Vector2D;
    
public class MenuFrame extends UIObject {
    private Color mainColor = new Color(255,255,255,255);
    private Color accentColor = new Color(255,255,255,255);
    private Dimension curvature = new Dimension(0,0);

    public MenuFrame(Dimension area, Vector2D offset) {
        super(area, offset);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public MenuFrame(Dimension area) {
        super(area);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void setMainColor(Color color) {
        mainColor = color;
    }

    public void setAccentColor(Color color) {
        accentColor = color;
    }

    public void setCurvature(int arcWidth, int arcHeight) {
        curvature.width = arcWidth;
        curvature.height = arcHeight;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // set stroke
        Graphics2D g2d = (Graphics2D) g;
        Stroke defaultStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(3));
        
        // paint background
        g2d.setColor(mainColor);
        g2d.fillRoundRect(2, 2, getWidth()-4, getHeight()-4, curvature.width, curvature.height);
        
        // paint borders in accent color
        g.setColor(accentColor);
        g2d.drawRoundRect(2, 2, getWidth()-4, getHeight()-4, curvature.width, curvature.height);
        
        // restore stroke
        g2d.setStroke(defaultStroke);
    }

    @Override
    public void resize(Vector2D ratio) {
        Vector2D updatedValues = new Vector2D();
        // set relative position
        updatedValues.setCoord(position.x * ratio.x, position.y * ratio.y);
        setPos(updatedValues);
        // set relative scaling
        updatedValues.setCoord(scale.x * ratio.x, scale.y * ratio.y);
        setScale(updatedValues);

        for (Component c : getComponents()) {
            Font font;
            if (c instanceof JLabel) {
                font = ((JLabel)c).getFont();
                ((JLabel)c).setFont(font.deriveFont(font.getStyle(), (float)(font.getSize() * ratio.y)));
            } else if (c instanceof JButton) {
                font = ((JButton)c).getFont();
                ((JButton)c).setFont(font.deriveFont(font.getStyle(), (float)(font.getSize() * ratio.y)));
            }  
        }
    }
    
}
