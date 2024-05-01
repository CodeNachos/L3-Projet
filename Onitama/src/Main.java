package Onitama.src;

import java.awt.*;

import Engine.Core.Engines.GameEngine;
import Engine.Core.Renderer.Scene;
import Engine.Global.Settings;
import Engine.Structures.Vector2D;
import Onitama.src.GameScene.Entities.Board.Board;
import Onitama.src.GameScene.Entities.Board.PieceSet;
import Onitama.src.GameScene.Interface.GameGUI;

public class Main {
    public static GameEngine engine;

    public static Scene gameScene;

    public static void main(String[] args) {
        // Custom Settings
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Settings.resolution = screenSize;
        
        // create engine
        engine = new GameEngine();
        engine.setResolution(Settings.resolution);

        // Create components
        GameGUI gui = new GameGUI(screenSize);

        Dimension boardArea = new Dimension(
            2 * Main.engine.getResolution().height / 4,
            2 * Main.engine.getResolution().height / 4

        );

        Vector2D boardPos = new Vector2D(
            (Main.engine.getResolution().width/2) - (boardArea.width/2),
            (Main.engine.getResolution().height / 3) - (boardArea.height/2)
        );

        PieceSet gamePieces = new PieceSet(boardArea, boardPos);
        Board gameBoard = new Board(boardArea, boardPos, gamePieces);
        
        // Set main scene
        gameScene = new Scene();
        gameScene.addComponent(gamePieces);
        gameScene.addComponent(gameBoard);
        gameScene.addComponent(gui);

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
