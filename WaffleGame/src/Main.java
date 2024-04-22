package WaffleGame.src;

import java.awt.Color;
import java.awt.Dimension;

import Engine.Core.Engines.GameEngine;
import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.ColorBackground;
import Engine.Entities.UI.UIFrame;
import Engine.Global.Settings;
import Engine.Structures.Vector2D;

public class Main {
    // Engine
    public static GameEngine engine;
    

    // Scenes

    // Main Scene
    public static Scene mainScene;
    // Main Scene Components
    public static WaffleGame game;
    public static WaffleTileMap map;
    public static ColorBackground background;


    public static void main(String args[]) {
        engine = new GameEngine();

        mainScene = new Scene();

        // game manager
        game = new WaffleGame();

        // tile map settings
        Vector2D tileMapOffset = new Vector2D(16,16);
        Dimension tileMapArea = new Dimension(
            Settings.resolution.width - 2*(int)tileMapOffset.x,
            Settings.resolution.height - 2*(int)tileMapOffset.y
        );

        map = new WaffleTileMap(4, 4, tileMapArea, tileMapOffset);
        map.populateWaffle();

        // game background
        background = new ColorBackground(new Color(108, 39, 8, 255), null);

        UIFrame swingFrame = new UIFrame(Settings.resolution);
        swingFrame.addUIComponent(new Background());

        // add components in rendering order
        mainScene.addComponent(background);
        mainScene.addComponent(map);
        mainScene.addComponent(game);
        mainScene.addComponent(swingFrame);
        
        // start engine
        engine.setCurrentScene(mainScene);
        engine.start(); 
    }
}
