package WaffleGame.src.Scenes.MainMenu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.MenuFrame;
import Engine.Global.Util;
import WaffleGame.src.Main;
import WaffleGame.src.Scenes.ConfigMenu.ConfigMenuScene;

public class MainMenu extends MenuFrame {

    public MainMenu() {
        super(Main.engine.getResolution());
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setMainColor(Main.primaryColor);
        setAccentColor(Main.whiteColor);
        setCurvature(10,10);

        JLabel waffleLable = new JLabel();
        waffleLable.setIcon(new ImageIcon(Util.getImage("WaffleGame/res/icon.png")));
        waffleLable.setAlignmentX(CENTER_ALIGNMENT);
        waffleLable.setBorder(BorderFactory.createEmptyBorder(80, 0,30, 0));
        add(waffleLable);

        FlatButton playButton = new FlatButton("PLAY");
        playButton.setMainColor(Main.primaryColor);
        playButton.setAccentColor(new Color(255,255,255,100));
        playButton.setForeground(Main.whiteColor);
        playButton.setCurvature(30,30);
        playButton.setFont(new Font("Arial", Font.BOLD, 32));
        playButton.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));
        playButton.setAlignmentX(CENTER_ALIGNMENT);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.configScene = new ConfigMenuScene();
                Main.engine.setCurrentScene(Main.configScene);
            }
        });

        add(playButton);
        add(Box.createRigidArea(new Dimension(0, 15)));

        FlatButton quitButton = new FlatButton("QUIT");
        quitButton.setMainColor(Main.primaryColor);
        quitButton.setAccentColor(new Color(255,255,255,100));
        quitButton.setForeground(Main.whiteColor);
        quitButton.setCurvature(30,30);
        quitButton.setFont(new Font("Arial", Font.BOLD, 32));
        quitButton.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));
        quitButton.setAlignmentX(CENTER_ALIGNMENT);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.engine.stop();
            }
        });

        add(quitButton);



    }


    
}
