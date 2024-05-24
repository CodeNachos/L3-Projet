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
import Engine.Global.Util;
import Engine.Structures.Vector2D;
import Onitama.src.Main;
import Onitama.src.Scenes.GameScene.GameScene;


public class TopBar extends MenuFrame {
    FlatButton validateButton;
    
    FlatButton menuButton;
    FlatButton undoButton;
    FlatButton hintButton;
    FlatButton redoButton;
    FlatButton helpButton;
    
    public TopBar(Dimension area, Vector2D offset) {
        super(area, offset);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        setMainColor(Main.Palette.selection.darker());
        setAccentColor(Main.Palette.selection);
        setCurvature(10, 10);

        add(Box.createHorizontalGlue()); // Add glue to right-align components

        createMenuButton();

        add(Box.createHorizontalGlue()); 

        createSepLabel();

        add(Box.createHorizontalGlue()); 
        
        createUndoButton();

        add(Box.createHorizontalStrut(6));
        
        createRedoButton();

        add(Box.createHorizontalGlue());
        
        createSepLabel();

        add(Box.createHorizontalGlue()); 

        createHintButton();
        
        add(Box.createHorizontalStrut(6));

        createHelpButton();

        add(Box.createHorizontalGlue());
    }

    
    private FlatButton createBaseButton(String content) {
        FlatButton button = new FlatButton(content);
        button.setFont(Main.FontManager.getUnicodeCustomFont(Font.BOLD, 16));
        button.setForeground(Main.Palette.foreground);
        button.setMainColor(Main.Palette.selection.brighter());
        button.setAccentColor(new Color(255,255,255,25));
        button.setCurvature(20, 20);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setSize(20, 20);
        button.setFocusable(false);
        return button;
    }

    private void createUndoButton() {

        undoButton = createBaseButton("↩");
        undoButton.setToolTipText("Undo");

        //undoButton.setEnabled(GameScene.canUndo());
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameScene.history.undo();
            }
        });

        add(undoButton);
    }

    private void createRedoButton() {
        
        redoButton = createBaseButton("↪");
        redoButton.setToolTipText("Redo");

        //redoButton.setEnabled(GameScene.canRedo());
        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameScene.history.redo();
            }
        });

        add(redoButton);
    }

    private void createHintButton() {

        hintButton = createBaseButton(" ? ");
        hintButton.setToolTipText("Need a hint?");
        hintButton.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 16));
        hintButton.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        //undoButton.setEnabled(GameScene.canUndo());
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Util.printWarning("Not implemented");
            }
        });

        add(hintButton);
    }

    private void createMenuButton() {

        menuButton = createBaseButton("☰");
        menuButton.setToolTipText("In Game Menu");
        menuButton.setFont(Main.FontManager.getUnicodeCustomFont(Font.BOLD, 26));
        menuButton.setBorder(BorderFactory.createEmptyBorder(6, 10, 4, 10));

        //undoButton.setEnabled(GameScene.canUndo());s
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //GameScene.undo();
            }
        });

        add(menuButton);
    }

    private void createHelpButton() {

        helpButton = createBaseButton("🛈");
        helpButton.setToolTipText("How To Play");
        helpButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //undoButton.setEnabled(GameScene.canUndo());
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //GameScene.undo();
            }
        });

        add(helpButton);
    }

    private void createSepLabel() {
        JLabel sepLabel = new JLabel("|");
        sepLabel.setFont(Main.FontManager.getUnicodeCustomFont(Font.BOLD, 24));
        sepLabel.setForeground(Main.Palette.selection);

        add(sepLabel);
    }


}
