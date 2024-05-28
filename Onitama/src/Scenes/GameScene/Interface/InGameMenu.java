package Onitama.src.Scenes.GameScene.Interface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

import Engine.Entities.UI.BlurredArea;
import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.MenuFrame;
import Engine.Global.Util;
import Engine.Structures.Vector2D;
import Onitama.src.Main;

public class InGameMenu extends MenuFrame {
    BlurredArea blurredArea;

    FlatButton restartButton;
    FlatButton resumeButton;
    FlatButton settingsButton;
    FlatButton quitButton;

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
        
        resumeButton = createBaseButton(" Resume ");
        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(resumeButton);

        add(Box.createVerticalStrut(6));
        
        restartButton = createBaseButton("Restart ");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(restartButton);

        add(Box.createVerticalStrut(6));

        settingsButton = createBaseButton("Settings");
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(settingsButton);

        add(Box.createVerticalStrut(6));
        
        quitButton = createBaseButton("  Quit  ");
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(quitButton);

        add(Box.createVerticalGlue());

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeMenu();
            }
            
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.gameScene.history.resetGame();
                removeMenu();
            }
            
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.engine.stop();
            }
            
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Util.printWarning("Chill, thats for tomorrow");
            }
            
        });
    }

    @Override
    public void input(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            removeMenu();
        }
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
    
}
