package Onitama.src.Scenes.MainMenuScene;


import java.awt.Dimension;

import Engine.Entities.UI.BlurredArea;
import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;
import Onitama.src.Main;

public class HowToPlayMenu extends MenuFrame {

    BlurredArea blurredArea;

    private boolean firstProcess = false;
    public HowToPlayMenu(Dimension area, Vector2D offset) {
        super(area, offset);
        setMainColor(Main.Palette.selection);
        setAccentColor(Main.Palette.selection.brighter());
        setCurvature(20, 20);

        blurredArea = new BlurredArea(Main.engine.getResolution(), BlurredArea.captureBackground(Main.mainMenuScene));
        
        Main.engine.getCurrentScene().addComponent(blurredArea);
    }

    @Override
    public void process(double delta) {
        if (!firstProcess) {
            if (getParent().getComponentZOrder(this) > 1) {
                getParent().setComponentZOrder(blurredArea, 1);
                getParent().setComponentZOrder(this, 0);
            }
            firstProcess = true;
        }
    }
    
}
