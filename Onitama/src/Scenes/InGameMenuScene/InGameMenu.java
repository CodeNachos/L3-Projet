package Onitama.src.Scenes.InGameMenuScene;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.BlurredArea;
import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.MenuFrame;
import Engine.Global.Util;
import Engine.Structures.Vector2D;
import Onitama.src.Main;
import Onitama.src.Scenes.GameScene.Scripts.States.Config;
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

    private Config newConfig;

    public InGameMenu(Dimension area, Vector2D offset) {
        
        super(area, offset);

        setMainColor(Main.Palette.selection);
        setAccentColor(Main.Palette.selection.brighter());
        setCurvature(20, 20);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        resumeButton = createBaseButton(" Resume ");
        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        restartButton = createBaseButton("Restart");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        newGameButton = createBaseButton("New Game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        saveButton = createBaseButton("Save Game");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainButton = createBaseButton("Main Menu");
        mainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
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

        saveMessage = new JLabel(" ");
        saveMessage.setFont(Main.FontManager.getDefaultCustomFont(Font.ITALIC, 14));
        saveMessage.setForeground(Main.Palette.foreground);
        saveMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveMessage.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        saveMessage.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(new Color(0,0,0,0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10,45,10,45));

        buttonsPanel.add(Box.createVerticalGlue());
        buttonsPanel.add(Box.createVerticalStrut(10));
        buttonsPanel.add(resumeButton);
        buttonsPanel.add(Box.createVerticalStrut(6));
        buttonsPanel.add(restartButton);
        buttonsPanel.add(Box.createVerticalStrut(6));
        buttonsPanel.add(newGameButton);
        buttonsPanel.add(Box.createVerticalStrut(6));
        buttonsPanel.add(saveButton);
        buttonsPanel.add(Box.createVerticalStrut(6));
        buttonsPanel.add(mainButton);
        buttonsPanel.add(Box.createVerticalGlue());

        JPanel selectionMenu = new JPanel();
        selectionMenu.setBackground(new Color(0,0,0,0));

        GridBagConstraints gbc = new GridBagConstraints();
        selectionMenu.setLayout(new GridBagLayout());
        
        JLabel menuTitle = new JLabel("Configure Players");
        menuTitle.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 22));
        menuTitle.setForeground(Main.Palette.foreground);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10,10,20,10);
        selectionMenu.add(menuTitle, gbc);

        newConfig = Main.gameScene.getGameConfig();

        FlatButton leftButtonPlayer1 = new FlatButton("  🞀  ");
        leftButtonPlayer1.setFont(Main.FontManager.getUnicodeCustomFont(Font.BOLD, 18));
        leftButtonPlayer1.setForeground(Main.Palette.foreground);
        leftButtonPlayer1.setMainColor(Main.Palette.selection);
        leftButtonPlayer1.setAccentColor(Main.Palette.selection.brighter());
        leftButtonPlayer1.setCurvature(10, 10);
        leftButtonPlayer1.setBorder(BorderFactory.createEmptyBorder());
        leftButtonPlayer1.setFocusable(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.ipady = 10;
        gbc.insets = new Insets(10,10,10,10);
        selectionMenu.add(leftButtonPlayer1, gbc);

        
        JLabel player1Label = new JLabel(newConfig.redDifficulty.toString(), SwingConstants.CENTER);
        player1Label.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 18));
        player1Label.setForeground(Main.Palette.red);
        player1Label.setPreferredSize(new Dimension(100,40));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.ipady = 0;
        selectionMenu.add(player1Label, gbc);

        FlatButton rightButtonPlayer1 = new FlatButton("  🞂  ");
        rightButtonPlayer1.setFont(Main.FontManager.getUnicodeCustomFont(Font.BOLD, 18));
        rightButtonPlayer1.setForeground(Main.Palette.foreground);
        rightButtonPlayer1.setMainColor(Main.Palette.selection);
        rightButtonPlayer1.setAccentColor(Main.Palette.selection.brighter());
        rightButtonPlayer1.setCurvature(10, 10);
        rightButtonPlayer1.setBorder(BorderFactory.createEmptyBorder());
        rightButtonPlayer1.setFocusable(false);
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.ipady = 10;
        selectionMenu.add(rightButtonPlayer1, gbc);

        FlatButton leftButtonPlayer2 = new FlatButton("  🞀  ");
        leftButtonPlayer2.setFont(Main.FontManager.getUnicodeCustomFont(Font.BOLD, 18));
        leftButtonPlayer2.setForeground(Main.Palette.foreground);
        leftButtonPlayer2.setMainColor(Main.Palette.selection);
        leftButtonPlayer2.setAccentColor(Main.Palette.selection.brighter());
        leftButtonPlayer2.setCurvature(10, 10);
        leftButtonPlayer2.setBorder(BorderFactory.createEmptyBorder());
        leftButtonPlayer2.setFocusable(false);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.ipady = 10;
        selectionMenu.add(leftButtonPlayer2, gbc);

        
        JLabel player2Label = new JLabel(newConfig.blueDifficulty.toString(), SwingConstants.CENTER);
        player2Label.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 18));
        player2Label.setForeground(Main.Palette.cyan);
        player2Label.setPreferredSize(new Dimension(100,40));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.ipady = 0;
        selectionMenu.add(player2Label, gbc);

        FlatButton rightButtonPlayer2 = new FlatButton("  🞂  ");
        rightButtonPlayer2.setFont(Main.FontManager.getUnicodeCustomFont(Font.BOLD, 18));
        rightButtonPlayer2.setForeground(Main.Palette.foreground);
        rightButtonPlayer2.setMainColor(Main.Palette.selection);
        rightButtonPlayer2.setAccentColor(Main.Palette.selection.brighter());
        rightButtonPlayer2.setCurvature(10, 10);
        rightButtonPlayer2.setBorder(BorderFactory.createEmptyBorder());
        rightButtonPlayer2.setFocusable(false);
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.ipady = 10;
        selectionMenu.add(rightButtonPlayer2, gbc);

        FlatButton applyConfigButton = createBaseButton("Apply");
        applyConfigButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.ipady = 10;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        selectionMenu.add(applyConfigButton, gbc);
        
        JPanel menuContent = new JPanel();
        menuContent.setLayout(new BoxLayout(menuContent, BoxLayout.X_AXIS));
        menuContent.setBackground(new Color(0,0,0,0));

        menuContent.add(Box.createHorizontalGlue());
        menuContent.add(buttonsPanel);
        menuContent.add(selectionMenu);
        menuContent.add(Box.createHorizontalGlue());

        add(Box.createVerticalGlue());
        add(menuContent);
        add(saveMessage);
        add(Box.createVerticalGlue());
        
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

        
        leftButtonPlayer1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                newConfig.redDifficulty = newConfig.redDifficulty.previous();
                player1Label.setText(newConfig.redDifficulty.toString());
            }
            
        });

        rightButtonPlayer1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                newConfig.redDifficulty = newConfig.redDifficulty.next();
                player1Label.setText(newConfig.redDifficulty.toString());
            }
            
        });

        leftButtonPlayer2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                newConfig.blueDifficulty = newConfig.blueDifficulty.previous();
                player2Label.setText(newConfig.blueDifficulty.toString());
            }
            
        });

        rightButtonPlayer2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                newConfig.blueDifficulty = newConfig.blueDifficulty.next();
                player2Label.setText(newConfig.blueDifficulty.toString());
            }
            
        });

        applyConfigButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Main.gameScene.setGameConfig(newConfig);
                Main.engine.setCurrentScene(Main.gameScene);
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
