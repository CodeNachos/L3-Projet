package Onitama.src.Scenes.GameScene.Interface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPOutputStream;


import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Timer;

import Engine.Entities.UI.BlurredArea;
import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.MenuFrame;
import Engine.Global.Util;
import Engine.Structures.Vector2D;
import Onitama.src.Main;
import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.NewGameMenu.NewGameMenuScene;

public class InGameMenu extends MenuFrame {
    BlurredArea blurredArea;

    FlatButton restartButton;
    FlatButton resumeButton;
    FlatButton settingsButton;
    FlatButton quitButton;
    FlatButton newGameButton;
    FlatButton saveButton;
    JLabel saveMessage;
    Timer timer;

    private int processCount = 0;

    public InGameMenu(Dimension area, Vector2D offset) {
        
        super(area, offset);

        setMainColor(Main.Palette.selection);
        setAccentColor(Main.Palette.selection.brighter());
        setCurvature(20, 20);

        blurredArea = new BlurredArea(Main.engine.getResolution(), BlurredArea.captureBackground(Main.gameScene));
        
        Main.gameScene.addComponent(blurredArea);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalGlue());
        add(Box.createVerticalStrut(10));

        resumeButton = createBaseButton(" Resume ");
        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(resumeButton);

        add(Box.createVerticalStrut(6));
        
        restartButton = createBaseButton("Restart");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(restartButton);

        add(Box.createVerticalStrut(6));

        newGameButton = createBaseButton("New Game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(newGameButton);
        add(Box.createVerticalStrut(6));

        saveButton = createBaseButton("Save Game");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(saveButton);
        add(Box.createVerticalStrut(6));

        settingsButton = createBaseButton("Settings");
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(settingsButton);

        add(Box.createVerticalStrut(6));
        
        quitButton = createBaseButton("Quit");
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(quitButton);

        saveMessage = new JLabel(" ");
        saveMessage.setFont(Main.FontManager.getDefaultCustomFont(Font.ITALIC, 14));
        saveMessage.setForeground(Main.Palette.foreground);
        saveMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveMessage.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        saveMessage.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

        add(Box.createVerticalGlue());
        add(saveMessage);

        // Calculate the maximum width needed
        int maxWidth = saveButton.getPreferredSize().width + 40;

        // Set the maximum width for all buttons
        Dimension buttonSize = new Dimension(maxWidth, newGameButton.getPreferredSize().height);

        resumeButton.setPreferredSize(buttonSize);
        resumeButton.setMaximumSize(buttonSize);
        resumeButton.setMinimumSize(buttonSize);

        restartButton.setPreferredSize(buttonSize);
        restartButton.setMaximumSize(buttonSize);
        restartButton.setMinimumSize(buttonSize);

        newGameButton.setPreferredSize(buttonSize);
        newGameButton.setMaximumSize(buttonSize);
        newGameButton.setMinimumSize(buttonSize);

        saveButton.setPreferredSize(buttonSize);
        saveButton.setMaximumSize(buttonSize);
        saveButton.setMinimumSize(buttonSize);

        settingsButton.setPreferredSize(buttonSize);
        settingsButton.setMaximumSize(buttonSize);
        settingsButton.setMinimumSize(buttonSize);

        quitButton.setPreferredSize(buttonSize);
        quitButton.setMaximumSize(buttonSize);
        quitButton.setMinimumSize(buttonSize);

        
        timer = new Timer(2000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                saveMessage.setText(" ");
            }
        });
        timer.setRepeats(false);

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.engine.resume();
                removeMenu();
            }
            
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.engine.resume();
                Main.gameScene.history.resetGame();
                removeMenu();
            }
            
        });

        newGameButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                removeMenu();
                Main.engine.setCurrentScene(new NewGameMenuScene());
            }
            
            
        });

        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try (FileOutputStream fileOut = new FileOutputStream("Onitama/savefiles/game.save");
				GZIPOutputStream gzipOut = new GZIPOutputStream(new BufferedOutputStream(fileOut));
				ObjectOutputStream out = new ObjectOutputStream(gzipOut)) {
                    out.writeObject(Main.gameScene.getGameState());
                    out.writeObject(Main.gameScene.history);
                    saveMessage.setForeground(Main.Palette.green);
                    saveMessage.setText("Game saved!");
                    timer.start();
						
			    } catch (IOException e1) {
                    e1.printStackTrace();
                    saveMessage.setForeground(Main.Palette.orange);
                    saveMessage.setText("Error while saving game");
                    timer.start();
                }

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
        Main.engine.resume();
        Main.gameScene.removeComponent(blurredArea);
        Main.gameScene.removeComponent(this);
        Main.gameScene.setEnabledGUI(true);
        Main.gameScene.updateGUI();
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
        
        if (processCount == 2) {
            Main.engine.pause();
        }

        processCount++;
    }
    
}
