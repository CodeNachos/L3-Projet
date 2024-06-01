package Onitama.src.Scenes.MainMenuScene;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

class BackgroundPanel extends JPanel {
    private final BufferedImage image;

    public BackgroundPanel(BufferedImage image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}

