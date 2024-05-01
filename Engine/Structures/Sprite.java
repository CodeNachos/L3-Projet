package Engine.Structures;

import java.awt.*;

public class Sprite {
    private Image image;
    private Texture texture;

    private int borderWidth;
    private Color borderColor;
    private int borderCurvature;

    public Sprite(Image image) {
        this.image = image;
        this.texture = null;
        this.borderWidth = 0;
        this.borderColor = Color.BLACK;
    }

    public Sprite(Texture texture) {
        this.texture = texture;
        this.image = null;
        this.borderWidth = 0;
        this.borderColor = Color.BLACK;
    }

    public void setImage(Image image) {
        this.image = image;
        this.texture = null;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        this.image = null;
    }

    public int getWidth() {
        if (image != null) {
            return image.getWidth(null);
        }
        else if (texture != null) {
            return texture.getWidth();
        }
        return 0;
    }

    public int getHeight() {
        if (image != null) {
            return image.getHeight(null);
        }
        else if (texture != null) {
            return texture.getHeight();
        }
        return 0;
    }

    public void setBorder(int width, Color color) {
        this.borderWidth = width;
        this.borderColor = color;
    }

    public void setBorder(int width, Color color, int curvature) {
        this.borderWidth = width;
        this.borderColor = color;
        this.borderCurvature = curvature;
    }

    public void drawSprite(Graphics g, int x, int y, int width, int height) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        } else if (texture != null) {
            texture.draw(g, x+borderWidth, y+borderWidth, width-2*borderWidth, height-2*borderWidth);
        }

        // Draw border
        if (borderWidth > 0) {
            g.setColor(borderColor);
            ((Graphics2D)g).setStroke(new BasicStroke(borderWidth));
            g.drawRoundRect(x+borderWidth, y+borderWidth, width-2*borderWidth, height-2*borderWidth, borderCurvature, borderCurvature);
        }
    }
}
