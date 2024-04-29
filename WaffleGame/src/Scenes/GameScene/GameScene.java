package WaffleGame.src.Scenes.GameScene;

import java.awt.*;

import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.ColorArea;
import Engine.Global.Util;
import Engine.Structures.Vector2D;
import WaffleGame.src.Main;
import WaffleGame.src.Scenes.GameScene.Entities.History;
import WaffleGame.src.Scenes.GameScene.Entities.WaffleGame;
import WaffleGame.src.Scenes.GameScene.Entities.WaffleTileMap;
import WaffleGame.src.Scenes.GameScene.Interface.GameOverMenu;
import WaffleGame.src.Scenes.GameScene.Interface.StatsMenu;

public class GameScene extends Scene {
    // Scene Components

    // Entities
    public static WaffleGame game;
    public static WaffleTileMap map;
    public static ColorArea background;
    // Interface
    public static StatsMenu statsMenu;
    public static GameOverMenu gameOverMenu;
    // History
    public static History actionHistory;

    // Sprites
    public static Image waffleSprite = Util.getImage("waffleTile.png"); // Load waffle tile sprite
    public static Image poisonSprite = Util.getImage("moldyTile.png"); // Load poison waffle tile sprite

    // Game Config

    // Waffle dimensions
    public static Dimension waffleDimension = new Dimension(10,10);

    // AI enabled
    public static boolean AIEnabled = false;

    public GameScene() {
        super(Main.engine.getResolution());

        // game manager
        game = new WaffleGame();

        // tile map settings
        Vector2D tileMapOffset = new Vector2D(16,16);
        Dimension tileMapArea = new Dimension(
            Main.engine.getResolution().width - 2*(int)tileMapOffset.x,
            Main.engine.getResolution().height - 2*(int)tileMapOffset.y - (int)(Main.engine.getResolution().height * 0.1)
        );

        map = new WaffleTileMap(waffleDimension.width, waffleDimension.height, tileMapArea, tileMapOffset);
        map.populateWaffle();

        // game background
        background = new ColorArea(Main.primaryColor, Main.engine.getResolution());

        actionHistory = new History();        
        
        statsMenu = new StatsMenu();
        // add components in reverse rendering order
        addComponent(game);
        addComponent(actionHistory);
        addComponent(statsMenu);
        addComponent(map);
        addComponent(background);

    }
}
