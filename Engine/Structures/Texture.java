package Engine.Structures;

import java.awt.*;

public class Texture {
    private Dimension area;
    private Color color;

    private int curvature;

    public Texture(Color color, int width, int height) {
        this.color = color;
        this.area = new Dimension(width, height);
        this.curvature = 0;
    }

    public Texture(Color color, int width, int height, int curvature) {
        this.color = color;
        this.area = new Dimension(width, height);
        this.curvature = curvature;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSize(int width, int height) {
        area.width = width;
        area.height = height;
    }

    public void setSize(Dimension area) {
        this.area = area;
    }

    public int getWidth() {
        return area.width;
    }

    public int getHeight() {
        return area.height;
    }

    public Color getColor() {
        return color;
    }

    public Dimension getSize() {
        return area;
    }

    public void setCurvature(int curvature) {
        this.curvature = curvature;
    }

    public void draw(Graphics g, int x, int y, int width, int height) {
        // Draw texture
        g.setColor(color);
        g.fillRoundRect(x, y, width, height, curvature, curvature);

    }
}
