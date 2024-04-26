package WaffleGame.src.Scenes.ConfigMenu;

import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.ColorArea;
import WaffleGame.src.Main;

public class ConfigMenuScene extends Scene {
    public ConfigMenuScene() {
        super(Main.engine.getResolution());
        
        add(new ConfigMenu());
        
        ColorArea background = new ColorArea(Main.primaryColor, Main.engine.getResolution());
        addComponent(background);
    }
}
