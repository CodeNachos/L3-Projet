package Onitama.src.Scenes.MainMenuScene;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.ColorArea;
import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;
import Onitama.src.Main;
import Onitama.src.Scenes.NewGameMenu.NewGameMenuScene;

public class MainMenuScene extends Scene {
    
    MenuFrame buttonsFrame;

    public MainMenuScene() {

        createButtons();

        // Add background
        ColorArea background = new ColorArea(Main.Palette.background, new Dimension(Main.engine.getResolution().width, Main.engine.getResolution().height));
        addComponent(background);
    }

    private void createButtons() {
        buttonsFrame = new MenuFrame(
            Main.engine.getResolution(),
            new Vector2D(

            )
        );

        buttonsFrame.setMainColor(new Color(0,0,0,0));
        buttonsFrame.setAccentColor(new Color(0,0,0,0));

        buttonsFrame.setLayout(new BoxLayout(buttonsFrame, BoxLayout.Y_AXIS));


        FlatButton newGameButton = createBaseButton("New Game");
        
        FlatButton quitButton = createBaseButton("Quit");

        buttonsFrame.add(Box.createVerticalGlue());
        buttonsFrame.add(newGameButton);
        buttonsFrame.add(Box.createVerticalStrut(6));
        buttonsFrame.add(quitButton);
        buttonsFrame.add(Box.createVerticalGlue());
        
        addComponent(buttonsFrame);

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.engine.setCurrentScene(new NewGameMenuScene());
            }
            
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.engine.stop();
            }
            
        });
    }

    private FlatButton createBaseButton(String content) {
        FlatButton button = new FlatButton(content);
        button.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 16));
        button.setForeground(Main.Palette.foreground);
        button.setMainColor(Main.Palette.selection.brighter());
        button.setAccentColor(new Color(255,255,255,25));
        button.setCurvature(20, 20);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setSize(20, 20);
        button.setFocusable(false);
        return button;
    }
}
