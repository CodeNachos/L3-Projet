package WaffleGame.src;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class Background extends JComponent {
    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("a");
        super.paintComponent(g);

        // Set color for the rectangle
        g.setColor(Color.RED);
        
        // Draw a filled rectangle
        g.fillRect(50, 50, 100, 100); // (x, y, width, height)
    }
}
