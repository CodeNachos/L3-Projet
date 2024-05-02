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
    int player;
    PlayerHand ph1;
    PlayerHand ph2;
    Scanner scanner;
    GameConfiguration gameConfig;
    List<Position> posPositions;
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
        player = 0;
        initialiseGame();
    }

    private void initialiseGame() {
        initBoard();
        chooseCards();
        assignCards();
        Card standby = gameCards.get(4);
        //Initial game config
        gameConfig = new GameConfiguration(board, ph1, ph2, standby);
        /* 
        gameConfig.displayConfig();
        playTurn();
        gameConfig.displayConfig();
        changePlayer();
        playTurn();
        gameConfig.displayConfig();
        */
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

    public void playTurn() {
        String ans = "";
        Position piece = null;
        Card playCard = null;
        Position move = null;
        while (!ans.equals("confirm") && !ans.equals("Confirm")) {
            piece = askPlayerPiece();
            playCard = askPlayerCard();
            System.out.println("You want to play piece (" + piece.getI() + "," + piece.getJ() + ") with card "
                    + playCard.getName());
            System.out.println("These are the possibilities:");
            gameConfig.displayMark(player, piece, playCard);
            boolean mark = gameConfig.getMarked();
            if (!mark)
            {
                System.out.println("As you can see, no marks are found. Therefore, you must change cards!");
                continue;
            }
            posPositions = gameConfig.getPossiblePositions(player, piece, playCard);
            move = askMove();
            System.out.println("You will play piece (" + piece.getI() + "," + piece.getJ() + ") with card "
                    + playCard.getName() + " at spot (" + move.getI() + "," + move.getJ() + ")");
            gameConfig.setMarked(false);
            System.out.println("Confirm move?: (type Confirm)");
            ans = scanner.nextLine();
        }
        //gameConfig.applyMove(player,piece, move);
        gameConfig.updateConfig(player, playCard, piece, move);
    }

    public void changePlayer() {
        player = (player + 1) % 2;
        return;
    }

    public Position askPlayerPiece()
    {
        if (player == 0) {
            System.out.println("Choose a red pawn in the table (enter i,j)");
        }
        else {
            System.out.println("Choose a blue pawn in the table (enter i,j)");
        }
            String input = scanner.nextLine();
            parse(input);
            while (firstNumber == -1 || !validPiece(firstNumber, secondNumber)) {
                System.out.println("Invalid piece! please try again!");
                input = scanner.nextLine();
                parse(input);
            }
            Position piece = new Position(firstNumber, secondNumber);
            return piece;
    }
    
    public void parse(String input)
    {
        String[] parts = input.split("\\s+");
        if (parts.length != 2) {
            System.out.println("Incorrect input!");
            this.firstNumber = -1;
            this.secondNumber = -1;
            return;
        }

        try {
            firstNumber = Integer.parseInt(parts[0]);
            secondNumber = Integer.parseInt(parts[1]);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid integers.");
        }
        return;
    }
    
    public boolean validPiece(int i, int j)
    {
        char c = board[i][j];
        if (player == 0 && (c == RED_PAWN || c == RED_KING))
            return true;
        else if (player == 1 && (c == BLUE_PAWN || c == BLUE_KING))
            return true;
        return false;
    }
    
    public Card askPlayerCard()
    {
        PlayerHand ph;
        if (player == 0)
            ph = gameConfig.getPlayer1Hand();
        else
            ph = gameConfig.getPlayer2Hand();
        System.out.println("Which card would you like to play? (intput 1 or 2)");
        int cardNumb = Integer.valueOf(scanner.nextLine());
        while (cardNumb != 1 && cardNumb != 2) {
            System.out.println("Please insert 1 or 2!");
            cardNumb = Integer.valueOf(scanner.nextLine());
        }
        if (cardNumb == 1)
            return ph.getFirstCard();
        else
            return ph.getSecondCard();

    }
    
    public Position askMove()
    {
        System.out.println("Choose a marked spot: (i,j)");
        String move = scanner.nextLine();
        parse(move);
        while (firstNumber == -1 || !validMove(firstNumber, secondNumber)) {
            System.out.println("Invalid move! please try again!");
            move = scanner.nextLine();
            parse(move);
        }
        return new Position(firstNumber, secondNumber);

    }
    
    public boolean validMove(int firstNumber, int secondNumber)
    {
        Position position = new Position(firstNumber, secondNumber);
        if (posPositions.contains(position))
            return true;
        else
            return false;

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
}
