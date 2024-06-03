package Onitama.src.Scenes.GameScene.Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Engine.Entities.UI.BlurredArea;
import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;
import Onitama.src.Main;
import Onitama.src.Scenes.NewGameMenu.NewGameMenuScene;
import Onitama.src.Scenes.GameScene.Constants;
import Onitama.src.Scenes.GameScene.Constants.PlayerType;
import Onitama.src.Scenes.MainMenuScene.MainMenuScene;

public class GameOverMenu extends MenuFrame {
    
    BlurredArea blurredArea;

    JLabel winnerLabel;
    FlatButton returnButton;
    FlatButton newGameButton;
    FlatButton mainMenuButton;

    GridBagConstraints gbc;

    public GameOverMenu(Dimension area, Vector2D offset) {
        super(area, offset);

        setMainColor( new Color(68,71,90,150));
        setAccentColor(Main.Palette.selection.brighter());
        setCurvature(20, 20);
        setBorderWidth(6);

        blurredArea = new BlurredArea(Main.engine.getResolution(), BlurredArea.captureBackground(Main.gameScene));
    
        Main.gameScene.addComponent(blurredArea);

        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();

        createWinnerLabel();
        createReturnButton();
        createNewGameButton();
        createMainMenuButton();
    }

    private void createWinnerLabel() {
        //String labelText = "<html><center><span style='font-family:" + Main.FontManager.getDefaultCustomFont(Font.ITALIC, 38).getFamily() + ";'>" + "Game<br>Over!</span></center></html>";
        String labelText = getWinnerText();
        winnerLabel = new JLabel(labelText);
        winnerLabel.setHorizontalAlignment(JLabel.CENTER);
        winnerLabel.setFont(Main.FontManager.getDefaultCustomFont(Font.ITALIC, 38));
        winnerLabel.setForeground(Main.Palette.foreground);
    
        gbc.gridx = 0;
        gbc.gridy = 1; // Adjusted to start from the top
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0; // Adjusted to make it span full width
        gbc.weighty = 0.4; // Adjusted to take only 20% of the available vertical space
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 20, 20);
        add(winnerLabel, gbc);
    }
    
    private void createReturnButton() {
        returnButton = createBaseButton("Return");
        returnButton.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 16));
        returnButton.setForeground(Main.Palette.foreground);
        returnButton.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        gbc.gridx = 1;
        gbc.gridy = 0; // Adjusted to place below the winnerLabel
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; // Adjusted to make it span full width
        gbc.weighty = 0.2; // Adjusted to take only 20% of the available vertical space
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 20, 0, 20);
        add(returnButton, gbc);

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeMenu();
            }
            
        });
    }
    
    private void createNewGameButton() {
        newGameButton = createBaseButton("New Game");
        newGameButton.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 16));
        newGameButton.setForeground(Main.Palette.foreground);
    
        gbc.gridx = 1;
        gbc.gridy = 2; // Adjusted to place below the winnerLabel
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 1.0; // Adjusted to make it span full width
        gbc.weighty = 0.2; // Adjusted to take only 20% of the available vertical space
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 10, 5);
        add(newGameButton, gbc);

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeMenu();
                Main.engine.setCurrentScene(new NewGameMenuScene());
            }
            
        });
    }
    
    private void createMainMenuButton() {
        mainMenuButton = createBaseButton("Main Menu");
        mainMenuButton.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 16));
        mainMenuButton.setForeground(Main.Palette.foreground);
    
        gbc.gridx = 2;
        gbc.gridy = 2; // Adjusted to place below the winnerLabel
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; // Adjusted to make it span full width
        gbc.weighty = 0.2; // Adjusted to take only 20% of the available vertical space
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 10, 20);
        add(mainMenuButton, gbc);

        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    Main.engine.setCurrentScene(new MainMenuScene());
                    
            }
            
        });
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
        button.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        button.setSize(20, 20);
        button.setFocusable(false);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        return button;
    }

    @Override
    public void process(double delta) {
        if (getParent().getComponentZOrder(this) > 1) {
            getParent().setComponentZOrder(blurredArea, 1);
            getParent().setComponentZOrder(this, 0);
        }
    }

    private String getWinnerText() {
        String winnerText = "";

        if (
            Main.gameScene.getPlayerType(Constants.RED_PLAYER) != PlayerType.HUMAN && 
            Main.gameScene.getPlayerType(Constants.BLUE_PLAYER) != PlayerType.HUMAN
        ) {
            if (Main.gameScene.getWinner() == Constants.RED_PLAYER) {
                winnerText = "RED AI WON";
            } else {
                winnerText = "BLUE AI WON";
            }
        } else if (
            Main.gameScene.getPlayerType(Constants.RED_PLAYER) == PlayerType.HUMAN &&
            Main.gameScene.getPlayerType(Constants.BLUE_PLAYER) == PlayerType.HUMAN
        ) {
            if (Main.gameScene.getWinner() == Constants.RED_PLAYER) {
                winnerText = "RED PLAYER WON!";
            } else {
                winnerText = "BLUE PLAYER WON!";
            }
        } else if (
            Main.gameScene.getPlayerType(Constants.RED_PLAYER) != PlayerType.HUMAN ||
            Main.gameScene.getPlayerType(Constants.BLUE_PLAYER) != PlayerType.HUMAN
        ) {
            if (
                isWinner(Constants.RED_PLAYER) && Main.gameScene.getPlayerType(Constants.RED_PLAYER) != PlayerType.HUMAN || 
                isWinner(Constants.BLUE_PLAYER) && Main.gameScene.getPlayerType(Constants.BLUE_PLAYER) != PlayerType.HUMAN
            ) {
                winnerText = "YOU LOST!";
            } else { 
                winnerText = "YOU WON!";
            }
        }
        
        return winnerText;
    }

    private boolean isWinner(int player) {
        return (Main.gameScene.getWinner() == player);
    }
}
