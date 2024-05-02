package Onitama.src.GameScene;

import java.awt.Dimension;
import java.util.List;

import Engine.Core.Renderer.Scene;
import Engine.Structures.Sprite;
import Engine.Structures.Texture;
import Engine.Structures.Vector2D;
import Onitama.src.Main;
import Onitama.src.GameScene.Entities.Board.Board;
import Onitama.src.GameScene.Entities.Board.PieceSet;
import Onitama.src.GameScene.Entities.Cards.Card;
import Onitama.src.GameScene.Entities.Cards.PlayerHand;
import Onitama.src.GameScene.Interface.GameGUI;

public class GameScene extends Scene {
    // Game Parameters
    List<Card> listOfCards;
    List<Card> gameCards;
    
    // Player ID's
    public static final int PLAYER_RED = 0;
    public static final int PLAYER_BLUE = 1;

    // Turn Info
    public static int currentPlayer = PLAYER_RED;

    // Game board
    public static PieceSet gamePieces;
    public static Board gameBoard;

    // Card Visuals
    public static Texture idleCardTexture;
    public static Texture selectedCardTexture;
    public static Sprite idleCardSprite;
    public static Sprite selectedCardSprite;

    // Player Hands
    public static PlayerHand redHand;
    public static PlayerHand blueHand;

    public GameScene() {
        // Add Board to scene
        createBoard();
        // Add cards to scene
        createCards();
        // Add gui to scene
        GameGUI gui = new GameGUI(Main.engine.getResolution());
        addComponent(gui);
    }



    public void createBoard() {

        // Compute board position and area
        Dimension boardArea = new Dimension(
            2 * Main.engine.getResolution().height / 4,
            2 * Main.engine.getResolution().height / 4

        );
        Vector2D boardPos = new Vector2D(
            (Main.engine.getResolution().width/2) - (boardArea.width/2),
            (Main.engine.getResolution().height / 3) - (boardArea.height/2)
        );

        // Create game pieces
        gamePieces = new PieceSet(boardArea, boardPos);
        // Create game board
        gameBoard = new Board(boardArea, boardPos, gamePieces);
        // Add components to scene
        addComponent(gamePieces);
        addComponent(gameBoard);
    }

    private void createCards() {
        // Idle Card Sprite
        idleCardTexture  = new Texture(
            Main.Palette.background,
            (int)(Main.engine.getResolution().getHeight()/5),
            (int)(Main.engine.getResolution().getHeight()/5),
            10
        );
        idleCardSprite = new Sprite(idleCardTexture);
        idleCardSprite.setBorder(5, Main.Palette.selection, 10);

        // Selected Card Sprite
        selectedCardTexture  = new Texture(
            Main.Palette.selection,
            (int)(Main.engine.getResolution().getHeight()/5),
            (int)(Main.engine.getResolution().getHeight()/5),
            10
        );
        selectedCardSprite = new Sprite(selectedCardTexture);
        selectedCardSprite.setBorder(5, Main.Palette.highlight, 10);

        // Create stand by card
        Vector2D cardPos = new Vector2D(
            (Main.engine.getResolution().width/2) - (int)(idleCardTexture.getWidth()/2),
            (int)(Main.engine.getResolution().height) -(int)(1.5*idleCardTexture.getHeight())
        );

        Card standByCard = new Card("", cardPos, idleCardSprite);
        addComponent(standByCard);

        // Create blue player hand
        Vector2D card1Pos = new Vector2D(
            (int)(gameBoard.getPos().getIntX()/2) - (int)(idleCardTexture.getWidth()/2),
            (int)(gameBoard.getPos().getIntY())
        );
        Vector2D card2Pos = new Vector2D(
            (int)(gameBoard.getPos().getIntX()/2) - (int)(idleCardTexture.getWidth()/2),
            (int)(gameBoard.getPos().getIntY() + gameBoard.getSize().height) - (int)(idleCardTexture.getHeight())
        );
        blueHand = new PlayerHand(PLAYER_BLUE, new Card("", card1Pos, idleCardSprite), new Card("", card2Pos, idleCardSprite));
        addComponent(blueHand.getFirstCard());
        addComponent(blueHand.getSecondCard());

        // Create red player hand
        card1Pos = new Vector2D(
            (int)(card1Pos.getIntX() + gameBoard.getPos().getIntX() + gameBoard.getSize().height),
            (int)(gameBoard.getPos().getIntY())
        );
        card2Pos = new Vector2D(
            (int)(card2Pos.getIntX() + gameBoard.getPos().getIntX() + gameBoard.getSize().height),
            (int)(gameBoard.getPos().getIntY() + gameBoard.getSize().height) - (int)(idleCardTexture.getHeight())
        );
        redHand = new PlayerHand(PLAYER_RED, new Card("", card1Pos, idleCardSprite), new Card("", card2Pos, idleCardSprite));
        addComponent(redHand.getFirstCard());
        addComponent(redHand.getSecondCard());
    
    }

    private void chooseCards() {
        
    }
    
}
