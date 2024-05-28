package Onitama.src.Scenes.GameScene.Interface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

import Engine.Entities.UI.BlurredArea;
import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;
import Onitama.src.Main;

public class InGameMenu extends MenuFrame {
    BlurredArea blurredArea;

    public InGameMenu(Dimension area, Vector2D offset) {
        
        super(area, offset);

        setMainColor(Main.Palette.selection);
        setAccentColor(Main.Palette.selection.brighter());
        setCurvature(20, 20);

        blurredArea = new BlurredArea(Main.engine.getResolution(), BlurredArea.captureBackground(Main.gameScene));
        
        Main.gameScene.addComponent(blurredArea);

        Main.gameScene.setComponentZOrder(blurredArea, 0);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalGlue());

        FlatButton resumeButton = createBaseButton(" Resume ");
        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(resumeButton);

        add(Box.createVerticalStrut(6));

        
        FlatButton restartButton = createBaseButton("Restart ");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(restartButton);

        add(Box.createVerticalStrut(6));
        
        FlatButton quitButton = createBaseButton("  Quit  ");
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(quitButton);

        

        add(Box.createVerticalGlue());
    }

    @Override
    public void input(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_Q) {
            Main.gameScene.removeComponent(blurredArea);
            Main.gameScene.removeComponent(this);
            Main.gameScene.setEnabledGUI(true);
        }
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
    
}
