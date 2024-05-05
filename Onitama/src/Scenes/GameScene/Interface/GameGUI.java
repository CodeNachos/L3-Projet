package Onitama.src.Scenes.GameScene.Interface;

import java.awt.*;

import javax.swing.Box;

import Engine.Entities.UI.ColorArea;
import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.MenuFrame;
import Onitama.src.Main;

public class GameGUI extends MenuFrame {

    ColorArea background;
    TopBar topBar;

    FlatButton validateButton;

    public GameGUI(Dimension area) {
        super(area);

        // Set layout to GridBagLayout for centering components
        setLayout(new GridBagLayout());

        setMainColor(new Color(0, 0, 0, 0));
        setAccentColor(Main.Palette.selection);
        setCurvature(15, 15);
        setBorderWidth(10);

         // Dimension for top bar
         Dimension topBarArea = new Dimension(
            Main.engine.getResolution().width,
            (int) (Main.engine.getResolution().height / 2)
        );

        // Create top bar and position it at the top center
        topBar = new TopBar(topBarArea);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Fill horizontally
        gbc.anchor = GridBagConstraints.NORTH; // Align at the top
        add(topBar, gbc);
    }
    
}
