package Onitama.src.Scenes.GameScene;


import java.awt.Color;
import java.awt.Dimension; 
import java.util.*;

import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.ColorArea;
import Engine.Entities.UI.MenuFrame;
import Engine.Global.Util;
import Engine.Structures.Vector2D;
import Onitama.src.Scenes.GameScene.Interface.TopBar;
import Onitama.src.JsonReader;
import Onitama.src.Scenes.GameScene.Scripts.Card.CardInfo;
import Onitama.src.Scenes.GameScene.Scripts.States.Action;
import Onitama.src.Scenes.GameScene.Scripts.States.State;
import Onitama.src.Main;
import Onitama.src.Scenes.GameScene.Entities.Board.Board;
import Onitama.src.Scenes.GameScene.Entities.Board.Piece;
import Onitama.src.Scenes.GameScene.Entities.Board.Piece.PieceType;
import Onitama.src.Scenes.GameScene.Entities.Card.Card;
import Onitama.src.Scenes.GameScene.Entities.Player.Player;

public class GameScene extends Scene {
    public static final Vector2D RED_THRONE = new Vector2D(2, 4);
    public static final Vector2D BLUE_THRONE = new Vector2D(2, 0);

    public static final int PLAYER1 = 0; // red
    public static final int PLAYER2 = 1; // blue

    public static int currentPlayer = PLAYER1;

    public static Player player1;
    public static Player player2;

    public static Board gameBoard;

    public static HashMap<String, CardInfo> gameCards;

    public GameScene() {
        // Create game
        
        // Pick game cards 
        chooseCards();
        
        // Instantiate game entities
        createBoard();
        createPlayers();

        player1.addToScene(this);
        player2.addToScene(this);
        addComponent(gameBoard);

        // Add GUI
        createGUI();
        
        // Add background
        ColorArea background = new ColorArea(Main.Palette.background, new Dimension(Main.engine.getResolution().width, Main.engine.getResolution().height));
        addComponent(background);

        //State s = getGameState();
        //System.out.println(s.gameCards.get(0));
        //State s2 = s.nextConfig(new Action(player1.getFirstCard(), player1.getPieces().get(2).getMapPosition(), s.possiblePositions(player1.getPieces().get(2).getMapPosition(), player1.getFirstCard()).get(0)));
        //System.out.println(s2.gameCards.get(4));

        //player1.enableAI(3);
        player2.enableAI(5);

        updateIteractableEntities();
    }

    public GameScene(State gameState) {
        
    }

    public static State getGameState() {
        ArrayList<String> cards = new ArrayList<>();

        cards.add(player1.getFirstCard());
        cards.add(player1.getSecondCard());
        cards.add(player2.getFirstCard());
        cards.add(player2.getSecondCard());
        if (player1.getStandByCard() != null) {
            cards.add(player1.getStandByCard().getName());
        } else {
            cards.add(player2.getStandByCard().getName());
        }

        return new State(getPlayerPieces(PLAYER1), getPlayerPieces(PLAYER2), cards, currentPlayer);
    }

    public static int getCurrentPlayer() {
        return currentPlayer;
    }

    public static HashMap<String, CardInfo> getGameCards() {
        return gameCards;
    } 

    public static Piece getPiece(int l, int c) {
        Piece piece = player1.checkPiecePresence(l, c);
        if (piece != null) {
            return piece;
        }

        piece = player2.checkPiecePresence(l, c);
        if (piece != null) {
            return piece;
        }

        return null;
    }

    public static boolean isPieceSelected() { 
        return gameBoard.getSelectedTile() != null;
    }

    public static Piece getSelectedPiece() {
        if (gameBoard.getSelectedTile() == null)
            return null;
        
        return getPiece(gameBoard.getSelectedTile());
    }

    public static Piece getPiece(Vector2D position) {
        int l = position.getIntY();
        int c = position.getIntX();

        return getPiece(l, c);
    }

    public static boolean isCardSelected() {
        return (player1.getSelectedCard() != null) || (player2.getSelectedCard() != null);
    }

    public static Card getSelectedCard() {
        Card card = player1.getSelectedCard();

        if (card != null)
            return card;

        card = player2.getSelectedCard();
        
        return card;
    }

    public static void clearSelectedCard() {
        player1.setSelectedCard(null);
        player2.setSelectedCard(null);
    }

    public static boolean isActionSelected() {
        return gameBoard.getSelectedAction() != null;
    }

    public static Vector2D getSelectedAction() {
        return gameBoard.getSelectedAction();
    }
    

    public static ArrayList<Piece> getPlayerPieces(int player) {
        ArrayList<Piece> pieces = null;

        if (player == PLAYER1) {
            pieces = player1.getPieces();
        } else if (player == PLAYER2) {
            pieces = player2.getPieces();
        } else {
            Util.printError("Invalid player");
        }

        return pieces;
    }

    public static int getNextPlayer() {
        return (currentPlayer + 1) % 2;
    }

    public static void changePlayer() {
        currentPlayer = (currentPlayer + 1) % 2;
    }

    public static boolean gameOver() {
        return conqueredKing() || capturedKing();
    }

    private static boolean conqueredKing() {
        for (Piece p : player1.getPieces()) {
            if (p.getType() == PieceType.RED_KING && p.getPosition().equals(BLUE_THRONE)) {
                return true;
            }
        }

        for (Piece p : player2.getPieces()) {
            if (p.getType() == PieceType.BLUE_KING && p.getPosition().equals(RED_THRONE)) {
                return true;
            }
        }

        return false;
    }

    private static boolean capturedKing() {

        return ((!checkPresence(PieceType.BLUE_KING) && checkPresence(PieceType.RED_KING))
                || (checkPresence(PieceType.BLUE_KING) && !checkPresence(PieceType.RED_KING)));
    }

    public static boolean checkPresence(PieceType type) {
        for (Piece p : player1.getPieces()) {
            if (p.getType() == type) {
                 return true;
            }
        }

        for (Piece p : player2.getPieces()) {
            if (p.getType() == type) {
                 return true;
            }
        }

        return false;
    }

    public static void exchangeCards() {
        String tmp = getSelectedCard().getName();
        if (currentPlayer == PLAYER1) {
            getSelectedCard().setName(player1.getStandByCard().getName());
            player1.getStandByCard().setName(tmp);
            player2.setStandBy(player1.getStandByCard());
            player1.removeStandBy();
        } else {
            getSelectedCard().setName(player2.getStandByCard().getName());
            player2.getStandByCard().setName(tmp);
            player1.setStandBy(player2.getStandByCard());
            player2.removeStandBy();
        }
    }
    
    public static void updateMatch() {
        if (!isPieceSelected() || !isCardSelected() || !isActionSelected()) {
            Util.printWarning("Update match called without update conditon met");
            if (!isPieceSelected())
                Util.printWarning("Piece not selected");
            if (!isCardSelected())
                Util.printWarning("Card not selected");
            if (!isActionSelected())
                Util.printWarning("Action not selected");
            return;
        }

        if (getPiece(getSelectedAction()) != null) {
            for (Piece p : getPlayerPieces(getNextPlayer())) {
                if (p.getPosition().equals(getSelectedAction())) {
                    getPlayerPieces(getNextPlayer()).remove(p);
                    break;
                }
            }
        }

        if (currentPlayer == GameScene.PLAYER1) {
            player1.movePiece(getSelectedPiece(), getSelectedAction());
            
        } else {
            player2.movePiece(getSelectedPiece(), getSelectedAction());
        }
        
        gameBoard.setSelectedTile(null); gameBoard.setSelectedAction(null);

        player1.update(); player2.update();

        exchangeCards();

        player1.setSelectedCard(null); player2.setSelectedCard(null);

        changePlayer();

        if (gameOver()) {
            System.out.println("Player " + (getNextPlayer() == GameScene.PLAYER1 ? "RED" : "BLUE") + " won");
            Main.engine.forceRefresh();
            Main.engine.pause();
        }

        updateIteractableEntities();

    }

    public static void setAction(Action act) {
        if (currentPlayer == PLAYER1) {
            player1.setSelectedCardByName(act.getCard());
        } else {
            player2.setSelectedCardByName(act.getCard());
        }

        gameBoard.setSelectedTile(act.getPiece());
        gameBoard.setSelectedAction(act.getMove());
    }

    public static void updateIteractableEntities() {
        if (currentPlayer == PLAYER1) {
            if (player1.isAiEnabled()) {
                player1.setCardsInteractable(false);
                player2.setCardsInteractable(false);
                gameBoard.setIteractable(false);
            } else {
                player1.setCardsInteractable(true);
                player2.setCardsInteractable(true);
                gameBoard.setIteractable(true);
            }
        } else {
            if (player2.isAiEnabled()) {
                player1.setCardsInteractable(false);
                player2.setCardsInteractable(false);
                gameBoard.setIteractable(false);
            } else {
                player1.setCardsInteractable(true);
                player2.setCardsInteractable(true);
                gameBoard.setIteractable(true);
            }
        }
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

        // Create game board
        gameBoard = new Board(boardArea, boardPos);
    }

    private void chooseCards() {
        JsonReader jReader = new JsonReader();
        List<CardInfo> listOfCards = jReader.readJson("Onitama/res/Cards/cards.json");

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

    private void createPlayers() {
        Iterator<String> cardIter = gameCards.keySet().iterator();
        
        player1 = new Player(PLAYER1, cardIter.next(), cardIter.next());

        player2 = new Player(PLAYER2, cardIter.next(), cardIter.next());

        if (currentPlayer == PLAYER1) {
            player1.createStandBy(this, cardIter.next());
        } else {
            player2.createStandBy(this, cardIter.next());
        }
    } 

    private void createGUI() {
        Dimension sideDimension = new Dimension(
            (int)(Main.engine.getResolution().width /4),
            (int)(5 * Main.engine.getResolution().height / 8)
        );
        Vector2D sideOffset = new Vector2D(
            (int)(Main.engine.getResolution().width - sideDimension.width),
            (int)(Main.engine.getResolution().height / 8)
        );
        MenuFrame blueSide = new MenuFrame(sideDimension, sideOffset);
        blueSide.setMainColor(new Color(139,233,253,70));
        blueSide.setAccentColor(new Color(139,233,253,100));
        blueSide.setCurvature(5, 5);
        blueSide.setBorderWidth(5);
    
        addComponent(blueSide);

        sideOffset = new Vector2D(
            (int)(0),
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

    

}
