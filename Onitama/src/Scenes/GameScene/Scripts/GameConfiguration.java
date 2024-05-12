package Onitama.src.Scenes.GameScene.Scripts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import Engine.Global.Util;
import Engine.Structures.Vector2D;
import Onitama.src.Scenes.GameScene.Scripts.Card.CardInfo;
import Onitama.src.Scenes.GameScene.Scripts.Card.PlayerHand;
import Onitama.src.Scenes.GameScene.Scripts.Piece.Piece;
import Onitama.src.Scenes.GameScene.Scripts.Piece.PieceType;
import Onitama.src.lib.json.JsonReader;

public class GameConfiguration implements Serializable {    
    // JSON file reader
    public JsonReader jReader;

    
    // Game constants

    static final Vector2D RED_THRONE = new Vector2D(2, 4);
    static final Vector2D BLUE_THRONE = new Vector2D(2, 0);

    // Player ID's
    public static final int PLAYER1 = 0; // red
    public static final int PLAYER2 = 1; // blue
    
    public final int CARD_COUNT = 16; 

    // Card Deck
    public static List<CardInfo> listOfCards;


    // Game State

    // Cards picked for the match -> [p1:c1, p1:c2, p2:c1, p2:c2, stand by]
    public HashMap<String, CardInfo> gameCards;

    // Player Hands
    public PlayerHand player1Hand;
    public PlayerHand player2Hand;
    // Stand By Card
    public CardInfo standByCard;

    // Players pieces
    public ArrayList<Piece> player1Pieces;
    public ArrayList<Piece> player2Pieces; 

    
    // Turn Info
    public int currentPlayer = PLAYER1;
    public CardInfo selectedCard = null; // current player hand selected card
    public Vector2D selectedPiece = null; // current player selected piece
    public Vector2D selectedAction = null; // current player selected action tile

    public GameConfiguration() {
        // Load all game cards
        jReader = new JsonReader();
        listOfCards = jReader.readJson("Onitama/res/Cards/cards.json");
        
        // Pick game cards
        chooseCards();

        // Distribute cards
        assignCards();

        // initiate pieces
        initiatePieces();
    }

    public GameConfiguration(
        PlayerHand ph1, 
        PlayerHand ph2, 
        CardInfo stb, 
        ArrayList<Piece> p1p,
        ArrayList<Piece> p2p,
        int currentPlayer
    ) {
        this.player1Hand = ph1.clone();
        this.player2Hand = ph2.clone();

        this.standByCard = stb;

        this.player1Pieces = copyPieces(p1p);
        this.player2Pieces = copyPieces(p2p);

        this.currentPlayer = currentPlayer;
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

    private void assignCards() {
        // iterate trough card game names
        Iterator<String> cardIter = gameCards.keySet().iterator();
        
        // initiate player's 1 hand
        player1Hand = new PlayerHand(
            PLAYER1, 
            gameCards.get(cardIter.next()), 
            gameCards.get(cardIter.next())
        );
        // initiate player's 2 hand
        player2Hand = new PlayerHand(
            PLAYER2, 
            gameCards.get(cardIter.next()), 
            gameCards.get(cardIter.next())
        );
        
        // set extra card
        standByCard = gameCards.get(cardIter.next());
    }

    private void initiatePieces() {
        player1Pieces = new ArrayList<>();
        player1Pieces.add(new Piece(PieceType.RED_PAWN, new Vector2D(0,4)));
        player1Pieces.add(new Piece(PieceType.RED_PAWN, new Vector2D(1,4)));
        player1Pieces.add(new Piece(PieceType.RED_KING, new Vector2D(2,4)));
        player1Pieces.add(new Piece(PieceType.RED_PAWN, new Vector2D(3,4)));
        player1Pieces.add(new Piece(PieceType.RED_PAWN, new Vector2D(4,4)));

        player2Pieces = new ArrayList<>();
        player2Pieces.add(new Piece(PieceType.BLUE_PAWN, new Vector2D(0,0)));
        player2Pieces.add(new Piece(PieceType.BLUE_PAWN, new Vector2D(1,0)));
        player2Pieces.add(new Piece(PieceType.BLUE_KING, new Vector2D(2,0)));
        player2Pieces.add(new Piece(PieceType.BLUE_PAWN, new Vector2D(3,0)));
        player2Pieces.add(new Piece(PieceType.BLUE_PAWN, new Vector2D(4,0)));

    }

    public void setSelectedCard(String card) {
        if (card.isEmpty()) {
            selectedCard = null;
        } else {
            selectedCard = gameCards.get(card);
        }

        selectedAction = null;
    }

    public PlayerHand getPlayerHand(int player) {
        PlayerHand hand = null;

        if (player == PLAYER1) { 
            hand = player1Hand;    
        } else if (player == PLAYER2) {
            hand = player2Hand;
        } else {
            Util.printError("Invalid player");
        }

        return hand;
    }

    public String getSelectedCard() {
        if (selectedCard == null) {
            return "";
        }

        return selectedCard.getName();
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<Piece> getPlayerPieces(int player) {
        ArrayList<Piece> pieces = null;

        if (player == PLAYER1) {
            pieces = player1Pieces;
        } else if (player == PLAYER2) {
            pieces = player2Pieces;
        } else {
            Util.printError("Invalid player");
        }

        return pieces;
    }

    public void setSelectedPiece(Vector2D piece) {
        if (piece == null)
            selectedPiece = null;
        else {
            selectedPiece = piece.clone();
        }

        selectedAction = null;
    }

    public Vector2D getSelectedPiece() {
        if (selectedPiece == null) 
            return null;
        
        return selectedPiece.clone();
    }

    public void setSelectedAction(Vector2D tile) {
        if (tile == null)
            selectedAction = null;
        else {
            selectedAction = tile.clone();
        }
    }

    public Vector2D getSelectedAction() {
        if (selectedAction == null) 
            return null;
        
        return selectedAction.clone();
    }

    public int getNextPlayer() {
        return (currentPlayer + 1) % 2;
    }

    public void changePlayer() {
        currentPlayer = (currentPlayer + 1) % 2;
    }

    public boolean isPieceSelected() {
        return selectedPiece != null;
    }

    public boolean isCardSelected() {
        return selectedCard != null;
    }

    public boolean isActionSelected() {
        return selectedAction != null;
    }

    public void exchangeCards() {
        String tmp = standByCard.getName();

        standByCard = gameCards.get(getSelectedCard());

        PlayerHand hand = getPlayerHand(currentPlayer);
        if (getSelectedCard().equals(hand.getFirstCard().getName())) {
            hand.setFirstCard(gameCards.get(tmp));
            
        } else {
            hand.setSecondCard(gameCards.get(tmp));
        }
        
    }

    public void exchangeCards(PlayerHand ph1, PlayerHand ph2, CardInfo selectedCard, CardInfo standBy)  {
        
        
    }

    public boolean gameOver() {
        return conqueredKing() || capturedKing();
    }

    private boolean conqueredKing() {
        for (Piece p : player1Pieces) {
            if (p.getType() == PieceType.RED_KING && p.getPosition().equals(BLUE_THRONE)) {
                return true;
            }
        }

        for (Piece p : player2Pieces) {
            if (p.getType() == PieceType.BLUE_KING && p.getPosition().equals(RED_THRONE)) {
                return true;
            }
        }


        return false;
    }

    private boolean capturedKing() {

        return ((!checkPresence(PieceType.BLUE_KING) && checkPresence(PieceType.RED_KING))
                || (checkPresence(PieceType.BLUE_KING) && !checkPresence(PieceType.RED_KING)));
    }

    public boolean checkPresence(PieceType type) {
        for (Piece p : player1Pieces) {
            if (p.getType() == type) {
                 return true;
            }
        }

        for (Piece p : player2Pieces) {
            if (p.getType() == type) {
                 return true;
            }
        }

        return false;
    }

    public boolean checkPresence(Vector2D position) {
        for (Piece p : player1Pieces) {
            if (p.getPosition().equals(position)) {
                 return true;
            }
        }

        for (Piece p : player2Pieces) {
            if (p.getPosition().equals(position)) {
                 return true;
            }
        }

        return false;
    }


    /* Start of AI's interface */
    /* Please discuss with Duc if you want to change something */

    public List<Piece> allyPieces()
    {
        if(currentPlayer== GameConfiguration.PLAYER1)
        {
            return copyPieces(player1Pieces);
        }
        else {
            return copyPieces(player2Pieces);
        }
    }

    public List<Vector2D> allyPositions() {
        return getPlayerPositions(currentPlayer);
    }

    public List<Vector2D> enemyPositions() {
        if (currentPlayer == GameConfiguration.PLAYER1)
            return getPlayerPositions(GameConfiguration.PLAYER2);
        else
            return getPlayerPositions(GameConfiguration.PLAYER1);
    }

    public Vector2D allyKing() {
        Vector2D king = null;
        if (currentPlayer == GameConfiguration.PLAYER1)
            king = getKing(GameConfiguration.PLAYER1);
        else
            king = getKing(GameConfiguration.PLAYER2);
        
        if (king == null) {
            king = new Vector2D(-100,-100);
        }

        return king;
    }

    public Vector2D enemyKing() {
        Vector2D king = null;
        if (currentPlayer == GameConfiguration.PLAYER1)
            king = getKing(GameConfiguration.PLAYER2);
        else
            king = getKing(GameConfiguration.PLAYER1);

        if (king == null) {
            king = new Vector2D(100,100);
        }
        return king;
    }

    public Vector2D allyGoal() {
        if (currentPlayer == PLAYER1)
            return GameConfiguration.RED_THRONE;
        else
            return GameConfiguration.BLUE_THRONE;
    }

    public Vector2D enemyGoal() {
        if (currentPlayer == PLAYER2)
            return GameConfiguration.RED_THRONE;
        else
            return GameConfiguration.BLUE_THRONE;
    }

    public List<CardInfo> availableCards() {
        List<CardInfo> cards = new ArrayList<>();
        PlayerHand hand;
        if (currentPlayer == 0)
            hand = player1Hand;
        else
            hand = player2Hand;
        cards.add(hand.getFirstCard());
        cards.add(hand.getSecondCard());
        return cards;
    }

    public List<Vector2D> possiblePositions(Vector2D piece, CardInfo card) {
        List<Vector2D> allies = allyPositions();
        List<Vector2D> curMovement;
        if (currentPlayer == GameConfiguration.PLAYER1)
            curMovement = card.getRedMovement();
        else
            curMovement = card.getBlueMovement();

        List<Vector2D> newPossiblePos = new ArrayList<>();
        
        for (Vector2D movement : curMovement) {
            Vector2D possPosition = new Vector2D(
                piece.getIntX() + movement.getIntY(),
                piece.getIntY() + movement.getIntX()
            );
            if (possPosition.getIntX() < 5 && possPosition.getIntY() < 5 &&
                possPosition.getIntX() >= 0 && possPosition.getIntY() >= 0  &&
                !allies.contains(possPosition)   
            ) {
                newPossiblePos.add(possPosition);
            }
        }

        return newPossiblePos;
    }

    public GameConfiguration nextConfig(Turn turn) {
        GameConfiguration next = new GameConfiguration(player1Hand, player2Hand, standByCard, player1Pieces, player2Pieces, currentPlayer);
        
        next.gameCards = gameCards;
        
        next.setSelectedPiece(turn.getPiece().getPosition());
        next.setSelectedCard(turn.getCard().getName());
        next.setSelectedAction(turn.move);

        if (next.checkPresence(turn.getMove())) {
            for (Piece p : next.getPlayerPieces(next.getNextPlayer())) {
                if (p.getPosition().equals(next.getSelectedAction())) {
                    next.getPlayerPieces(next.getNextPlayer()).remove(p);
                    break;
                }
            }
        }

        for (Piece p : next.getPlayerPieces(next.currentPlayer)) {
            if (p.getPosition().equals(turn.getPiece().getPosition())) {
                p.setPosition(turn.getMove());
                break;
            }
        }

        next.exchangeCards();

        next.changePlayer();

        return next;
    }

    private List<Vector2D> getPlayerPositions(int player) {
        List<Vector2D> pos = new ArrayList<>();

        if (player == GameConfiguration.PLAYER1) {
            for (Piece p : player1Pieces) {
                pos.add(p.getPosition());
            }
        } else if (player == GameConfiguration.PLAYER2) {
            for (Piece p : player2Pieces) {
                pos.add(p.getPosition());
            }
        } else {
            Util.printError("Invalid player");
        }

        return pos;
    }

    private Vector2D getKing(int player) {
        ArrayList<Piece> pieces;
        if (player == GameConfiguration.PLAYER1) {
            pieces = player1Pieces;
        } else if (player == GameConfiguration.PLAYER2) {
            pieces = player2Pieces;
        } else {
            Util.printError("Invalid player");
            return null;
        }

        for (Piece p : pieces) {
            if (p.getType() == PieceType.BLUE_KING || p.getType() == PieceType.RED_KING)
                return p.getPosition();
        }

        return null;
    }

    private ArrayList<Piece> copyPieces(ArrayList<Piece> pieces) {
        ArrayList<Piece> newPieces = new ArrayList<>();

        for (Piece p : pieces) {
            newPieces.add(p.clone());
        }

        return newPieces;
    }
}
