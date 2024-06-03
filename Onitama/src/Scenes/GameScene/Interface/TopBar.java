package Onitama.src.Scenes.GameScene.Interface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.MenuFrame;
import Engine.Global.Settings;
import Engine.Global.Util;
import Engine.Structures.Vector2D;
import Onitama.src.Main;
import Onitama.src.Scenes.GameScene.Scripts.AI.SmartAI;
import Onitama.src.Scenes.GameScene.Scripts.States.Action;
import Onitama.src.Scenes.HowToPlayScene.HowToPlayScene;
import Onitama.src.Scenes.InGameMenuScene.InGameMenuScene;


public class TopBar extends MenuFrame {
    FlatButton validateButton;
    
    FlatButton menuButton;
    FlatButton undoButton;
    FlatButton hintButton;
    FlatButton redoButton;
    FlatButton helpButton;

    private Dimension currentResolution;
    
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

        currentResolution = Main.engine.getResolution();
    }

    public void setEnabledUndo(boolean state) {
        undoButton.setEnabled(state);
    }

    public void setEnabledRedo(boolean state) {
        redoButton.setEnabled(state);
    }

    public void setEnabledHint(boolean state) {
        hintButton.setEnabled(state);
    }

    public void setEnabledMenu(boolean state) {
        menuButton.setEnabled(state);
    }

    public void setEnabledHelp(boolean state) {
        helpButton.setEnabled(state);
    }

    public void setEnabledButtons(boolean state) {
        setEnabledUndo(state);
        setEnabledRedo(state);
        setEnabledHint(state);
        setEnabledMenu(state);
        setEnabledHelp(state);
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

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.gameScene.history.undo();
                Main.iaShouldWait = true;
            }
        });

        add(undoButton);
    }

    private void createRedoButton() {
        
        redoButton = createBaseButton("↪");
        redoButton.setToolTipText("Redo");

        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.gameScene.history.redo();
                Main.iaShouldWait = true;
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
                SmartAI hintAI = new SmartAI(2, Main.gameScene.getCurrentPlayer());
                Action hint = hintAI.play();
                Main.gameScene.setAction(hint);
            }
        });

        add(hintButton);
    }

    private void createMenuButton() {

        menuButton = createBaseButton("☰");
        menuButton.setToolTipText("In Game Menu");
        menuButton.setFont(Main.FontManager.getUnicodeCustomFont(Font.BOLD, 26));
        menuButton.setBorder(BorderFactory.createEmptyBorder(7, 10, 3, 10));
        
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.engine.setCurrentScene(new InGameMenuScene());
            }
        });

        add(menuButton);
    }

    private void createHelpButton() {

        helpButton = createBaseButton("🛈");
        helpButton.setToolTipText("How To Play");
        helpButton.setFont(Main.FontManager.getUnicodeCustomFont(Font.BOLD, 18));
        helpButton.setBorder(BorderFactory.createEmptyBorder(9, 10, 9, 10));
        
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.engine.setCurrentScene(new HowToPlayScene(Main.engine.getCurrentScene()));
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

    @Override
    public void input(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                Main.engine.setCurrentScene(new InGameMenuScene());
            } else if (e.getKeyCode() == Settings.undo_key)  {
                Main.gameScene.history.undo();
                Main.iaShouldWait = true;
            } else if (e.getKeyCode() == Settings.redo_key)  {
                Main.gameScene.history.redo();
                Main.iaShouldWait = true;
            }
        }
    }

    @Override
    public void process(double delta) {
        if (!currentResolution.equals(Main.initialResolution)) {
            Vector2D resizeRatio = new Vector2D(
                (double) Main.engine.getResolution().width / (double) Main.initialResolution.width,
                (double) Main.engine.getResolution().height / (double) Main.initialResolution.height
            );
            menuButton.setFont(Main.FontManager.getUnicodeCustomFont(Font.BOLD, (int)(26 * resizeRatio.y)));
            undoButton.setFont(Main.FontManager.getUnicodeCustomFont(Font.BOLD, (int)(16 * resizeRatio.y)));
            redoButton.setFont(Main.FontManager.getUnicodeCustomFont(Font.BOLD, (int)(16 * resizeRatio.y)));
            hintButton.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, (int)(16 * resizeRatio.y)));
            helpButton.setFont(Main.FontManager.getUnicodeCustomFont(Font.BOLD, (int)(18 * resizeRatio.y)));
        }
        
        currentResolution = Main.engine.getResolution();

    }


}
