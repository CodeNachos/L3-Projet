package Onitama2.src.Scenes.GameScene;


import java.awt.Dimension;

import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.ColorArea;
import Onitama2.src.Main;
import Onitama2.src.Scenes.GameScene.Entities.Player.Player;

public class GameScene extends Scene {

    Player player1;
    Player player2;

    public GameScene() {
        // Create game
        
        // Instantiate game entities
        // Add Board to scene
        createBoard();
        // Add cards to scene
        createCards();
        
        // Add GUI
        createGUI();
        
        
        

        // Add background
        ColorArea background = new ColorArea(Main.Palette.background, new Dimension(Main.engine.getResolution().width, Main.engine.getResolution().height));
        addComponent(background);
    }



    private void createBoard() {

    }

    private void createCards() {

    }

    private void createGUI() {
        
    }
    
    public static void updateMatch() {
        
    }

}
