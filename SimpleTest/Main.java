package SimpleTest;

import java.awt.Dimension;

import Engine.Core.Engines.GameEngine;
import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.ColorArea;
import Engine.Global.Settings;

public class Main {
    // Engine
    public static GameEngine engine;
    
    // Scenes

    // Main Scene
    public static Scene mainScene;
    public static void main(String args[]) {
        
        engine = new GameEngine();
        engine.setResolution(Settings.resolution);
        // start scene
        mainScene = new Scene(engine.getResolution());
        mainScene.add(new ColorArea(engine.getResolution()));
        
        // start engine
        engine.setCurrentScene(mainScene);
        engine.start(); 
    }
}
