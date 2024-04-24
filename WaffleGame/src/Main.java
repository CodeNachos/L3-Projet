package WaffleGame.src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Engine.Core.Engines.GameEngine;
import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.*;
import Engine.Global.Settings;
import Engine.Global.Util;
import Engine.Structures.Vector2D;

public class Main {
    // Engine
    public static GameEngine engine;
    

    // Scenes

    // Main Scene
    public static Scene mainScene;
    // Main Scene Components
    public static WaffleGame game;
    public static WaffleTileMap map;
    public static ColorArea background;
    // Stats menu components
    public static JLabel playerLabel;
    

    public static void main(String args[]) {
        engine = new GameEngine();

        // start scene
        mainScene = startGameScene();
        
        // start engine
        engine.setCurrentScene(mainScene);
        engine.start(); 
    }

    public static Scene startGameScene() {
        Scene gameScene = new Scene();

        // game manager
        game = new WaffleGame();

        // tile map settings
        Vector2D tileMapOffset = new Vector2D(16,16);
        Dimension tileMapArea = new Dimension(
            Settings.resolution.width - 2*(int)tileMapOffset.x,
            Settings.resolution.height - 2*(int)tileMapOffset.y - (int)(Settings.resolution.height * 0.1)
        );

        map = new WaffleTileMap(5, 5, tileMapArea, tileMapOffset);
        map.populateWaffle();

        // game background
        background = new ColorArea(new Color(108, 39, 8, 255), Settings.resolution);

        
        gameScene.addComponent(createStatsMenu());

        // add components in reverse rendering order
        gameScene.addComponent(game);
        gameScene.addComponent(map);
        gameScene.addComponent(background);

        return gameScene;
    }

    public static MenuFrame createStatsMenu() {
        int height = (int)(Settings.resolution.height * 0.1);
        MenuFrame statsMenu = new MenuFrame(new Dimension(Settings.resolution.width, height));
        statsMenu.setPos(new Vector2D(0, Settings.resolution.height - height));
        
        Color menuColor = new Color(206,129,71,255);
        statsMenu.setMainColor(menuColor);
        statsMenu.setAccentColor(menuColor);

        // Use GridBagLayout
        statsMenu.setLayout(new GridBagLayout());
        
        // Set alignement to center
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        // Create and configure label
        playerLabel = new JLabel("PLAYER 1 TURN");
        playerLabel.setForeground(new Color(108,39,8,255));
        playerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Add label
        statsMenu.add(playerLabel, gbc);

        return statsMenu;
    }

    public static MenuFrame createGameOverMenu() {
        MenuFrame gameOverMenu = new MenuFrame(new Dimension(300,200));

        gameOverMenu.setLayout(new BoxLayout(gameOverMenu, BoxLayout.Y_AXIS));

        gameOverMenu.setPos(new Vector2D(((Settings.resolution.width/2) - 150), ((Settings.resolution.height/2) - 150)));

        gameOverMenu.setMainColor(new Color(108,39,8,255));
        gameOverMenu.setAccentColor(new Color(255,255,255,100));
        gameOverMenu.setCurvature(30, 30);

        JLabel gameOverLabel = new JLabel("GAME OVER");
        gameOverLabel.setForeground(new Color(255,255, 255,200));
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 32));
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameOverLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        gameOverMenu.add(gameOverLabel);

        FlatButton restartButton = new FlatButton("Play Again");
        restartButton.setMainColor(new Color(255,255,255,100));
        restartButton.setAccentColor(new Color(255,255,255,100));
        restartButton.setCurvature(10, 10);
        restartButton.setForeground(new Color(255,255,255,200));
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call your function here
                mainScene = startGameScene();
                engine.setCurrentScene(mainScene);
                mainScene.remove(gameOverMenu);
            }
        });
        
        gameOverMenu.add(restartButton);
        gameOverMenu.add(Box.createRigidArea(new Dimension(0, 10)));

        FlatButton quitButton = new FlatButton("Quit Game");
        quitButton.setMainColor(new Color(255,255,255,100));
        quitButton.setAccentColor(new Color(255,255,255,100));
        quitButton.setCurvature(10, 10);
        quitButton.setForeground(new Color(255,255,255,200));
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call your function here
                engine.stop();
            }
        });
        
        gameOverMenu.add(quitButton);


        return gameOverMenu;
    }
}
