package ENGINE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import JSON.*;

public class Engine {
    List<Card> listOfCards;
    List<Card> gameCards;
    JsonReader jReader;
    char[][] board;
    int rows;
    int cols;
    int firstNumber;
    int secondNumber;
    Random random;
    //int player;
    Player player;
    PlayerHand ph1;
    PlayerHand ph2;
    Scanner scanner;
    GameConfiguration gameConfig;
    List<Position> posPositions;
    Turn turn;
    private final static char BLUE_PAWN = 'b';
    private final static char RED_PAWN = 'r';
    private final static char BLUE_KING = 'B';
    private final static char RED_KING = 'R';
    private final static Position RED_THRONE = new Position(4, 2);
    private final static Position BLUE_THRONE = new Position(0, 2); 
    public Engine(int w, int h)
    {
        jReader = new JsonReader();
        listOfCards = jReader.readJson();
        board = new char[w][h];
        this.rows = w;
        this.cols = h;
        gameCards = new ArrayList<>();
        random = new Random();
        scanner = new Scanner(System.in);
        turn = null;
        player = new Player(0);
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
    
    private void initBoard() {
        for (int i = 0; i < rows; ++i)
        {
            for (int j = 0; j < cols; ++j) {
                if (i == 0) {
                    if (j == 2)
                        board[i][j] = BLUE_KING;
                    else
                        board[i][j] = BLUE_PAWN;
                }
                else if (i == 4) {
                    if (j == 2)
                        board[i][j] = RED_KING;
                    else
                        board[i][j] = RED_PAWN;
                }
                else
                    board[i][j] = '.';
            }
        }
        
        return;
    }

    private void chooseCards() {
        Set<Integer> set = new HashSet<>();
        int i = 0;
        while (i<5)
        {
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

    //Could potentially randomise this further but for now it's good enough (it's already random...)
    private void assignCards() {
        Card player1Card1 = gameCards.get(0);
        Card player1Card2 = gameCards.get(1);
        Card player2Card1 = gameCards.get(2);
        Card player2Card2 = gameCards.get(3);
        ph1 = new PlayerHand(0, player1Card1, player1Card2);
        ph2 = new PlayerHand(1, player2Card1, player2Card2);
    }


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

    public void playTurn(Position piece, Card playCard, Position move) {

        turn = player.getTurn(piece, playCard, move);
        gameConfig.updateConfig(turn);
        }

    public boolean gameOver()
    {
        return conqueredKing() || capturedKing();
    }

    public boolean conqueredKing()
    {
        int red_i = RED_THRONE.getI();
        int red_j = RED_THRONE.getJ();
        int blue_i = BLUE_THRONE.getI();
        int blue_j = BLUE_THRONE.getJ();
        if (board[red_i][red_j] == BLUE_KING) {
            System.out.println("Player 2 has won!");
            return true;
        }
        else if(board[blue_i][blue_j] == RED_KING)
        {
            System.out.println("Player 1 has won");
            return true;
        }
        return false;
    }

    public boolean capturedKing()
    {

        return ((!checkPresence(BLUE_KING) && checkPresence(RED_KING))
                || (checkPresence(BLUE_KING) && !checkPresence(RED_KING)));
    }

    public boolean checkPresence(char king)
    {
        for (int i = 0; i < rows;++i)
        {
            for (int j = 0; j < cols; ++j) {
                if (board[i][j] == king)
                    return true;
            }
        }
        return false;
    }

    public GameConfiguration getGameConfiguration()
    {
        return gameConfig;
    }

    public Player getPlayer()
    {
        return player;
    }
}
