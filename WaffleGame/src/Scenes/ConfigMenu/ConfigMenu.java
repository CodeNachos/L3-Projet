package WaffleGame.src.Scenes.ConfigMenu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.FlatTextField;
import Engine.Entities.UI.FlatToggleButton;
import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;
import WaffleGame.src.Main;
import WaffleGame.src.Scenes.GameScene.GameScene;

public class ConfigMenu extends MenuFrame implements ItemListener {

    private FlatToggleButton aiButton;

    public ConfigMenu() {
        super(new Dimension(300, 300));
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        
        setPos(new Vector2D(
            ((Main.engine.getResolution().width/2) - 150), 
            ((Main.engine.getResolution().height/2) - 160))
        );

        setMainColor(Main.shadowColor);
        setAccentColor(Main.secondaryColor);
        setCurvature(30, 30);

        JLabel sizeLabel = new JLabel("WAFFLE SIZE");
        sizeLabel.setForeground(Main.whiteColor);
        sizeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3; // Make the label span across three columns
        gbc.anchor = GridBagConstraints.CENTER; // Set anchor to center
        add(sizeLabel, gbc);

        // two boxes for waffle dimension
        FlatTextField textField1 = new FlatTextField("10", 3);
        textField1.setMainColor(Main.primaryColor);
        textField1.setAccentColor(new Color(255,255,255,100));
        textField1.setForeground(Main.whiteColor);
        textField1.setCurvature(10, 10);
        textField1.setHorizontalAlignment(JTextField.CENTER);
        textField1.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(textField1, gbc);

        // Label for the text fields
        JLabel label = new JLabel("x");
        label.setForeground(Main.whiteColor);
        gbc.gridx = 1; // Place the label between the text fields
        gbc.gridy = 1;
        add(label, gbc);

        FlatTextField textField2 = new FlatTextField("10", 3);
        textField2.setMainColor(Main.primaryColor);
        textField2.setAccentColor(new Color(255,255,255,100));
        textField2.setForeground(Main.whiteColor);
        textField2.setCurvature(10, 10);
        textField2.setHorizontalAlignment(JTextField.CENTER);
        textField2.setFont(new Font("Arial", Font.BOLD, 24));
        textField2.setBorder(null);
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(textField2, gbc);

        // Attach document filter to allow only numbers
        ((AbstractDocument) textField1.getDocument()).setDocumentFilter(new NumberFilter(1, 20));
        ((AbstractDocument) textField2.getDocument()).setDocumentFilter(new NumberFilter(1, 20));

        JLabel aiLabel = new JLabel("ENNABLE AI?");
        aiLabel.setForeground(Main.whiteColor);
        aiLabel.setFont(new Font("Arial", Font.BOLD, 24));
        aiLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3; // Make the label span across three columns
        gbc.anchor = GridBagConstraints.CENTER; // Set anchor to center
        add(aiLabel, gbc);

        aiButton = new FlatToggleButton("DISABLED", false);
        aiButton.setMainColor(Main.primaryColor.darker());
        aiButton.setAccentColor(new Color(255,255,255,100));
        aiButton.setForeground(Main.whiteColor);
        aiButton.setCurvature(10,10);
        aiButton.setFont(new Font("Arial", Font.BOLD, 16));
        aiButton.addItemListener(this);
        aiButton.setPreferredSize(new Dimension(100, aiButton.getPreferredSize().height));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(aiButton, gbc);

        JLabel sepLabel = new JLabel("──────────────");
        sepLabel.setForeground(Main.whiteColor);
        sepLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sepLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3; // Make the label span across three columns
        gbc.anchor = GridBagConstraints.CENTER; // Set anchor to center
        add(sepLabel, gbc);

        FlatButton startButton = new FlatButton("START");
        startButton.setMainColor(Main.primaryColor);
        startButton.setAccentColor(new Color(255,255,255,100));
        startButton.setForeground(Main.whiteColor);
        startButton.setCurvature(30,30);
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        startButton.setAlignmentX(CENTER_ALIGNMENT);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value1 = 10;
                int value2 = 10;
                try {
                    value1 = Integer.parseInt(textField1.getText());
                    value2 = Integer.parseInt(textField2.getText());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace(); 
                }
                GameScene.waffleDimension = new Dimension(value1, value2);
                GameScene.AIEnabled = aiButton.isSelected();
                Main.gameScene = new GameScene();
                Main.engine.setCurrentScene(Main.gameScene);
                GameScene.actionHistory.clearRecords();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        add(startButton, gbc);

    }

    private static class NumberFilter extends DocumentFilter {
        private final Pattern pattern = Pattern.compile("\\d*");
        private final int min;
        private final int max;

        public NumberFilter(int min, int max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string.isEmpty() || pattern.matcher(string).matches()) {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
                int newValue = Integer.parseInt(currentText);
                if (newValue >= min && newValue <= max) {
                    super.insertString(fb, offset, string, attr);
                }
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text.isEmpty() || pattern.matcher(text).matches()) {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
                int newValue = Integer.parseInt(newText);
                if (newValue >= min && newValue <= max) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (aiButton.isSelected()) {
            aiButton.setText("ENABLED");
            aiButton.setMainColor(Main.primaryColor);
        } else {
            aiButton.setText("DISABLED");
            aiButton.setMainColor(Main.primaryColor.darker());
        }
    }
}
