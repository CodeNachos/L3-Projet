package Old.src.Scenes.GameScene.Interface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;
import Old.src.Main;
import Old.src.Scenes.GameScene.GameScene;
import Old.src.Scenes.GameScene.Scripts.GameConfiguration;

public class TopBar extends MenuFrame {
    FlatButton validateButton;
    JLabel timerLabel;
    JLabel playerLabel;
    
    public TopBar(Dimension area, Vector2D offset) {
        super(area);
        setPos(offset);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        setMainColor(Main.Palette.selection.darker());
        setAccentColor(Main.Palette.selection.darker());
        setCurvature(5, 5);

        add(Box.createHorizontalGlue()); // Add glue to left-align components

        createPlayerLabel();

        add(Box.createHorizontalGlue());

        createValidateButton();

        add(Box.createHorizontalGlue());
        
        createTimerLabel();

        add(Box.createHorizontalGlue()); // Add glue to right-align components
    }

    private void createValidateButton() {
        validateButton = new FlatButton("End Turn");
        validateButton.setFont(new Font("Arial", Font.BOLD, 16));
        validateButton.setForeground(Main.Palette.background);
        validateButton.setMainColor(Main.Palette.orange);
        validateButton.setAccentColor(Main.Palette.orange);
        validateButton.setCurvature(20, 20);
        validateButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameScene.updateMatch();
            }
        });

        add(validateButton);
    }

    private void createTimerLabel() {
        timerLabel = new JLabel("0:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setForeground(Main.Palette.foreground);

        add(timerLabel);
    }

    private void createPlayerLabel() {
        playerLabel = new JLabel(getPlayerName());
        playerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        playerLabel.setForeground(Main.Palette.foreground);

        add(playerLabel);
    }

    private String getPlayerName() {
        if (GameScene.game.getCurrentPlayer() == GameConfiguration.PLAYER1) {
            return "RED";
        } else {
            return "BLUE";
        }
    }

    @Override
    public void process(double delta) {
        playerLabel.setText(getPlayerName());
        if (GameScene.game.getCurrentPlayer() == GameConfiguration.PLAYER1) {
            playerLabel.setForeground(Main.Palette.red);
        } else {
            playerLabel.setForeground(Main.Palette.highlight);
        }

        if (isValidTurn()) {
            validateButton.setMainColor(Main.Palette.orange);
            validateButton.setAccentColor(Main.Palette.orange);
            validateButton.setEnabled(true);
        } else {
            validateButton.setMainColor(Main.Palette.selection);
            validateButton.setAccentColor(Main.Palette.selection.brighter());
            validateButton.setEnabled(false);
        }


    }

    private boolean isValidTurn() {
        return (GameScene.game.isCardSelected() && GameScene.game.isPieceSelected() && GameScene.game.isActionSelected());
    }
    
}
