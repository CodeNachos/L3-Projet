package Onitama.src.Scenes.MainMenuScene;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.ColorArea;
import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;
import Onitama.src.Main;
import Onitama.src.Scenes.NewGameMenu.NewGameMenuScene;

public class MainMenuScene extends Scene {
    
    MenuFrame buttonsFrame;
    MenuFrame titleFrame;

    public MainMenuScene() {
        createTitle();
        createTitleShadow();
        createButtons();

        // Add foreground
        ColorArea foreground = new ColorArea(
            Main.Palette.background, 
            new Dimension(
                (int)(Main.engine.getResolution().width * 0.85), 
                (int)(Main.engine.getResolution().height * 0.85)
            ),
            new Vector2D(
                Main.engine.getResolution().width * 0.075,
                Main.engine.getResolution().height * 0.075
            )
        );
        foreground.setCurvature(120, 120);
        addComponent(foreground);

        // Add background
        ColorArea background = new ColorArea(Main.Palette.background.brighter(), new Dimension(Main.engine.getResolution().width, Main.engine.getResolution().height));
        addComponent(background);
    }

    private void createTitle() {
        titleFrame = new MenuFrame(
            new Dimension(
                (int)(Main.engine.getResolution().width * 0.8),
                (int)(Main.engine.getResolution().height * 0.4)
            ),
            new Vector2D(
                Main.engine.getResolution().width * 0.1,
                Main.engine.getResolution().height * 0.05
            )
        );

        titleFrame.setMainColor(new Color(0,0,0,0));
        titleFrame.setAccentColor(new Color(0,0,0,0));

        titleFrame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("O N I T A M A", SwingConstants.CENTER);
        titleLabel.setFont(Main.FontManager.getDefaultCustomFont(Font.ITALIC, 86));
        titleLabel.setForeground(Main.Palette.foreground);

        titleFrame.add(titleLabel, BorderLayout.CENTER);

        addComponent(titleFrame);
    }

    private void createTitleShadow() {
        MenuFrame titleFrameShadow = new MenuFrame(
            new Dimension(
                (int)(Main.engine.getResolution().width * 0.8),
                (int)(Main.engine.getResolution().height * 0.4)
            ),
            new Vector2D(
                Main.engine.getResolution().width * 0.1 + 5,
                Main.engine.getResolution().height * 0.05 + 5
            )
        );

        titleFrameShadow.setMainColor(new Color(0,0,0,0));
        titleFrameShadow.setAccentColor(new Color(0,0,0,0));

        titleFrameShadow.setLayout(new BorderLayout());

        JLabel titleShadow = new JLabel("O N I T A M A", SwingConstants.CENTER);
        titleShadow.setFont(Main.FontManager.getDefaultCustomFont(Font.ITALIC, 86));
        titleShadow.setForeground(Main.Palette.selection);

        titleFrameShadow.add(titleShadow, BorderLayout.CENTER);

        addComponent(titleFrameShadow);
    }

    private void createButtons() {
        buttonsFrame = new MenuFrame(
            new Dimension(
                (int)(Main.engine.getResolution().width/2),
                (int)(Main.engine.getResolution().height/2)
            ),
            new Vector2D(
                (int)((Main.engine.getResolution().width/2) - Main.engine.getResolution().width/4),
                (int)((Main.engine.getResolution().height/2))
            )
        );

        buttonsFrame.setMainColor(new Color(0,0,0,0));
        buttonsFrame.setAccentColor(new Color(0,0,0,0));

        buttonsFrame.setLayout(new BoxLayout(buttonsFrame, BoxLayout.Y_AXIS));


        FlatButton newGameButton = createBaseButton("New Game");
        
        FlatButton quitButton = createBaseButton("Quit");

        // Calculate the maximum width needed
        int maxWidth = Math.max(newGameButton.getPreferredSize().width, quitButton.getPreferredSize().width);

        // Set the maximum width for all buttons
        Dimension buttonSize = new Dimension(maxWidth, newGameButton.getPreferredSize().height);
        newGameButton.setPreferredSize(buttonSize);
        newGameButton.setMaximumSize(buttonSize);
        newGameButton.setMinimumSize(buttonSize);

        quitButton.setPreferredSize(buttonSize);
        quitButton.setMaximumSize(buttonSize);
        quitButton.setMinimumSize(buttonSize);


        Box buttonBox1 = Box.createHorizontalBox();
        buttonBox1.add(Box.createHorizontalGlue());
        buttonBox1.add(newGameButton);
        buttonBox1.add(Box.createHorizontalGlue());

        Box buttonBox2 = Box.createHorizontalBox();
        buttonBox2.add(Box.createHorizontalGlue());
        buttonBox2.add(quitButton);
        buttonBox2.add(Box.createHorizontalGlue());

        buttonsFrame.add(buttonBox1);
        buttonsFrame.add(Box.createVerticalStrut(6));
        buttonsFrame.add(buttonBox2);

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
        button.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 26));
        button.setForeground(Main.Palette.foreground);
        button.setMainColor(Main.Palette.selection);
        button.setAccentColor(new Color(255,255,255,25));
        button.setCurvature(20, 20);
        button.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        button.setSize(20, 20);
        button.setFocusable(false);
        return button;
    }
}
