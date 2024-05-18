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

    public StandByArrow(Dimension area, Vector2D offset) {
        super(area, offset);

        setMainColor(new Color(255,255,255,20));
        setAccentColor(new Color(255,255,255,8));
        setCurvature(10, 10);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        arrowLabel = new JLabel("< >");
        arrowLabel.setFont(new Font("Cascadia Mono", Font.BOLD, 24));
        arrowLabel.setForeground(Main.Palette.foreground);

        arrowLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        add(Box.createVerticalGlue()); // Add vertical glue for top spacing
        add(arrowLabel);
        add(Box.createVerticalGlue()); // Add vertical glue for bottom spacing
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
    
}
