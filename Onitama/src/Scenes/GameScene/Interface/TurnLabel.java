package Onitama.src.Scenes.GameScene.Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;
import Onitama.src.Main;

public class TurnLabel extends MenuFrame {

    JLabel turnLabel;

    private int counter;
    private int player;

    private static String[] labels = {"   Red to play", "    Blue to play"};

    public TurnLabel(Dimension area, Vector2D offset) {
        super(area, offset);

        setMainColor(new Color(255,255,255,10));
        setAccentColor(new Color(255,255,255,15));
        setCurvature(10, 10);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        turnLabel = new JLabel("LABEL");
        turnLabel.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 18));
        turnLabel.setForeground(Main.Palette.foreground);
        

        add(Box.createVerticalGlue()); // Add vertical glue for top spacing
        add(turnLabel);
        add(Box.createVerticalGlue()); // Add vertical glue for bottom spacing
    }

    public void setBlueTurn() {
        player = 1;
        turnLabel.setText(labels[player]);
        counter = 30;
    }

    public void setRedTurn() {
        player = 0;
        turnLabel.setText(labels[player]);
        counter = 30;
    }

    public void clearTurn() {
        turnLabel.setText("");
    }

    @Override
    public void process(double delta) {
        if (counter <= 0) {
            if (!turnLabel.getText().isEmpty()) {
                if (turnLabel.getText().length() > 35)
                    turnLabel.setText(labels[player]);
                turnLabel.setText(turnLabel.getText() + ".");
            }
            counter = 30;
        } else {
            counter--;
        }
    }
    
}
