package Engine.Entities.UI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import Engine.Structures.Vector2D;

public class BlurredArea extends UIObject {
    // Define a stronger blur kernel (5x5)
    float[] blurKernel = {
        1/25f, 1/25f, 1/25f, 1/25f, 1/25f,
        1/25f, 1/25f, 1/25f, 1/25f, 1/25f,
        1/25f, 1/25f, 1/25f, 1/25f, 1/25f,
        1/25f, 1/25f, 1/25f, 1/25f, 1/25f,
        1/25f, 1/25f, 1/25f, 1/25f, 1/25f
    };

    private BufferedImage blurredBackground;
    
    public BlurredArea(Dimension area, BufferedImage background) {
        super(area);
        setOpaque(false); // Make sure the panel is non-opaque
        this.blurredBackground = applyBlurEffect(background);
    }
    
    public BlurredArea(Dimension area, Vector2D position, BufferedImage background) {
        super(area, position);
        setOpaque(false); // Make sure the panel is non-opaque
        this.blurredBackground = applyBlurEffect(background);
    }

    private BufferedImage applyBlurEffect(BufferedImage image) {
        BufferedImage blurredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dBlur = blurredImage.createGraphics();
        ConvolveOp blur = new ConvolveOp(new Kernel(5, 5, blurKernel), ConvolveOp.EDGE_NO_OP, null);
        g2dBlur.drawImage(image, blur, 0, 0);
        g2dBlur.dispose();
        return blurredImage;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (blurredBackground != null) {
            g.drawImage(blurredBackground, 0, 0, getWidth(), getHeight(), null);
        }
        super.paintComponent(g);
    }

    public static BufferedImage captureBackground(Component component) {
        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        component.paint(g2d);
        g2d.dispose();
        return image;
    }
    
}
