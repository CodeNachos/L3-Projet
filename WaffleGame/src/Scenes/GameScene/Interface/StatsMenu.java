package WaffleGame.src.Scenes.GameScene.Interface;

import java.awt.*;

import javax.swing.*;

import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;
import WaffleGame.src.Main;

public class StatsMenu extends MenuFrame {
    public JLabel playerLabel;

    public StatsMenu() {
        super(new Dimension(
            (int)(Main.engine.getResolution().width * 0.5), 
            (int)(Main.engine.getResolution().height * 0.1)
        ));

        int height = (int)(Main.engine.getResolution().height * 0.1);
        setPos(new Vector2D(
            getWidth()/2, 
            Main.engine.getResolution().height - height - 15
            ));
        
        setMainColor(Main.secondaryColor);
        setAccentColor(Main.shadowColor);
        setCurvature(10,10);

        // Use GridBagLayout
        setLayout(new GridBagLayout());
        
        // Set alignment to center
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        // Create and configure label
        playerLabel = new JLabel("PLAYER 1 TURN");
        playerLabel.setForeground(Main.whiteColor);
        playerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Add label
        add(playerLabel, gbc);
    }
}
