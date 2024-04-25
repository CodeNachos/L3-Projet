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

/**
 * The Main class initializes and starts the WaffleGame application.
 */
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
    // History
    public static History actionHistory;
    // Sprites
    public static Image waffleSprite = Util.getImage("waffleTile.png"); // Load waffle tile sprite
    public static Image poisonSprite = Util.getImage("poison_waffleTile.png"); // Load poison waffle tile sprite

    /**
     * The main method of the WaffleGame application.
     * Initializes the engine and starts the main game scene.
     * @param args The command line arguments.
     */
    public static void main(String args[]) {
        engine = new GameEngine();
        engine.setResolution(Settings.resolution);

        // start scene
        mainScene = startGameScene();
        
        // start engine
        engine.setCurrentScene(mainScene);
        engine.start(); 
    }

    /**
     * Initializes and configures the main game scene.
     * @return The configured main game scene.
     */
    public static Scene startGameScene() {
        Scene gameScene = new Scene(engine.getResolution());

        // game manager
        game = new WaffleGame();

        // tile map settings
        Vector2D tileMapOffset = new Vector2D(16,16);
        Dimension tileMapArea = new Dimension(
            engine.getResolution().width - 2*(int)tileMapOffset.x,
            engine.getResolution().height - 2*(int)tileMapOffset.y - (int)(engine.getResolution().height * 0.1)
        );

        map = new WaffleTileMap(5, 5, tileMapArea, tileMapOffset);
        map.populateWaffle();

        // game background
        background = new ColorArea(new Color(108, 39, 8, 255), engine.getResolution());

        actionHistory = new History();        
        
        // add components in reverse rendering order
        gameScene.addComponent(game);
        gameScene.addComponent(actionHistory);
        gameScene.addComponent(createStatsMenu());
        gameScene.addComponent(map);
        gameScene.addComponent(background);

        return gameScene;
    }

    /**
     * Creates and configures the stats menu.
     * @return The configured stats menu.
     */
    public static MenuFrame createStatsMenu() {
        int height = (int)(engine.getResolution().height * 0.1);
        MenuFrame statsMenu = new MenuFrame(new Dimension(engine.getResolution().width, height));
        statsMenu.setPos(new Vector2D(0, engine.getResolution().height - height));
        
        Color menuColor = new Color(206,129,71,255);
        statsMenu.setMainColor(menuColor);
        statsMenu.setAccentColor(menuColor);

        // Use GridBagLayout
        statsMenu.setLayout(new GridBagLayout());
        
        // Set alignment to center
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

    /**
     * Creates and configures the game over menu.
     * @return The configured game over menu.
     */
    public static MenuFrame createGameOverMenu() {
        MenuFrame gameOverMenu = new MenuFrame(new Dimension(300,200));

        gameOverMenu.setLayout(new BoxLayout(gameOverMenu, BoxLayout.Y_AXIS));

        gameOverMenu.setPos(new Vector2D(((engine.getResolution().width/2) - 150), ((engine.getResolution().height/2) - 150)));

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
                mainScene = startGameScene();
                engine.setCurrentScene(mainScene);
                mainScene.remove(gameOverMenu);
                actionHistory.clearRecords();
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
                engine.stop();
            }
        });
        
        gameOverMenu.add(quitButton);


        return gameOverMenu;
    }
}
