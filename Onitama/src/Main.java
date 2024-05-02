package Onitama.src;

import java.awt.*;

import Engine.Core.Engines.GameEngine;
import Engine.Core.Renderer.Scene;
import Engine.Global.Settings;
import Onitama.src.GameScene.GameScene;

public class Main {
    public static GameEngine engine;

    public static Scene gameScene;

    public static void main(String[] args) {

        // Custom Settings
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double aspectRatio = (double) screenSize.width / screenSize.height;
        //Settings.resolution = screenSize;
        Settings.resolution = new Dimension((int)(aspectRatio*600),600);
        Settings.stretch = false;

        // create engine
        engine = new GameEngine();
        engine.setResolution(Settings.resolution);

        gameScene = new GameScene();

        // start engine
        engine.setMainScene(gameScene);
        engine.start();
    } 
    
    public class Palette {
        public static Color background = new Color(40,42,54,255);
        public static Color selection = new Color(68,71,90,255);
        public static Color foreground = new Color(248,248,242,255);
        public static Color highlight = new Color(98,144,164,255);
        
        public static Color cyan = new Color(139,233,253,255);
        public static Color green = new Color(80,250,123,255);
        public static Color orange = new Color(255,184,108, 255);
        public static Color pink = new Color(255,121,198,255);
        public static Color purple = new Color(189,147,249,255);
        public static Color red = new Color(255, 85, 85,255);
        public static Color yellow = new Color(241,250,140,255); 
    }


}
