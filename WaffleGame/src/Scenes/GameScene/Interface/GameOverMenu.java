package WaffleGame.src.Scenes.GameScene.Interface;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;
import WaffleGame.src.Main;
import WaffleGame.src.Scenes.GameScene.GameScene;
import WaffleGame.src.Scenes.MainMenu.MainMenuScene;

public class GameOverMenu extends MenuFrame {
    public GameOverMenu() {
        super(new Dimension(300,200));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setPos(new Vector2D(((Main.engine.getResolution().width/2) - 150), ((Main.engine.getResolution().height/2) - 150)));

        setMainColor(Main.shadowColor);
        setAccentColor(Main.secondaryColor);
        setCurvature(30, 30);

        JLabel gameOverLabel = new JLabel("GAME OVER");
        gameOverLabel.setForeground(Main.whiteColor);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 32));
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameOverLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        add(gameOverLabel);

        FlatButton restartButton = new FlatButton("Play Again");
        restartButton.setMainColor(Main.secondaryColor);
        restartButton.setAccentColor(Main.primaryColor);
        restartButton.setCurvature(10, 10);
        restartButton.setForeground(Main.whiteColor);
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        MenuFrame toRemove = this;
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.gameScene = new GameScene();
                Main.engine.setCurrentScene(Main.gameScene);
                Main.gameScene.remove(toRemove);
                GameScene.actionHistory.clearRecords();
            }
        });
        
        add(restartButton);
        add(Box.createRigidArea(new Dimension(0, 10)));

        FlatButton mainMenuButton = new FlatButton("Main Menu");
        mainMenuButton.setMainColor(Main.secondaryColor);
        mainMenuButton.setAccentColor(Main.primaryColor);
        mainMenuButton.setCurvature(10, 10);
        mainMenuButton.setForeground(Main.whiteColor);
        mainMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuButton.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.mainScene = new MainMenuScene();
                Main.engine.setCurrentScene(Main.mainScene);
            }
        });
        
        add(mainMenuButton);
    }
}