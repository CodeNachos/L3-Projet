package WaffleGame.src;

import java.awt.Color;

import Engine.Core.Engines.GameEngine;
import Engine.Core.Renderer.Scene;
import Engine.Global.Settings;
import Engine.Global.Util;
import WaffleGame.src.Scenes.MainMenu.MainMenuScene;

/**
 * The Main class initializes and starts the WaffleGame application.
 */
public class Main {
    // Engine
    public static GameEngine engine;
    
    // Scenes
    public static Scene mainScene;
    public static Scene configScene;
    public static Scene gameScene;

    // Color Palette
    public static Color primaryColor = new Color(251,195,83,255);
    public static Color secondaryColor = new Color(235,149,52,255);
    public static Color shadowColor = new Color(197, 123, 41, 255);
    public static Color whiteColor = new Color(255,255,255,255);
    public static Color blackColor = new Color(0,0,0,255);

    /**
     * The main method of the WaffleGame application.
     * Initializes the engine and starts the main game scene.
     * @param args The command line arguments.
     */
    public static void main(String args[]) {
        Settings.applicationName = "Wafflez";

        engine = new GameEngine();
        engine.setResolution(Settings.resolution);
        engine.setIcon(Util.getImage("icon.png"));

        // start scene
        // start engine
        engine.setCurrentScene(new MainMenuScene());
        engine.start(); 
    }
}