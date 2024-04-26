package WaffleGame.src.Scenes.MainMenu;

import Engine.Core.Renderer.Scene;
import WaffleGame.src.Main;

public class MainMenuScene extends Scene {
    
    public MainMenuScene() {
        super(Main.engine.getResolution());
        
        add(new MainMenu());
    }
}
