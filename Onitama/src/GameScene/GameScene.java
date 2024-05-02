package Onitama.src.GameScene;

import java.awt.Dimension;

import Engine.Core.Renderer.Scene;
import Engine.Structures.Sprite;
import Engine.Structures.Texture;
import Engine.Structures.Vector2D;
import Onitama.src.Main;
import Onitama.src.GameScene.Entities.Board.Board;
import Onitama.src.GameScene.Entities.Board.PieceSet;
import Onitama.src.GameScene.Entities.Cards.Card;
import Onitama.src.GameScene.Entities.Cards.StandByCard;
import Onitama.src.GameScene.Interface.GameGUI;

public class GameScene extends Scene {

    public static Texture idleCardTexture;
    public static Texture selectedCardTexture;
    public static Sprite idleCardSprite;
    public static Sprite selectedCardSprite;

    public GameScene() {
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

        idleCardTexture  = new Texture(
            Main.Palette.background,
            (int)(Main.engine.getResolution().getHeight()/5),
            (int)(Main.engine.getResolution().getHeight()/5),
            10
        );
        idleCardSprite = new Sprite(idleCardTexture);
        idleCardSprite.setBorder(5, Main.Palette.selection, 10);

        selectedCardTexture  = new Texture(
            Main.Palette.selection,
            (int)(Main.engine.getResolution().getHeight()/5),
            (int)(Main.engine.getResolution().getHeight()/5),
            10
        );
        selectedCardSprite = new Sprite(selectedCardTexture);
        selectedCardSprite.setBorder(5, Main.Palette.highlight, 10);


        Vector2D cardPos = new Vector2D(
            (Main.engine.getResolution().width/2) - (int)(idleCardTexture.getWidth()/2),
            (int)(Main.engine.getResolution().height) -(int)(1.5*idleCardTexture.getHeight())
        );

        Card standByCard = new StandByCard(cardPos, idleCardSprite);
        
        // Create gui
        GameGUI gui = new GameGUI(Main.engine.getResolution());

        // Set game scene
        addComponent(standByCard);
        addComponent(gamePieces);
        addComponent(gameBoard);
        addComponent(gui);
    }
    
}
