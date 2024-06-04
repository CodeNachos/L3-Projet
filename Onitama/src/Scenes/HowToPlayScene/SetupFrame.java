package Onitama.src.Scenes.HowToPlayScene;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;
import Onitama.src.Main;

public class SetupFrame extends MenuFrame {
    
    private boolean firstProcess = true;

    public SetupFrame() {
        super(
            new Dimension(
                (int)(Main.engine.getResolution().width * 0.6),
                (int)(Main.engine.getResolution().height * 0.6)
            ), new Vector2D(
                Main.engine.getResolution().width * 0.5 - Main.engine.getResolution().width * (0.6/2),
                Main.engine.getResolution().height * 0.2
            )
        );

        setMainColor(new Color(0,0,0,0));
        setAccentColor(new Color(0,0,0,0));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(" GAME SETUP ", SwingConstants.CENTER);
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(Main.FontManager.getDefaultCustomFont(Font.ITALIC, 32));
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
        textContent.setFont(Main.FontManager.getDefaultCustomFont(Font.PLAIN, 22));
        textContent.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        textContent.setBackground(new Color(0,0,0,0));

        // Read file content and set it to the text area
        String fileContent = HowToPlayScene.readFile("Onitama/res/Rules/Setup.txt");
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

        add(Box.createVerticalGlue());
        add(title);
        add(Box.createVerticalStrut(30));
        add(scrollPane); // Add the scroll pane instead of the text area
        add(Box.createVerticalGlue());
    }

    @Override
    public void process(double delta) {
        if (firstProcess) {
            firstProcess = false;
            if (getParent().getComponentZOrder(this) > 1) {
                getParent().setComponentZOrder(this, 0);
            }
        }
    }
}
