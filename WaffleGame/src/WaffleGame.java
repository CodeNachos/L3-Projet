package WaffleGame.src;

import java.awt.Color;
import java.awt.Dimension;

import Engine.Core.Engines.GameEngine;
import Engine.Core.Renderer.Scene;
import Engine.Entities.GameObject;
import Engine.Entities.UI.ColorBackground;
import Engine.Global.Settings;
import Engine.Structures.Vector2D;

public class WaffleGame extends GameObject {

    static GameEngine engine;
    static Scene mainScene;
    
    static WaffleTileMap map;
    static ColorBackground background;

    static int currentPlayer;

    public static void main(String args[]) {
        new WaffleGame();
    }    
    
    public WaffleGame() {
        engine = new GameEngine();
        
        mainScene = new Scene();
        mainScene.addComponent(this);

        Vector2D tileMapOffset = new Vector2D(16,16);
        Dimension tileMapArea = new Dimension(
            Settings.resolution.width - 2*(int)tileMapOffset.x,
            Settings.resolution.height - 2*(int)tileMapOffset.y
        );

        map = new WaffleTileMap(4, 4, tileMapArea, tileMapOffset);
        map.populateWaffle();
        
        background = new ColorBackground(new Color(108, 39, 8, 255), null);
        mainScene.addComponent(background);
        mainScene.addComponent(map);
        
        engine.setCurrentScene(mainScene);
        engine.start(); 

        System.out.println("Player " + currentPlayer + " turn");
    }

    @Override
    public void process() {
        if (isGameOver()) {
            System.out.println("Game Over : Player " + (currentPlayer + 1) % 2 + " won");
            engine.stop();
        }

        if (map.next_player) {
            nextPlayer();
            System.out.println("Player " + currentPlayer + " turn");
            map.next_player = false;
        }
    }

    static boolean isGameOver() {
        return (map.gridmap[0][0] == null);
    }

    static void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % 2; // for 2 players
    }
}
