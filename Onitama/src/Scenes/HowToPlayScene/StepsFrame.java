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

public class StepsFrame extends MenuFrame {
    
    private boolean firstProcess = true;

    public StepsFrame() {
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

        JLabel title = new JLabel(" GAME STEPS ", SwingConstants.CENTER);
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(Main.FontManager.getDefaultCustomFont(Font.ITALIC, 22));
        title.setForeground(Main.Palette.foreground);


        JLabel subTitle1 = new JLabel("1. Movement");
        subTitle1.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 16));
        subTitle1.setForeground(Main.Palette.foreground);
        subTitle1.setAlignmentX(CENTER_ALIGNMENT);

        // Create a JTextArea instead of FlatTextField
        JTextArea textContent1 = new JTextArea();
        textContent1.setAlignmentX(CENTER_ALIGNMENT);
        textContent1.setWrapStyleWord(true);
        textContent1.setLineWrap(true);
        textContent1.setEditable(false);
        textContent1.enableInputMethods(false);
        textContent1.setOpaque(false);
        textContent1.setForeground(Main.Palette.foreground);
        textContent1.setFont(Main.FontManager.getDefaultCustomFont(Font.PLAIN, 14));
        textContent1.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        textContent1.setBackground(new Color(0,0,0,0));

        // Read file content and set it to the text area
        String fileContent1 = HowToPlayScene.readFile("Onitama/res/Rules/Steps_Movement.txt");
        textContent1.setText(fileContent1);

        // Put the JTextArea inside a JScrollPane
        JScrollPane scrollPane1 = new JScrollPane(textContent1);
        scrollPane1.setAlignmentX(CENTER_ALIGNMENT);
        scrollPane1.setBorder(null);
        scrollPane1.setPreferredSize(new Dimension(
            (int) (Main.engine.getResolution().width * 0.7),
            (int) (Main.engine.getResolution().height * 0.5)
        ));

        scrollPane1.setOpaque(false);
        scrollPane1.getViewport().setOpaque(false);
        

        JLabel subTitle2 = new JLabel("2. Card Exchange");
        subTitle2.setFont(Main.FontManager.getDefaultCustomFont(Font.BOLD, 16));
        subTitle2.setForeground(Main.Palette.foreground);
        subTitle2.setAlignmentX(CENTER_ALIGNMENT);

        JTextArea textContent2 = new JTextArea();
        textContent2.setAlignmentX(CENTER_ALIGNMENT);
        textContent2.setWrapStyleWord(true);
        textContent2.setLineWrap(true);
        textContent2.setEditable(false);
        textContent2.enableInputMethods(false);
        textContent2.setOpaque(false);
        textContent2.setForeground(Main.Palette.foreground);
        textContent2.setFont(Main.FontManager.getDefaultCustomFont(Font.PLAIN, 14));
        textContent2.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        textContent2.setBackground(new Color(0,0,0,0));

        // Read file content and set it to the text area
        String fileContent2 = HowToPlayScene.readFile("Onitama/res/Rules/Steps_Exchange.txt");
        textContent2.setText(fileContent2);

        // Put the JTextArea inside a JScrollPane
        JScrollPane scrollPane2 = new JScrollPane(textContent2);
        scrollPane2.setAlignmentX(CENTER_ALIGNMENT);
        scrollPane2.setBorder(null);
        scrollPane2.setPreferredSize(new Dimension(
            (int) (Main.engine.getResolution().width * 0.7),
            (int) (Main.engine.getResolution().height * 0.5)
        ));

        scrollPane2.setOpaque(false);
        scrollPane2.getViewport().setOpaque(false);

        add(Box.createVerticalGlue());
        add(title);
        add(Box.createVerticalStrut(30));
        add(subTitle1);
        add(Box.createVerticalStrut(10));
        add(scrollPane1); 
        add(Box.createVerticalStrut(20));
        add(subTitle2);
        add(Box.createVerticalStrut(10));
        add(scrollPane2);
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
