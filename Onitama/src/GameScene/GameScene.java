package Onitama.src.GameScene;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import Engine.Core.Renderer.Scene;
import Engine.Structures.Sprite;
import Engine.Structures.Texture;
import Engine.Structures.Vector2D;
import Onitama.src.Main;
import Onitama.src.GameScene.Entities.Board.Board;
import Onitama.src.GameScene.Entities.Board.PieceSet;
import Onitama.src.GameScene.Entities.Cards.Card;
import Onitama.src.GameScene.Interface.GameGUI;
import Onitama.src.GameScene.Scripts.Card.CardInfo;
import Onitama.src.GameScene.Scripts.Card.PlayerHand;
import Onitama.src.lib.json.JsonReader;

public class GameScene extends Scene {
    // JSON file reader
    JsonReader jReader;

    // Game Parameters
    static final int CARD_COUNT = 16; 
    static List<CardInfo> listOfCards;
    public static HashMap<String, CardInfo> gameCards;
    
    // Player ID's
    public static final int PLAYER1 = 0; // red
    public static final int PLAYER2 = 1; // blue

    // Turn Info
    public static int currentPlayer = PLAYER1;

    // Game board
    public static PieceSet gamePieces;
    public static Board gameBoard;

    // Card Visuals
    public static Texture idleCardTexture;
    public static Texture selectedCardTexture;
    public static Sprite idleCardSprite;
    public static Sprite selectedCardSprite;

    // Player Hands
    public static PlayerHand player1Hand;
    public static PlayerHand player2Hand;

    public GameScene() {
        // Load all game cards
        jReader = new JsonReader();
        listOfCards = jReader.readJson("Onitama/res/cards.json");

        // Pick game cards
        chooseCards();

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


        // iterate trough card game names
        Iterator<String> cardIter = gameCards.keySet().iterator();

        // Create blue player hand
        Vector2D card1Pos = new Vector2D(
            (int)(gameBoard.getPos().getIntX()/2) - (int)(idleCardTexture.getWidth()/2),
            (int)(gameBoard.getPos().getIntY())
        );
        Vector2D card2Pos = new Vector2D(
            (int)(gameBoard.getPos().getIntX()/2) - (int)(idleCardTexture.getWidth()/2),
            (int)(gameBoard.getPos().getIntY() + gameBoard.getSize().height) - (int)(idleCardTexture.getHeight())
        );
        player2Hand = new PlayerHand(
            PLAYER2, 
            new Card(gameCards.get(cardIter.next()).getName(), card1Pos, idleCardSprite), 
            new Card(gameCards.get(cardIter.next()).getName(), card2Pos, idleCardSprite)
        );
        player2Hand.updateCards();

        // Create red player hand
        card1Pos = new Vector2D(
            (int)(card1Pos.getIntX() + gameBoard.getPos().getIntX() + gameBoard.getSize().height),
            (int)(gameBoard.getPos().getIntY())
        );
        card2Pos = new Vector2D(
            (int)(card2Pos.getIntX() + gameBoard.getPos().getIntX() + gameBoard.getSize().height),
            (int)(gameBoard.getPos().getIntY() + gameBoard.getSize().height) - (int)(idleCardTexture.getHeight())
        );
        player1Hand = new PlayerHand(
            PLAYER1, 
            new Card(gameCards.get(cardIter.next()).getName(), card1Pos, idleCardSprite), 
            new Card(gameCards.get(cardIter.next()).getName(), card2Pos, idleCardSprite)
        );
        player1Hand.updateCards();

        player1Hand.addHandToScene(this);
        player2Hand.addHandToScene(this);


        Card standByCard = new Card(
            gameCards.get(cardIter.next()).getName(), 
            cardPos, 
            idleCardSprite
        );
        standByCard.cardMap.populateActions(standByCard.getName(), PLAYER1);
        standByCard.addCardToScene(this);

    
    }

    private void chooseCards() {
        gameCards = new HashMap<>();
        Set<Integer> set = new HashSet<>();

        Random random = new Random();

        int i = 0;
        while (i<5)
        {
            int cardIndex = random.nextInt(16);
            if (!set.contains(cardIndex)) {
                CardInfo card = listOfCards.get(cardIndex);
                gameCards.put(card.getName(), card);
                set.add(cardIndex);
                ++i;
            }
        }
        return;
    }
    
}
