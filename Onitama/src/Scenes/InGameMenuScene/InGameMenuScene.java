package Onitama.src.Scenes.InGameMenuScene;

import java.awt.Dimension;

import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.BlurredArea;
import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;
import Onitama.src.Main;

public class InGameMenuScene extends Scene {
    MenuFrame menuFrame;

    public InGameMenuScene() {
        createInGameMenu();

        BlurredArea blurredArea = new BlurredArea(Main.engine.getResolution(), BlurredArea.captureBackground(Main.gameScene));
        addComponent(blurredArea);

    }

    private void createInGameMenu() {
        Dimension menuArea = new Dimension(
            (int)(Main.engine.getResolution().width/3.5),
            (int)(Main.engine.getResolution().width/3.2)
        );
    
        Vector2D menuOffset = new Vector2D(
            (Main.engine.getResolution().width/2) - (menuArea.width/2),
            (Main.engine.getResolution().height/2) - (menuArea.height/2)
        );
        
        menuFrame = new InGameMenu(menuArea, menuOffset);
        addComponent(menuFrame);
    }

    protected enum MenuActions {
        RESTART,
        NEW_GAME,
        MAIN_MENU
    }

    
}
