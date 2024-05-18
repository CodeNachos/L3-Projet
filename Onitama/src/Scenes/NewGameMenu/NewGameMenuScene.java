package Onitama.src.Scenes.NewGameMenu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.ColorArea;
import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;
import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Main;

public class NewGameMenuScene extends Scene {

    ArrayList<String> playerClasses;

    String classPlayer1;
    String classPlayer2;

    public NewGameMenuScene() {

        playerClasses = new ArrayList<>();
        playerClasses.add("HUMAN");
        playerClasses.add("AI EASY");
        playerClasses.add("AI MEDIUM");
        playerClasses.add("AI HARD");

       createSelectionMenu();
       createStartButton();
       createMainMenuButton();

        // Add background
        ColorArea background = new ColorArea(Main.Palette.background, new Dimension(Main.engine.getResolution().width, Main.engine.getResolution().height));
        addComponent(background);
    }

    public String getPlayerClass(int player) {
        switch (player) {
            case GameScene.PLAYER1:
                return classPlayer1;
                
            case GameScene.PLAYER2:
                return classPlayer2;

            default:
                return null;
        }
    }

    private void createSelectionMenu() {
        // Compute field area
        Dimension menuArea = new Dimension(
            (int)(Main.engine.getResolution().width /3),
            (int)(Main.engine.getResolution().height/2)
        );

        Vector2D menuOffset = new Vector2D(
            (int)((Main.engine.getResolution().width/2) - (menuArea.width/2)),
            (int)((Main.engine.getResolution().height/2) - (menuArea.height/2))
        );

        MenuFrame selectionMenu = new MenuFrame(menuArea, menuOffset);
        
        selectionMenu.setMainColor(Main.Palette.selection.darker());
        selectionMenu.setAccentColor(Main.Palette.selection.darker());

        GridBagConstraints gbc = new GridBagConstraints();
        selectionMenu.setLayout(new GridBagLayout());
        
        JLabel menuTitle = new JLabel("CHOOSE PLAYERS");
        menuTitle.setFont(new Font("Arial", Font.BOLD, 18));
        menuTitle.setForeground(Main.Palette.foreground);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10,10,20,10);
        selectionMenu.add(menuTitle, gbc);

        FlatButton leftButtonPlayer1 = new FlatButton("  <  ");
        leftButtonPlayer1.setFont(new Font("Arial", Font.BOLD, 18));
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

        
        JLabel player1Label = new JLabel(playerClasses.get(0), SwingConstants.CENTER);
        player1Label.setFont(new Font("Arial", Font.BOLD, 14));
        player1Label.setForeground(Main.Palette.red);
        player1Label.setPreferredSize(new Dimension(80,20));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.ipady = 0;
        selectionMenu.add(player1Label, gbc);

        FlatButton rightButtonPlayer1 = new FlatButton("  >  ");
        rightButtonPlayer1.setFont(new Font("Arial", Font.BOLD, 18));
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

        FlatButton leftButtonPlayer2 = new FlatButton("  <  ");
        leftButtonPlayer2.setFont(new Font("Arial", Font.BOLD, 18));
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

        
        JLabel player2Label = new JLabel(playerClasses.get(2), SwingConstants.CENTER);
        player2Label.setFont(new Font("Arial", Font.BOLD, 14));
        player2Label.setForeground(Main.Palette.cyan);
        player2Label.setPreferredSize(new Dimension(80,20));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.ipady = 0;
        selectionMenu.add(player2Label, gbc);

        FlatButton rightButtonPlayer2 = new FlatButton("  >  ");
        rightButtonPlayer2.setFont(new Font("Arial", Font.BOLD, 18));
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


        leftButtonPlayer1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int index = playerClasses.indexOf(player1Label.getText());
                index--;
                if (index < 0)
                    index = playerClasses.size()-1;
                player1Label.setText(playerClasses.get(index));
                classPlayer1 = playerClasses.get(index);
            }
            
        });

        rightButtonPlayer1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int index = playerClasses.indexOf(player1Label.getText());
                index = (index + 1) % playerClasses.size();
                player1Label.setText(playerClasses.get(index));
                classPlayer1 = playerClasses.get(index);
            }
            
        });

        leftButtonPlayer2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int index = playerClasses.indexOf(player2Label.getText());
                index--;
                if (index < 0)
                    index = playerClasses.size()-1;
                player2Label.setText(playerClasses.get(index));
                classPlayer2 = playerClasses.get(index);
            }
            
        });

        rightButtonPlayer2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int index = playerClasses.indexOf(player2Label.getText());
                index = (index + 1) % playerClasses.size();
                player2Label.setText(playerClasses.get(index));
                classPlayer2 = playerClasses.get(index);
            }
            
        });

        classPlayer1 = playerClasses.get(0);
        classPlayer2 = playerClasses.get(2);

        
        addComponent(selectionMenu);
        addComponent(new ColorArea(new Color(0,0,0,15), menuArea, menuOffset.add(new Vector2D(8,8))));
    }

    private void createStartButton() {
        FlatButton startButton = new FlatButton("START GAME");
        startButton.setFont(new Font("Arial", Font.BOLD, 22));
        startButton.setForeground(Main.Palette.foreground);
        startButton.setMainColor(Main.Palette.selection);
        startButton.setAccentColor(Main.Palette.selection.brighter());
        startButton.setCurvature(15, 15);
        startButton.setBorder(BorderFactory.createEmptyBorder());
        startButton.setFocusable(false);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.gameScene = new GameScene();
                if (!getPlayerClass(GameScene.PLAYER1).equals(playerClasses.get(0))) {
                    if (getPlayerClass(GameScene.PLAYER1).equals(playerClasses.get(1))) {
                        Main.gameScene.enablePlayerAI(GameScene.PLAYER1, 1);
                    } else if (getPlayerClass(GameScene.PLAYER1).equals(playerClasses.get(2))) {
                        Main.gameScene.enablePlayerAI(GameScene.PLAYER1, 3);
                    } else if (getPlayerClass(GameScene.PLAYER1).equals(playerClasses.get(3))) {
                        Main.gameScene.enablePlayerAI(GameScene.PLAYER1, 5);
                    }
                }

                if (!getPlayerClass(GameScene.PLAYER2).equals(playerClasses.get(0))) {
                    if (getPlayerClass(GameScene.PLAYER2).equals(playerClasses.get(1))) {
                        Main.gameScene.enablePlayerAI(GameScene.PLAYER2, 1);
                    } else if (getPlayerClass(GameScene.PLAYER2).equals(playerClasses.get(2))) {
                        Main.gameScene.enablePlayerAI(GameScene.PLAYER2, 3);
                    } else if (getPlayerClass(GameScene.PLAYER2).equals(playerClasses.get(3))) {
                        Main.gameScene.enablePlayerAI(GameScene.PLAYER2, 5);
                    }
                }

                Main.engine.setCurrentScene(Main.gameScene);
            }
            
        });

        Dimension buttonArea = new Dimension(
            (int)(Main.engine.getResolution().width /5),
            (int)(Main.engine.getResolution().height/7)
        );

        Vector2D buttonOffset = new Vector2D(
            (int)((5*Main.engine.getResolution().width/6) - (buttonArea.width/2)),
            (int)((Main.engine.getResolution().height/2) - (buttonArea.height/2))
        );

        MenuFrame buttonFrame = new MenuFrame(buttonArea, buttonOffset);

        buttonFrame.setMainColor(new Color(100,0,0,255));
        buttonFrame.setAccentColor(new Color(0,0,0,0));
        
        buttonFrame.setLayout(new GridLayout());

        buttonFrame.add(startButton);

        addComponent(buttonFrame);
    }

    private void createMainMenuButton() {
        FlatButton mainMenuButton = new FlatButton("MAIN MENU");
        mainMenuButton.setFont(new Font("Arial", Font.BOLD, 14));
        mainMenuButton.setForeground(Main.Palette.foreground);
        mainMenuButton.setMainColor(Main.Palette.selection.darker());
        mainMenuButton.setAccentColor(Main.Palette.selection);
        mainMenuButton.setCurvature(15, 15);
        mainMenuButton.setBorder(BorderFactory.createEmptyBorder());
        mainMenuButton.setFocusable(false);

        Dimension buttonArea = new Dimension(
            (int)(Main.engine.getResolution().width /8),
            (int)(Main.engine.getResolution().height/10)
        );

        Vector2D buttonOffset = new Vector2D(
            (int)((1*Main.engine.getResolution().width/6) - (buttonArea.width/2)),
            (int)((Main.engine.getResolution().height/2) - (buttonArea.height/2))
        );

        MenuFrame buttonFrame = new MenuFrame(buttonArea, buttonOffset);

        buttonFrame.setMainColor(new Color(100,0,0,255));
        buttonFrame.setAccentColor(new Color(0,0,0,0));
        
        buttonFrame.setLayout(new GridLayout());

        buttonFrame.add(mainMenuButton);

        addComponent(buttonFrame);
    }
}
