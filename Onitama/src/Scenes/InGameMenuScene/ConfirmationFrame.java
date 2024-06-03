package Onitama.src.Scenes.InGameMenuScene;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;
import Onitama.src.Main;
import Onitama.src.Scenes.InGameMenuScene.InGameMenuScene.MenuActions;
import Onitama.src.Scenes.MainMenuScene.MainMenuScene;
import Onitama.src.Scenes.NewGameMenu.NewGameMenuScene;

public class ConfirmationFrame extends MenuFrame {

    private boolean firstProcess = true;

    private MenuFrame caller;
    private MenuActions action;

    public ConfirmationFrame(MenuFrame caller, MenuActions action, Dimension area, Vector2D offset) {
        super(area, offset);

        this.caller = caller;
        this.action = action;

        setMainColor(Main.Palette.selection);
        setAccentColor(Main.Palette.selection.brighter());
        setBorderWidth(6);
        setCurvature(20, 20);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel confirmText = new JLabel(getConfirmationText(action), SwingConstants.CENTER);
        confirmText.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 22));
        confirmText.setForeground(Main.Palette.foreground);
        confirmText.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
        confirmText.setAlignmentX(CENTER_ALIGNMENT);

        FlatButton yesButton = new FlatButton("continue");
        yesButton.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 16));
        yesButton.setMainColor(Main.Palette.selection);
        yesButton.setAccentColor(Main.Palette.selection.brighter());
        yesButton.setForeground(Main.Palette.foreground);
        yesButton.setCurvature(10, 10);
        yesButton.setFocusable(false);
        yesButton.setBorder(BorderFactory.createEmptyBorder(12, 15, 12,15));
        

        FlatButton noButton = new FlatButton(" cancel ");
        noButton.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 16));
        noButton.setMainColor(Main.Palette.selection);
        noButton.setAccentColor(Main.Palette.selection.brighter());
        noButton.setForeground(Main.Palette.foreground);
        noButton.setCurvature(10, 10);
        noButton.setFocusable(false);
        noButton.setBorder(BorderFactory.createEmptyBorder(12, 15, 12,15));
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setBackground(new Color(0,0,0,0));
        buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);

        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(yesButton);
        buttonsPanel.add(Box.createHorizontalStrut(30));
        buttonsPanel.add(noButton);
        buttonsPanel.add(Box.createHorizontalGlue());

        add(Box.createVerticalGlue());
        add(confirmText);
        add(buttonsPanel);
        add(Box.createVerticalGlue());

        yesButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                switch (action) {
                    case RESTART:
                        Main.gameScene.history.resetGame();
                        Main.engine.setCurrentScene(Main.gameScene);
                        break;
                    
                    case NEW_GAME:
                        Main.engine.setCurrentScene(new NewGameMenuScene());
                        break;
                    
                    case MAIN_MENU:
                        Main.engine.setCurrentScene(new MainMenuScene());
                        break;
                
                    default:
                        break;
                }
            }
            
        });

        noButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ((Scene)getParent()).addComponent(caller);
                removeMenu();
            }
            
        });
    }

    private void removeMenu() {
        ((Scene)getParent()).removeComponent(this);
        firstProcess = true;
    }

    private String getConfirmationText(MenuActions action) {
        String confText = "";

        switch (action) {
            case RESTART:
                confText = "Restart current game?";
                break;
        
            case NEW_GAME:
                confText = "Create new game?";
                break;
            
            case MAIN_MENU:
                confText = "Quit current game?";
                break;

            default:
                break;
        }

        return confText;
    }

    @Override
    public void process(double delta) {
        if (firstProcess) {
            getParent().setComponentZOrder(this, 0);
            firstProcess = false;
        }
    }

    @Override
    public void input(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED ) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                ((Scene)getParent()).addComponent(caller);
                removeMenu();
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                switch (action) {
                    case RESTART:
                        Main.gameScene.history.resetGame();
                        Main.engine.setCurrentScene(Main.gameScene);
                        break;
                    
                    case NEW_GAME:
                        Main.engine.setCurrentScene(new NewGameMenuScene());
                        break;
                    
                    case MAIN_MENU:
                        Main.engine.setCurrentScene(new MainMenuScene());
                        break;
                
                    default:
                        break;
                }
            }
        }
    }
    
}
