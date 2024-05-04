package Onitama.src.Scenes.GameScene;

import java.awt.Dimension;
import java.util.Iterator;

import Engine.Core.Renderer.Scene;
import Engine.Structures.Sprite;
import Engine.Structures.Texture;
import Engine.Structures.Vector2D;
import Onitama.src.Main;
import Onitama.src.Scenes.GameScene.Entities.Board.Board;
import Onitama.src.Scenes.GameScene.Entities.Board.PieceSet;
import Onitama.src.Scenes.GameScene.Entities.Cards.Card;
import Onitama.src.Scenes.GameScene.Interface.GameGUI;
import Onitama.src.Scenes.GameScene.Scripts.Match;

public class GameScene extends Scene {
    // Game board
    public static PieceSet gamePieces;
    public static Board gameBoard;

    // Card Visuals
    public static Texture idleCardTexture;
    public static Texture selectedCardTexture;
    public static Sprite idleCardSprite;
    public static Sprite selectedCardSprite;

    // Card Positions -> [p1:c1, p1:c2, p2:c1, p2:c2, stand by]
    Vector2D[] cardPositions;
    // Cards -> [p1:c1, p1:c2, p2:c1, p2:c2, stand by]
    Card[] cards;

    Card selectedCard;


    public GameScene() {
        // Create Match
        Match.initialise();

        // Instantiate game entities
        // Add Board to scene
        createBoard();
        // Add cards to scene
        createCards();
        
        // Instantiate GUI
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

        // Initiate card positions
        cardPositions = new Vector2D[5];

        // Compute blue player hand positions
        // Compute player 2 card 1 positon
        Vector2D card1Pos = new Vector2D(
            (int)(gameBoard.getPos().getIntX()/2) - (int)(idleCardTexture.getWidth()/2),
            (int)(gameBoard.getPos().getIntY())
        );
        // Compute player 2 card 2 positon
        cardPositions[2] = card1Pos.clone();
        Vector2D card2Pos = new Vector2D(
            (int)(gameBoard.getPos().getIntX()/2) - (int)(idleCardTexture.getWidth()/2),
            (int)(gameBoard.getPos().getIntY() + gameBoard.getSize().height) - (int)(idleCardTexture.getHeight())
        );
        cardPositions[3] = card2Pos.clone();


        // Compute red player hand positions
        // Compute player 1 card 1 positon
        card1Pos = new Vector2D(
            (int)(card1Pos.getIntX() + gameBoard.getPos().getIntX() + gameBoard.getSize().height),
            (int)(gameBoard.getPos().getIntY())
        );
        cardPositions[0] = card1Pos.clone();
        // Compute player 1 card 2 positon
        card2Pos = new Vector2D(
            (int)(card2Pos.getIntX() + gameBoard.getPos().getIntX() + gameBoard.getSize().height),
            (int)(gameBoard.getPos().getIntY() + gameBoard.getSize().height) - (int)(idleCardTexture.getHeight())
        );
        cardPositions[1] = card2Pos.clone();

        // Compute stand by card position
        Vector2D cardPos = new Vector2D(
            (Main.engine.getResolution().width/2) - (int)(idleCardTexture.getWidth()/2),
            (int)(Main.engine.getResolution().height) -(int)(1.5*idleCardTexture.getHeight())
        );
        cardPositions[4] = cardPos.clone();

        // Initiate cards
        cards = new Card[5];

        // Initiate player 1 card 1
        cards[0] = new Card(
            Match.player1Hand.getFirstCard().getName(),
            cardPositions[0],
            idleCardSprite,
            Match.player1Hand
        );

        // Initiate player 1 card 2
        cards[1] = new Card(
            Match.player1Hand.getSecondCard().getName(),
            cardPositions[1],
            idleCardSprite,
            Match.player1Hand
        );
        // Initiate player 2 card 1
        cards[2] = new Card(
            Match.player2Hand.getFirstCard().getName(),
            cardPositions[2],
            idleCardSprite,
            Match.player2Hand
        );
        // Initiate player 2 card 2
        cards[3] = new Card(
            Match.player2Hand.getSecondCard().getName(),
            cardPositions[3],
            idleCardSprite,
            Match.player2Hand
        );
        // Initiate stand by card
        cards[4] = new Card(
            Match.standByCard.getName(),
            cardPositions[4],
            idleCardSprite,
            null
        );

        for (int c = 0; c < 5; c++) {
            cards[c].addCardToScene(this);
        }
    }
    
}
