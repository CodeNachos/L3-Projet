package Onitama.src.Scenes.HowToPlayScene;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.ColorArea;
import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.FlatTextField;
import Engine.Entities.UI.FlatToggleButton;
import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;
import Onitama.src.Main;
import Onitama.src.Scenes.MainMenuScene.MainMenuScene;

public class HowToPlayScene extends Scene implements ItemListener {

    Scene returnScene;

    FlatToggleButton gameSetupButton;
    FlatToggleButton gameStepsButton;
    FlatToggleButton howToWinButton;

    public HowToPlayScene(Scene returnScene) {
        this.returnScene = returnScene;

        createReturnButton();

        createTabs();

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

    private void createTabs() {
        MenuFrame tabsFrame = new MenuFrame(
            new Dimension(
                (int)(Main.engine.getResolution().width * 0.5),
                (int)(Main.engine.getResolution().height * 0.10)
            ),
            new Vector2D(
                Main.engine.getResolution().width /2 - Main.engine.getResolution().width * 0.5 / 2,
                Main.engine.getResolution().height * 0.075
            )
        );

        tabsFrame.setMainColor(new Color(0,0,0,25));
        tabsFrame.setAccentColor(new Color(0,0,0,0));
        tabsFrame.setCurvature(30, 30);

        tabsFrame.setLayout(new BoxLayout(tabsFrame, BoxLayout.X_AXIS));

        gameSetupButton = createToggleButton("Game Setup");
        gameSetupButton.addItemListener(this);
        gameStepsButton = createToggleButton("Game Steps");
        gameStepsButton.setMainColor(Main.Palette.selection.darker());
        gameStepsButton.addItemListener(this);
        howToWinButton = createToggleButton("How to Win");
        howToWinButton.setMainColor(Main.Palette.selection.darker());
        howToWinButton.addItemListener(this);

        tabsFrame.add(Box.createHorizontalGlue());
        tabsFrame.add(gameSetupButton);
        tabsFrame.add(gameStepsButton);
        tabsFrame.add(howToWinButton);
        tabsFrame.add(Box.createHorizontalGlue());

        showSetupContent();

        addComponent(tabsFrame);
    }

    private void createReturnButton() {
        MenuFrame returnFrame = new MenuFrame(
            new Dimension(
                (int)(Main.engine.getResolution().width * 0.10),
                (int)(Main.engine.getResolution().height * 0.08)
            ),
            new Vector2D(
                Main.engine.getResolution().width * 0.5 - Main.engine.getResolution().width * (0.10/2),
                Main.engine.getResolution().height * 0.82
            )
        );
    
        returnFrame.setLayout(new BoxLayout(returnFrame, BoxLayout.Y_AXIS)); // Set BorderLayout to the returnFrame
    
        returnFrame.setMainColor(new Color(0,0,0,0));
        returnFrame.setAccentColor(new Color(0,0,0,0));
    
        returnFrame.setLayout(new BoxLayout(returnFrame, BoxLayout.Y_AXIS));

        FlatButton returnButton = createBaseButton("Return");
        returnButton.setAlignmentX(CENTER_ALIGNMENT);
        
        returnFrame.add(Box.createVerticalGlue());
        returnFrame.add(returnButton); // Add the button to the center
        returnFrame.add(Box.createVerticalGlue());

        addComponent(returnFrame);

        returnButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (returnScene == null) {
                    Main.engine.setCurrentScene(new MainMenuScene());
                } else {
                    Main.engine.setCurrentScene(returnScene);
                }
            }
            
        });
    }

    private MenuFrame createContentFrame() {
        MenuFrame contentFrame = new MenuFrame(
            new Dimension(
                (int)(Main.engine.getResolution().width * 0.6),
                (int)(Main.engine.getResolution().height * 0.6)
            ), new Vector2D(
                Main.engine.getResolution().width * 0.5 - Main.engine.getResolution().width * (0.6/2),
                Main.engine.getResolution().height * 0.2
            )
        );
        
        contentFrame.setMainColor(new Color(0,0,0,0));
        contentFrame.setAccentColor(new Color(0,0,0,0));

        contentFrame.setLayout(new BoxLayout(contentFrame, BoxLayout.Y_AXIS));

        return contentFrame; 
    } 

    private void showSetupContent() {
        MenuFrame contentFrame = createContentFrame();

        JLabel title = new JLabel(" GAME SETUP ", SwingConstants.CENTER);
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(Main.FontManager.getDefaultCustomFont(Font.ITALIC, 22));
        title.setForeground(Main.Palette.foreground);

        // Create a JTextArea instead of FlatTextField
        JTextArea textContent = new JTextArea();
        textContent.setAlignmentX(CENTER_ALIGNMENT);
        textContent.setWrapStyleWord(true);
        textContent.setLineWrap(true);
        textContent.setEditable(false);
        textContent.enableInputMethods(false);
        textContent.setOpaque(false);
        textContent.setForeground(Main.Palette.foreground);
        textContent.setFont(Main.FontManager.getDefaultCustomFont(Font.PLAIN, 14));
        textContent.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        textContent.setBackground(new Color(0,0,0,0));

        // Read file content and set it to the text area
        String fileContent = readFile("Onitama/res/Rules/Setup.txt");
        textContent.setText(fileContent);

        // Put the JTextArea inside a JScrollPane
        JScrollPane scrollPane = new JScrollPane(textContent);
        scrollPane.setAlignmentX(CENTER_ALIGNMENT);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(
            (int) (Main.engine.getResolution().width * 0.7),
            (int) (Main.engine.getResolution().height * 0.5)
        ));

        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        contentFrame.add(Box.createVerticalGlue());
        contentFrame.add(title);
        contentFrame.add(scrollPane); // Add the scroll pane instead of the text area
        contentFrame.add(Box.createVerticalGlue());

        addComponent(contentFrame);
    }

    private void showStepsContent() {

    }

    public void showHowToWinContent() {

    }

    private FlatButton createBaseButton(String content) {
        FlatButton button = new FlatButton(content);
        button.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 14));
        button.setForeground(Main.Palette.foreground);
        button.setMainColor(Main.Palette.selection);
        button.setAccentColor(new Color(255,255,255,25));
        button.setCurvature(20, 20);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setSize(20, 20);
        button.setFocusable(false);
        return button;
    }

    private FlatToggleButton createToggleButton(String content) {
        FlatToggleButton button = new FlatToggleButton(content, false);
        button.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 14));
        button.setForeground(Main.Palette.foreground);
        button.setMainColor(Main.Palette.selection);
        button.setAccentColor(new Color(255,255,255,25));
        button.setCurvature(0, 0);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setSize(20, 20);
        button.setFocusable(false);
        return button;
    }

    private String readFile(String resourcePath) {
        StringBuilder contentBuilder = new StringBuilder();

        // Use ClassLoader to load the resource
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Resource not found: " + resourcePath);
        }

        return contentBuilder.toString();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItem() == gameStepsButton) {
            gameSetupButton.setSelected(false);
            gameSetupButton.setMainColor(Main.Palette.selection.darker());
            
            howToWinButton.setSelected(false);
            howToWinButton.setMainColor(Main.Palette.selection.darker());
            
            gameStepsButton.setMainColor(Main.Palette.selection);
        } else if (e.getItem() == gameSetupButton) {
            gameStepsButton.setSelected(false);
            gameStepsButton.setMainColor(Main.Palette.selection.darker());
            
            howToWinButton.setSelected(false);
            howToWinButton.setMainColor(Main.Palette.selection.darker());
            
            gameSetupButton.setMainColor(Main.Palette.selection);
        } else if (e.getItem() == howToWinButton) {
            gameStepsButton.setSelected(false);
            gameStepsButton.setMainColor(Main.Palette.selection.darker());
            
            gameSetupButton.setSelected(false);
            gameSetupButton.setMainColor(Main.Palette.selection.darker());
            
            howToWinButton.setMainColor(Main.Palette.selection);
        }
    }
}
