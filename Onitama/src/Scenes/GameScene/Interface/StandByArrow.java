package Onitama.src.Scenes.GameScene.Interface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;
import Onitama.src.Main;

public class StandByArrow extends MenuFrame {

    JLabel arrowLabel;

    private Dimension currentResolution;

    public StandByArrow(Dimension area, Vector2D offset) {
        super(area, offset);

        setMainColor(new Color(255,255,255,20));
        setAccentColor(new Color(255,255,255,8));
        setCurvature(10, 10);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        arrowLabel = new JLabel("< >");
        arrowLabel.setFont(Main.FontManager.getUnicodeCustomFont(Font.BOLD, 24));
        arrowLabel.setForeground(Main.Palette.foreground);

        arrowLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        add(Box.createVerticalGlue()); // Add vertical glue for top spacing
        add(arrowLabel);
        add(Box.createVerticalGlue()); // Add vertical glue for bottom spacing

        currentResolution = Main.engine.getResolution();
    }

    public void toggleLeftArrow() {
        arrowLabel.setText(" 🞀    ");
    }

    public void toggleRightArrow() {
        arrowLabel.setText("   🞂 ");
    }

    public void clearArrow() {
        arrowLabel.setText("");
    }

    @Override
    public void process(double delta) {
        if (!currentResolution.equals(Main.initialResolution)) {
            Vector2D resizeRatio = new Vector2D(
                (double) Main.engine.getResolution().width / (double) Main.initialResolution.width,
                (double) Main.engine.getResolution().height / (double) Main.initialResolution.height
            );
            arrowLabel.setFont(Main.FontManager.getUnicodeCustomFont(Font.BOLD, (int)(24 * resizeRatio.y)));
        }
        
        currentResolution = Main.engine.getResolution();
    }
    
}
