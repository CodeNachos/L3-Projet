package Onitama.src.Scenes.GameScene.Entities.Board;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import javax.swing.*;

public class Arrow extends JComponent{

    Timer timer;
    int fromX;
    int fromY;
    int toX;
    int toY;
    boolean drawArrow = true;

    Arrow(int frX, int frY, int tX, int tY)
    {
        this.fromX = frX;
        this.fromY = frY;
        this.toX = tX;
        this.toY = tY;
        timer = new Timer(4000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                drawArrow = false;
                repaint();
            }
            
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (drawArrow)
        {
            drawArrow(g2d, fromX, fromY, toX, toY);
        }
        
        
    }

     private void drawArrow(Graphics2D g2d, int fromX, int fromY, int toX, int toY) {
        int arrowSize = 10;

        // Draw line
        g2d.drawLine(fromX, fromY, toX, toY);

        // Draw arrow head
        double angle = Math.atan2(toY - fromY, toX - fromX);

        AffineTransform tx1 = g2d.getTransform();
        AffineTransform tx2 = g2d.getTransform();

        g2d.translate(toX, toY);
        g2d.rotate((angle - Math.PI / 2d));

        g2d.fillPolygon(new int[]{0, arrowSize / 2, -arrowSize / 2},
                new int[]{0, -arrowSize, -arrowSize}, 3);

        g2d.setTransform(tx1);

        g2d.translate(fromX, fromY);
        g2d.rotate((angle - Math.PI / 2d - Math.PI));

        g2d.fillPolygon(new int[]{0, arrowSize / 2, -arrowSize / 2},
                new int[]{0, -arrowSize, -arrowSize}, 3);

        g2d.setTransform(tx2);
    }
}
