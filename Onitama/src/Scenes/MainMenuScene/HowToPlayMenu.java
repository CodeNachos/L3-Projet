package Onitama.src.Scenes.MainMenuScene;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

import Engine.Entities.UI.BlurredArea;
import Engine.Entities.UI.FlatButton;
import Engine.Entities.UI.MenuFrame;
import Engine.Global.Util;
import Engine.Structures.Vector2D;
import Onitama.src.Main;
import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.NewGameMenu.NewGameMenuScene;

public class HowToPlayMenu extends MenuFrame {

    BlurredArea blurredArea;
    public HowToPlayMenu(Dimension area, Vector2D offset) {
        super(area, offset);
        setMainColor(Main.Palette.selection);
        setAccentColor(Main.Palette.selection.brighter());
        setCurvature(20, 20);

        blurredArea = new BlurredArea(Main.engine.getResolution(), BlurredArea.captureBackground(Main.mainMenuScene));
        
        Main.gameScene.addComponent(blurredArea);
    }
    
}
