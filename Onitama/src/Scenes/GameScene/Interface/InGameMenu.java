package Onitama.src.Scenes.GameScene.Interface;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

import Engine.Entities.UI.BlurredArea;
import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;
import Onitama.src.Main;

public class InGameMenu extends MenuFrame {
    BlurredArea blurredArea;

    public InGameMenu(Dimension area, Vector2D offset) {
        
        super(area, offset);

        setMainColor(Main.Palette.selection);
        setAccentColor(Main.Palette.selection.brighter());
        setCurvature(20, 20);

        blurredArea = new BlurredArea(Main.engine.getResolution(), BlurredArea.captureBackground(Main.gameScene));
        
        Main.gameScene.addComponent(blurredArea);

        Main.gameScene.setComponentZOrder(blurredArea, 0);

    }

    @Override
    public void input(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_Q) {
            Main.gameScene.removeComponent(blurredArea);
            Main.gameScene.removeComponent(this);
            Main.gameScene.setEnabledGUI(true);
        }
    }
    
}
