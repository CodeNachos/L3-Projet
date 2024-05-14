package Onitama.src.Scenes.GameScene;

import java.awt.Color;
import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.ColorArea;
import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Sprite;
import Engine.Structures.Texture;
import Engine.Structures.Vector2D;
import Onitama.src.Main;
import Onitama.src.Scenes.GameScene.Entities.Board.Board;
import Onitama.src.Scenes.GameScene.Entities.Board.PieceSet;
import Onitama.src.Scenes.GameScene.Entities.Bots.Bot;
import Onitama.src.Scenes.GameScene.Entities.Cards.Card;
import Onitama.src.Scenes.GameScene.Interface.TopBar;
import Onitama.src.Scenes.GameScene.Scripts.GameConfiguration;

public class GameScene extends Scene {

    // Game
    public static GameConfiguration game;

    // Game board
    public static PieceSet gamePieces;
    public static Board gameBoard;

    // Card Visuals
    public static Texture idleCardTexture;
    public static Texture selectedCardTexture;
    public static Sprite idleCardSprite;
    public static Sprite selectedCardSprite;

    // Card Positions -> [p1:c1, p1:c2, p2:c1, p2:c2, stand by]
    static Vector2D[] cardPositions;
    // Cards -> [p1:c1, p1:c2, p2:c1, p2:c2, stand by]
    static Card[] cards;


    public GameScene() {
        // Create game
        game = new GameConfiguration();

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

        addComponent(new Bot(GameConfiguration.PLAYER1, 5));
        //addComponent(new Bot(GameConfiguration.PLAYER2, 1));
    }



    private void createBoard() {

        // Compute board position and area
        Dimension boardArea = new Dimension(
            (int)(2.3 * Main.engine.getResolution().height / 4),
            (int)(2.3 * Main.engine.getResolution().height / 4)

        );
        Vector2D boardPos = new Vector2D(
            (Main.engine.getResolution().width/2) - (boardArea.width/2),
            (Main.engine.getResolution().height / 2.5) - (boardArea.height/2.3)
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
            (int)(gameBoard.getPos().getIntY() + (gameBoard.getSize().height / 2) - (1.1*idleCardTexture.getHeight()))
        );
        // Compute player 2 card 2 positon
        cardPositions[2] = card1Pos.clone();
        Vector2D card2Pos = new Vector2D(
            (int)(gameBoard.getPos().getIntX()/2) - (int)(idleCardTexture.getWidth()/2),
            (int)(gameBoard.getPos().getIntY() + (gameBoard.getSize().height / 2) + (0.1*idleCardTexture.getHeight()))
        );
        cardPositions[3] = card2Pos.clone();


        // Compute red player hand positions
        // Compute player 1 card 1 positon
        card1Pos = new Vector2D(
            (int)(card1Pos.getIntX() + gameBoard.getPos().getIntX() + gameBoard.getSize().height),
            (int)(gameBoard.getPos().getIntY() + (gameBoard.getSize().height / 2) - (1.1*idleCardTexture.getHeight()))
        );
        cardPositions[0] = card1Pos.clone();
        // Compute player 1 card 2 positon
        card2Pos = new Vector2D(
            (int)(card2Pos.getIntX() + gameBoard.getPos().getIntX() + gameBoard.getSize().height),
            (int)(gameBoard.getPos().getIntY() + (gameBoard.getSize().height / 2) + (0.1*idleCardTexture.getHeight()))
        );
        cardPositions[1] = card2Pos.clone();

        // Compute stand by card position
        Vector2D cardPos = new Vector2D(
            (Main.engine.getResolution().width/2) - (int)(idleCardTexture.getWidth()/2),
            (int)(Main.engine.getResolution().height) -(int)(1.2*idleCardTexture.getHeight())
        );
        cardPositions[4] = cardPos.clone();

        // Initiate cards
        cards = new Card[5];

        // Initiate player 1 card 1
        cards[0] = new Card(
            game.player1Hand.getFirstCard().getName(),
            cardPositions[0],
            idleCardSprite,
            game.player1Hand
        );

        // Initiate player 1 card 2
        cards[1] = new Card(
            game.player1Hand.getSecondCard().getName(),
            cardPositions[1],
            idleCardSprite,
            game.player1Hand
        );
        // Initiate player 2 card 1
        cards[2] = new Card(
            game.player2Hand.getFirstCard().getName(),
            cardPositions[2],
            idleCardSprite,
            game.player2Hand
        );
        // Initiate player 2 card 2
        cards[3] = new Card(
            game.player2Hand.getSecondCard().getName(),
            cardPositions[3],
            idleCardSprite,
            game.player2Hand
        );
        // Initiate stand by card
        cards[4] = new Card(
            game.standByCard.getName(),
            cardPositions[4],
            idleCardSprite,
            null
        );

        for (int c = 0; c < 5; c++) {
            cards[c].addCardToScene(this);
        }
    }

    private void createGUI() {
        Dimension sideDimension = new Dimension(
            (int)(Main.engine.getResolution().width /4),
            (int)(5 * Main.engine.getResolution().height / 8)
        );
        Vector2D sideOffset = new Vector2D(
            (int)(0),
            (int)(Main.engine.getResolution().height / 8)
        );
        MenuFrame blueSide = new MenuFrame(sideDimension, sideOffset);
        blueSide.setMainColor(new Color(139,233,253,70));
        blueSide.setAccentColor(new Color(139,233,253,100));
        blueSide.setCurvature(5, 5);
        blueSide.setBorderWidth(5);
    
        addComponent(blueSide);

        sideOffset = new Vector2D(
            (int)(Main.engine.getResolution().width - sideDimension.width),
            (int)(Main.engine.getResolution().height / 8)
        );
        MenuFrame redSide = new MenuFrame(sideDimension, sideOffset);
        redSide.setMainColor(new Color(255, 85, 85,70));
        redSide.setAccentColor(new Color(255, 85, 85,100));
        redSide.setCurvature(5, 5);
        redSide.setBorderWidth(5);

        addComponent(redSide);
        
        // Dimension for top bar
        Dimension topBarArea = new Dimension(
            Main.engine.getResolution().width/3,
            (int) (Main.engine.getResolution().height /10)
        );
        Vector2D topBarPos = new Vector2D(
            (int)(Main.engine.getResolution().width/2 - (topBarArea.width/2)),
            0
        );
        TopBar gui = new TopBar(topBarArea, topBarPos);
        addComponent(gui); 
    }
    

    public static boolean canUndo() {
        return game.canUndo();
    }

    public static boolean canRedo() {
        return game.canRedo();
    }

    public static void undo() {
        game.undo();
        updateCards();
        gamePieces.updatePieces();
    }

    public static void redo() {
        game.redo();
        updateCards();
        gamePieces.updatePieces();
    }

    public static void save() {
        game.save();
    }

    public static void loadSave(String saveFile) {
        
        if (saveFile == null) {
            saveFile = GameConfiguration.DEFAULT_SAVE_FILE;
        }

        try {

            FileInputStream fileInputStream = new FileInputStream(saveFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            game = (GameConfiguration) objectInputStream.readObject();
            
            updateCards();
            gamePieces.updatePieces();

            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public static void updateMatch() {

        game.play();

        gamePieces.updatePieces();
        updateCards();

        if (game.isGameOver()) {
            System.out.println("Player " + (game.getNextPlayer() == GameConfiguration.PLAYER1 ? "RED" : "BLUE") + " won");
            Main.engine.forceRefresh();
            Main.engine.pause();
        }
    }

    private static void updateCards() {
        cards[0].setName(game.player1Hand.getFirstCard().getName());
        cards[1].setName(game.player1Hand.getSecondCard().getName());
        cards[2].setName(game.player2Hand.getFirstCard().getName());
        cards[3].setName(game.player2Hand.getSecondCard().getName());

        
        // cards[4].setName(game.getSelectedCard());
        cards[4].setName(game.standByCard.getName());
    }

}
