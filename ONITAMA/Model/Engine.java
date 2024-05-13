package Model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import JSON.*;

public class Engine {
    List<Card> listOfCards;
    List<Card> gameCards;
    History hist;
    JsonReader jReader;
    Board board;
    Random random;
    PlayerHand ph1;
    PlayerHand ph2;
    GameConfiguration gameConfig;
    Turn turn;

    public Engine(int w, int h) {
        jReader = new JsonReader();
        listOfCards = jReader.readJson();
        board = new Board();
        //past = new LinkedList<>();
        //futur = new LinkedList<>();
        gameCards = new ArrayList<>();
        random = new Random(423040);
        hist = new History(this);
        turn = null;
        initialiseGame();
    }

    private void initialiseGame() {
        initBoard();
        chooseCards();
        assignCards();
        Card standby = gameCards.get(4);
        //Initial game config
        gameConfig = new GameConfiguration(board, ph1, ph2, standby);
    }

    /*
     * Method: InitBoard
     * Specs: Initializes the game board (Shouldn't be called elsewhere...)
     * Returns: void
     */
    private void initBoard() {
        board.initialiseBoard();
        return;
    }

    /*
     * Method chooseCards
     * Specs: Given a list of cards (16), this method chooses 5 cards (randomly) among the 16
     * Return: void
     */
    private void chooseCards() {
        Set<Integer> set = new HashSet<>();
        int i = 0;
        while (i < 5) {
            int cardIndex = random.nextInt(16);
            if (!set.contains(cardIndex)) {
                Card card = listOfCards.get(cardIndex);
                gameCards.add(card);
                set.add(cardIndex);
                ++i;
            }
        }
        return;
    }

    /*
     * Method: assignCards
     * Specs: Given a list of game cards (5), assign 2 cards to player 1, 2 cards to player 2.
     * Return: void
     */
    private void assignCards() {
        Card player1Card1 = gameCards.get(0);
        Card player1Card2 = gameCards.get(1);
        Card player2Card1 = gameCards.get(2);
        Card player2Card2 = gameCards.get(3);
        ph1 = new PlayerHand(0, player1Card1, player1Card2);
        ph2 = new PlayerHand(1, player2Card1, player2Card2);
    }

    /*
     * Method: printCards
     * Specs: Prints all the cards and how they move their respective pawns
     * Return: void
     */
    public void printCards() {
        for (Card card : listOfCards) {
            System.out.println("Card name is: " + card.getName());
            System.out.println("For blue's movements:");
            for (Movement movement : card.getBlueMovement()) {
                System.out.println("(" + movement.getDeltaRow() + "," + movement.getDeltaCol() + ")");
            }
            System.out.println("For red's movements:");
            for (Movement movement : card.getRedMovement()) {
                System.out.println("(" + movement.getDeltaRow() + "," + movement.getDeltaCol() + ")");
            }
            System.out.println();
        }
    }

    /*
     * Method: PlayTurn
     * Specs: Given a piece, a card that will be played and the new position (move), moves the piece to its
     * new position according to the card played. (Simulates a turn)
     * Args: Position piece, Card playCard, Position move
     * Return: void
     */
    public void playTurn(Piece piece, Card playCard, Piece move) {
        hist.getPast().addFirst(gameConfig.copyConfig());
        hist.getFutur().clear();
        turn = new Turn(playCard, piece, move);
        gameConfig.updateConfig(turn);
    }

    /*
     * Method: changePlayer
     * Specs: Changes the player for a new turn
     * Return: void
     */
    public void changePlayer() {
        gameConfig.changePlayer();
    }
    /*
     * Method: undo
     * Specs: Undos a turn in the game (if possible)
     * Return: void
     */
    public void undo() {
        hist.undo();
        return;

    }

    /*
     * Method: redo
     * Specs: Redoes a turn in the game (if possible)
     * Return: void
     */
    public void redo() {
        hist.redo();
    }

    /*
     * Method: save
     * Specs: saves the game in a specified file
     * Args: String file
     * Return: void
     */
    public void save(String file) throws Exception {

        try (FileOutputStream fileOut = new FileOutputStream(file);
                GZIPOutputStream gzipOut = new GZIPOutputStream(new BufferedOutputStream(fileOut));
                ObjectOutputStream out = new ObjectOutputStream(gzipOut)) {
            out.writeObject(gameConfig);
            out.writeObject(hist.getPast());
            out.writeObject(hist.getFutur());
        }
    }

    /*
     * Method: load
     * Specs: Loads a game from a specified file
     * Args: String file
     * Return: void
     */
    public void load(String file) throws Exception {
        try (FileInputStream fileIn = new FileInputStream(file);
                GZIPInputStream gzipIn = new GZIPInputStream(new BufferedInputStream(fileIn));
                ObjectInputStream in = new ObjectInputStream(gzipIn)) {
            GameConfiguration gc_cpy = (GameConfiguration) in.readObject();
            @SuppressWarnings("unchecked")
            LinkedList<GameConfiguration> undoStack = (LinkedList<GameConfiguration>) in.readObject();
            @SuppressWarnings("unchecked")
            LinkedList<GameConfiguration> redoStack = (LinkedList<GameConfiguration>) in.readObject();
            setConfig(gc_cpy);
            hist.setPast(undoStack);
            hist.setFutur(redoStack);
            return;
        }
    }

    public boolean isGameOver()
    {
        return gameConfig.gameOver();
    }

    /*Returns current player of the game */
    public int getCurrentPlayer()
    {
        return gameConfig.getCurrentPlayer();
    }

    /*Getter for GameConfiguration */
    public GameConfiguration getGameConfiguration() {
        return gameConfig;
    }

    /*Setter for GameConfiguration */
    public void setConfig(GameConfiguration gc) {
        this.gameConfig = gc;
    }
}
