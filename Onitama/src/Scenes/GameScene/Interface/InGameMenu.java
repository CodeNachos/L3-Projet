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

    public InGameMenu(Dimension area, Vector2D offset) {
        
        super(area, offset);

        setMainColor(Main.Palette.selection);
        setAccentColor(Main.Palette.selection.brighter());
        setCurvature(20, 20);

        blurredArea = new BlurredArea(Main.engine.getResolution(), BlurredArea.captureBackground(Main.gameScene));
        
        Main.gameScene.addComponent(blurredArea);

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

        newGameButton = createBaseButton("New Game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(newGameButton);
        add(Box.createVerticalStrut(6));

        saveButton = createBaseButton("Save");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(saveButton);
        add(Box.createVerticalStrut(6));

        settingsButton = createBaseButton("Settings");
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(settingsButton);

        add(Box.createVerticalStrut(6));
        
        quitButton = createBaseButton("  Quit  ");
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(quitButton);

        add(Box.createVerticalGlue());

        saveMessage = new JLabel("Game successfully saved!");
        saveMessage.setForeground(Color.GREEN);
        saveMessage.setVisible(false);
        add(saveMessage);
        saveMessage.setAlignmentX(Component.LEFT_ALIGNMENT);
        saveMessage.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        timer = new Timer(2000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                saveMessage.setVisible(false);
            }
        });
        timer.setRepeats(false);

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

        newGameButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Main.engine.setCurrentScene(new NewGameMenuScene());
            }
            
            
        });

        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try (FileOutputStream fileOut = new FileOutputStream("Onitama/savefiles/gameSave1.txt");
				GZIPOutputStream gzipOut = new GZIPOutputStream(new BufferedOutputStream(fileOut));
				ObjectOutputStream out = new ObjectOutputStream(gzipOut)) {
                    out.writeObject(Main.gameScene.getGameState());
                    out.writeObject(GameScene.history);
                    saveMessage.setVisible(true);
                    timer.start();
						
			    } catch (IOException e1) {
                    // TODO Auto-generated catch block
                e1.printStackTrace();
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
