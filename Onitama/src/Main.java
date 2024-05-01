package Onitama.src;

import java.awt.*;

import Engine.Core.Engines.GameEngine;
import Engine.Core.Renderer.Scene;
import Engine.Global.Settings;
import Engine.Structures.Vector2D;
import Onitama.src.GameScene.Interface.Card;

public class Main {
    public static GameEngine engine;

    public static Scene gameScene;

    public static void main(String[] args) {
        // Custom Settings
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //Settings.resolution = screenSize;
        Settings.resolution = new Dimension(600,600);
        
        // create engine
        engine = new GameEngine();
        engine.setResolution(Settings.resolution);

        // Create components
        Card test = new Card(new Dimension(200,120), new Vector2D(100,100));
        
        // Set main scene
        gameScene = new Scene();
        gameScene.addComponent(test);

        // start engine
        engine.setMainScene(gameScene);
        engine.start();
    } 
}