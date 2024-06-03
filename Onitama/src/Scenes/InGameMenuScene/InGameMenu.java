package Onitama.src.Scenes.InGameMenuScene;

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

import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.BlurredArea;
import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.MenuFrame;
import Engine.Global.Util;
import Engine.Structures.Vector2D;
import Onitama.src.Main;
import Onitama.src.Scenes.InGameMenuScene.InGameMenuScene.MenuActions;
import Onitama.src.Scenes.MainMenuScene.MainMenuScene;
import Onitama.src.Scenes.NewGameMenu.NewGameMenuScene;

public class InGameMenu extends MenuFrame {
    BlurredArea blurredArea;

    FlatButton restartButton;
    FlatButton resumeButton;
    FlatButton mainButton;
    FlatButton newGameButton;
    FlatButton saveButton;
    JLabel saveMessage;
    Timer timer;

    private boolean firstProcess = true;

    public InGameMenu(Dimension area, Vector2D offset) {
        
        super(area, offset);

        setMainColor(Main.Palette.selection);
        setAccentColor(Main.Palette.selection.brighter());
        setCurvature(20, 20);

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
        
        mainButton = createBaseButton("Main Menu");
        mainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(mainButton);

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

        mainButton.setPreferredSize(buttonSize);
        mainButton.setMaximumSize(buttonSize);
        mainButton.setMinimumSize(buttonSize);

        
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
                Main.engine.setCurrentScene(Main.gameScene);
            }
            
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createConfirmationMenu(MenuActions.RESTART);
                removeMenu();
            }
            
        });

        newGameButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                createConfirmationMenu(MenuActions.NEW_GAME);
                removeMenu();
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

        mainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createConfirmationMenu(MenuActions.MAIN_MENU);
                removeMenu();
            }
            
        });
    }

    @Override
    public void input(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Main.engine.setCurrentScene(Main.gameScene);
        }
    }

    @Override
    public void process(double delta) {
        if (firstProcess) {
            getParent().setComponentZOrder(this, 0);
            firstProcess = false;
        }
    }

    private void removeMenu() {
        ((Scene)getParent()).removeComponent(this);
        firstProcess = true;
    }

    private void createConfirmationMenu(MenuActions action) {
        Dimension menuArea = new Dimension(
            (int)(Main.engine.getResolution().width/3),
            (int)(Main.engine.getResolution().width/5)
        );
    
        Vector2D menuOffset = new Vector2D(
            (Main.engine.getResolution().width/2) - (menuArea.width/2),
            (Main.engine.getResolution().height/2) - (menuArea.height/2)
        );
        
        MenuFrame confirmationFrame = new ConfirmationFrame(this, action, menuArea, menuOffset);
        ((Scene)getParent()).addComponent(confirmationFrame);
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
