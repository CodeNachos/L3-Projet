package Onitama.src.Scenes.InGameMenuScene;

import java.awt.Dimension;

import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.BlurredArea;
import Engine.Structures.Vector2D;
import Onitama.src.Main;

public class InGameMenuScene extends Scene {
    public InGameMenuScene() {
        Dimension menuArea = new Dimension(
            (int)(Main.engine.getResolution().width/3.5),
            (int)(Main.engine.getResolution().width/3.2)
        );
    
        Vector2D menuOffset = new Vector2D(
            (Main.engine.getResolution().width/2) - (menuArea.width/2),
            (Main.engine.getResolution().height/2) - (menuArea.height/2)
        );
        
        InGameMenu menu = new InGameMenu(menuArea, menuOffset);
        addComponent(menu);

        BlurredArea blurredArea = new BlurredArea(Main.engine.getResolution(), BlurredArea.captureBackground(Main.gameScene));
        addComponent(blurredArea);

    }
}
