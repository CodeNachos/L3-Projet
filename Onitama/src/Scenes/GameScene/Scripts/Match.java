package Onitama.src.Scenes.GameScene.Scripts;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import Engine.Structures.Vector2D;
import Onitama.src.Scenes.GameScene.Scripts.Card.CardInfo;
import Onitama.src.Scenes.GameScene.Scripts.Card.PlayerHand;
import Onitama.src.lib.json.JsonReader;

public class Match {
    
    // JSON file reader
    public static JsonReader jReader;

    // Game Parameters
    public static final int CARD_COUNT = 16; 
    public static List<CardInfo> listOfCards;
    // Cards picked for the match -> [p1:c1, p1:c2, p2:c1, p2:c2, stand by]
    public static HashMap<String, CardInfo> gameCards;
    
    // Player ID's
    public static final int PLAYER1 = 0; // red
    public static final int PLAYER2 = 1; // blue

    // Player Hands
    public static PlayerHand player1Hand;
    public static PlayerHand player2Hand;
    // Stand By Card
    public static CardInfo standByCard;

    
    // Turn Info
    public static int currentPlayer = PLAYER1;
    public static CardInfo selectedCard = null; // current player hand selected card
    public static Vector2D selectedPiece = null; // current player selected piece
    public static Vector2D selectedAction = null; // current player selected action tile

    public static void initialise() {
        // Load all game cards
        Match.jReader = new JsonReader();
        Match.listOfCards = Match.jReader.readJson("Onitama/res/Cards/cards.json");
        
        // Pick game cards
        chooseCards();

        // Distribute cards
        assignCards();
    }

    private static void chooseCards() {
        Match.gameCards = new HashMap<>();
        Set<Integer> set = new HashSet<>();

        Random random = new Random();

        int i = 0;  
        while (i<5)
        {
            int cardIndex = random.nextInt(16);
            if (!set.contains(cardIndex)) {
                CardInfo card = Match.listOfCards.get(cardIndex);
                Match.gameCards.put(card.getName(), card);
                set.add(cardIndex);
                ++i;
            }
        }
        return;
    }

    private static void assignCards() {
        // iterate trough card game names
        Iterator<String> cardIter = Match.gameCards.keySet().iterator();
        
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

    public static void setSelectedCard(String card) {
        if (card.isEmpty()) {
            selectedCard = null;
        } else {
            selectedCard = gameCards.get(card);
        }

        selectedAction = null;
    }

    public static String getSelectedCard() {
        if (selectedCard == null) {
            return "";
        }

        return selectedCard.getName();
    }

    public static int getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setSelectedPiece(Vector2D piece) {
        if (piece == null)
            selectedPiece = null;
        else {
            selectedPiece = piece.clone();
        }

        selectedAction = null;
    }

    public static Vector2D getSelectedPiece() {
        if (selectedPiece == null) 
            return null;
        
        return selectedPiece.clone();
    }

    public static void setSelectedAction(Vector2D tile) {
        if (tile == null)
            selectedAction = null;
        else {
            selectedAction = tile.clone();
        }
    }

    public static Vector2D getSelectedAction() {
        if (selectedAction == null) 
            return null;
        
        return selectedAction.clone();
    }

    public static int getNextPlayer() {
        return (currentPlayer + 1) % 2;
    }

    public static void changePlayer() {
        currentPlayer = (currentPlayer + 1) % 2;
    }

    public static boolean isPieceSelected() {
        return selectedPiece != null;
    }

    public static boolean isCardSelected() {
        return selectedCard != null;
    }

    public static boolean isActionSelected() {
        return selectedAction != null;
    }
}
