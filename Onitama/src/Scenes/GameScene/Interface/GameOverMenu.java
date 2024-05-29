package Onitama.src.Scenes.GameScene.Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import Engine.Entities.UI.BlurredArea;
import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;
import Onitama.src.Main;

public class GameOverMenu extends MenuFrame {
    
    BlurredArea blurredArea;

    JLabel winnerLabel;

    public GameOverMenu( Dimension area, Vector2D offset) {
        super(area, offset);

        setMainColor(Main.Palette.selection);
        setAccentColor(Main.Palette.selection.brighter());
        setCurvature(20, 20);

        blurredArea = new BlurredArea(Main.engine.getResolution(), BlurredArea.captureBackground(Main.gameScene));
    
        Main.gameScene.addComponent(blurredArea);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalGlue());

        createWinnerLabel();

        add(Box.createVerticalGlue());
    }

    private void createWinnerLabel() {
        winnerLabel = new JLabel("GAME OVER!");
        winnerLabel.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 32));
        winnerLabel.setForeground(Main.Palette.foreground);

        winnerLabel.setAlignmentX(CENTER_ALIGNMENT);

        add(winnerLabel);
    }

    private void removeMenu() {
        Main.gameScene.removeComponent(blurredArea);
        Main.gameScene.removeComponent(this);
        Main.gameScene.setEnabledGUI(true);
    }

    private FlatButton createBaseButton(String content) {
        FlatButton button = new FlatButton(content);
        button.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 16));
        button.setForeground(Main.Palette.foreground);
        button.setMainColor(Main.Palette.selection.brighter());
        button.setAccentColor(new Color(255,255,255,25));
        button.setCurvature(20, 20);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setSize(20, 20);
        button.setFocusable(false);
        return button;
    }

    
    @Override
    public void process(double delta) {
        if (getParent().getComponentZOrder(this) > 1) {
            getParent().setComponentZOrder(blurredArea, 1);
            getParent().setComponentZOrder(this, 0);
        }
    }
    
}
