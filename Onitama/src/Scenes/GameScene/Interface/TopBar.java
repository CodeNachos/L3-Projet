package Onitama.src.Scenes.GameScene.Interface;

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
import Onitama.src.Main;
import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Scripts.GameConfiguration;

public class TopBar extends MenuFrame {
    FlatButton validateButton;
    
    FlatButton undoButton;
    FlatButton redoButton;

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
        
        createTimerLabel();

        add(Box.createHorizontalGlue()); // Add glue to right-align components

        createUndoButton();
        
        add(Box.createHorizontalStrut(10));

        createRedoButton();
    }

    
    private FlatButton createBaseButton(String content) {
        FlatButton button = new FlatButton(content);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Main.Palette.background);
        button.setMainColor(Main.Palette.orange);
        button.setAccentColor(Main.Palette.orange);
        button.setCurvature(20, 20);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setSize(20, 20);
        return button;
    }

    private void createUndoButton() {

        undoButton = createBaseButton("Undo");

        undoButton.setEnabled(GameScene.canUndo());
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameScene.undo();
            }
        });

        add(undoButton);
    }

    private void createRedoButton() {
        
        redoButton = createBaseButton("Redo");

        redoButton.setEnabled(GameScene.canRedo());
        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameScene.redo();
            }
        });

        add(redoButton);
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

        redoButton.setEnabled(GameScene.canRedo());
        undoButton.setEnabled(GameScene.canUndo());
    }

    
}
