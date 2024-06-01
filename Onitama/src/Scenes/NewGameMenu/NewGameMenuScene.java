package Onitama.src.Scenes.NewGameMenu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.ColorArea;
import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.FlatToggleButton;
import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;
import Onitama.src.Scenes.GameScene.Constants;
import Onitama.src.Scenes.GameScene.Constants.PlayerType;
import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Scripts.States.Config;
import Onitama.src.Scenes.MainMenuScene.MainMenuScene;
import Onitama.src.Main;

public class NewGameMenuScene extends Scene implements ItemListener {

    Config gameConfig = new Config(PlayerType.HUMAN, PlayerType.HUMAN, Constants.RED_PLAYER);
    
    FlatToggleButton redFirstButton;
    FlatToggleButton blueFirstButton;

    public NewGameMenuScene() {
       createSelectionMenu();
       createStartButton();
       createMainMenuButton();

        // Add background
        ColorArea background = new ColorArea(Main.Palette.background, new Dimension(Main.engine.getResolution().width, Main.engine.getResolution().height));
        addComponent(background);
    }

    public NewGameMenuScene(Config config) {
        gameConfig = config.clone();

        createSelectionMenu();
        createStartButton();
        createMainMenuButton();

        // Add background
        ColorArea background = new ColorArea(Main.Palette.background, new Dimension(Main.engine.getResolution().width, Main.engine.getResolution().height));
        addComponent(background);
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
        selectionMenu.setCurvature(30, 30);

        GridBagConstraints gbc = new GridBagConstraints();
        selectionMenu.setLayout(new GridBagLayout());
        
        JLabel menuTitle = new JLabel("Choose Players");
        menuTitle.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 22));
        menuTitle.setForeground(Main.Palette.foreground);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10,10,20,10);
        selectionMenu.add(menuTitle, gbc);

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

        
        JLabel player1Label = new JLabel(gameConfig.redDifficulty.toString(), SwingConstants.CENTER);
        player1Label.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 14));
        player1Label.setForeground(Main.Palette.red);
        player1Label.setPreferredSize(new Dimension(80,20));
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

        
        JLabel player2Label = new JLabel(gameConfig.blueDifficulty.toString(), SwingConstants.CENTER);
        player2Label.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 14));
        player2Label.setForeground(Main.Palette.cyan);
        player2Label.setPreferredSize(new Dimension(80,20));
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

        JLabel firstTitle = new JLabel("First Player");
        firstTitle.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 22));
        firstTitle.setForeground(Main.Palette.foreground);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10,10,5,10);
        selectionMenu.add(firstTitle, gbc);

        // Create the panel to hold the toggle buttons
        JPanel toggleButtonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints panelGbc = new GridBagConstraints();
        toggleButtonPanel.setBackground(new Color(0,0,0,0));
        // Create the first toggle button
        redFirstButton = new FlatToggleButton("red", true);
        redFirstButton.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 14));
        redFirstButton.setMainColor(Main.Palette.selection);
        redFirstButton.setAccentColor(Main.Palette.selection.brighter());
        redFirstButton.setForeground(Main.Palette.foreground);
        redFirstButton.setCurvature(10, 10);
        redFirstButton.setFocusable(false);
        redFirstButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,10));
        redFirstButton.addItemListener(this);

        // Create the second toggle button
        blueFirstButton = new FlatToggleButton("blue", false);
        blueFirstButton.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 14));
        blueFirstButton.setMainColor(Main.Palette.selection.darker());
        blueFirstButton.setAccentColor(Main.Palette.selection.brighter());
        blueFirstButton.setForeground(Main.Palette.foreground);
        blueFirstButton.setCurvature(10, 10);
        blueFirstButton.setFocusable(false);
        blueFirstButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,10));
        blueFirstButton.addItemListener(this);

        // Set constraints and add the first button to the panel
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        panelGbc.weightx = 0.5;
        panelGbc.fill = GridBagConstraints.HORIZONTAL;
        toggleButtonPanel.add(redFirstButton, panelGbc);

        // Set constraints and add the second button to the panel
        panelGbc.gridx = 1;
        toggleButtonPanel.add(blueFirstButton, panelGbc);

        // Set constraints for the main layout and add the panel to the main container
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.ipady = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,10,10,10);
        selectionMenu.add(toggleButtonPanel, gbc);


        leftButtonPlayer1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gameConfig.redDifficulty = gameConfig.redDifficulty.previous();
                player1Label.setText(gameConfig.redDifficulty.toString());
            }
            
        });

        rightButtonPlayer1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gameConfig.redDifficulty = gameConfig.redDifficulty.next();
                player1Label.setText(gameConfig.redDifficulty.toString());
            }
            
        });

        leftButtonPlayer2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gameConfig.blueDifficulty = gameConfig.blueDifficulty.previous();
                player2Label.setText(gameConfig.blueDifficulty.toString());
            }
            
        });

        rightButtonPlayer2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gameConfig.blueDifficulty = gameConfig.blueDifficulty.next();
                player2Label.setText(gameConfig.blueDifficulty.toString());
            }
            
        });
        
        addComponent(selectionMenu);
        ColorArea shadow = new ColorArea(new Color(0,0,0,15), menuArea, menuOffset.add(new Vector2D(8,8)));
        shadow.setCurvature(30, 30);
        addComponent(shadow);
    }

    private void createStartButton() {
        FlatButton startButton = new FlatButton("Start Game");
        startButton.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 22));
        startButton.setForeground(Main.Palette.foreground);
        startButton.setMainColor(Main.Palette.selection);
        startButton.setAccentColor(Main.Palette.selection.brighter());
        startButton.setCurvature(15, 15);
        startButton.setBorder(BorderFactory.createEmptyBorder());
        startButton.setFocusable(false);
        startButton.setBorderWidth(6);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Main.gameScene = new GameScene(gameConfig);              
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

        buttonFrame.setMainColor(new Color(0,0,0,0));
        buttonFrame.setAccentColor(new Color(0,0,0,0));
        
        buttonFrame.setLayout(new GridLayout());

        buttonFrame.add(startButton);

        addComponent(buttonFrame);
    }

    private void createMainMenuButton() {
        FlatButton mainMenuButton = new FlatButton("Main Menu");
        mainMenuButton.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 14));
        mainMenuButton.setForeground(Main.Palette.foreground);
        mainMenuButton.setMainColor(Main.Palette.selection.darker());
        mainMenuButton.setAccentColor(Main.Palette.selection);
        mainMenuButton.setCurvature(15, 15);
        mainMenuButton.setBorder(BorderFactory.createEmptyBorder());
        mainMenuButton.setFocusable(false);

        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.engine.setCurrentScene(new MainMenuScene());
            }
            
        });

        Dimension buttonArea = new Dimension(
            (int)(Main.engine.getResolution().width /8),
            (int)(Main.engine.getResolution().height/10)
        );

        Vector2D buttonOffset = new Vector2D(
            (int)((1*Main.engine.getResolution().width/6) - (buttonArea.width/2)),
            (int)((Main.engine.getResolution().height/2) - (buttonArea.height/2))
        );

        MenuFrame buttonFrame = new MenuFrame(buttonArea, buttonOffset);

        buttonFrame.setMainColor(new Color(0,0,0,0));
        buttonFrame.setAccentColor(new Color(0,0,0,0));
        
        buttonFrame.setLayout(new GridLayout());

        buttonFrame.add(mainMenuButton);

        addComponent(buttonFrame);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItem() == redFirstButton) {
            blueFirstButton.setSelected(false);
            blueFirstButton.setMainColor(Main.Palette.selection.darker());
            redFirstButton.setMainColor(Main.Palette.selection);
            gameConfig.firstPlayer = Constants.RED_PLAYER;
        } else {
            redFirstButton.setSelected(false);
            blueFirstButton.setMainColor(Main.Palette.selection);
            redFirstButton.setMainColor(Main.Palette.selection.darker());
            gameConfig.firstPlayer = Constants.BLUE_PLAYER;
        }
    }
}
