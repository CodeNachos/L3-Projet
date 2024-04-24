package Engine.Entities.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;


import Engine.Structures.Vector2D;

public class ColorArea extends UIObject {
    public Color color = Color.white;

    public ColorArea(Color color, Dimension area, Vector2D offset) {
        super(area, offset);
        this.color = color;
    }

    public ColorArea(Color color, Dimension area) {
        super(area);
        this.color = color;
    }

    public ColorArea(Dimension area) {
        super(area);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(color);
        g.fillRect(position.getIntX(), position.getIntY(), getWidth(), getHeight());
    }
}
