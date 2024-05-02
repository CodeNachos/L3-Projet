package INTERFACE;

import java.util.List;
import java.util.Scanner;

import ENGINE.*;
import GLOBAL.*;

public class InterfaceTextuelle {
    
    Configurations config;
    Engine eng;
    int firstNumber;
    int secondNumber;
    Scanner scanner;
    List<Position> posPositions;
    Position piece;
    Card playCard;
    Position move;
    private final static char BLUE_PAWN = 'b';
    private final static char RED_PAWN = 'r';
    private final static char BLUE_KING = 'B';
    private final static char RED_KING = 'R';

    public InterfaceTextuelle(Configurations conf, Engine eng)
    {
        this.config = conf;
        this.eng = eng;
        scanner = new Scanner(System.in);
    }

    public void display()
    {
        String mode = config.getMode();
        switch (mode) {
            case "Normal":
                askPlayer();
                break;
            case "AI":
                if(eng.getPlayer().getCurrentPlayer()==0)
                {
                    askPlayer();
                }
                else {
                    //To be implemented
                }
            case "Automatic":
                //To be implemented
            default:
                break;
        }
    }

    public void askPlayer()
    {
        String ans = "";
        while (!ans.equals("confirm") && !ans.equals("Confirm")) {
            eng.getGameConfiguration().displayConfig();
            piece = askPlayerPiece();
            playCard = askPlayerCard();
            System.out.println("You want to play piece (" + piece.getI() + "," + piece.getJ() + ") with card "
                    + playCard.getName());
            System.out.println("These are the possibilities:");
            eng.getGameConfiguration().displayMark(eng.getPlayer().getCurrentPlayer(), piece, playCard);
            boolean mark = eng.getGameConfiguration().getMarked();
            if (!mark) {
                System.out.println("As you can see, no marks are found. Therefore, you must change cards!");
                continue;
            }
            posPositions = eng.getGameConfiguration().getPossiblePositions(eng.getPlayer().getCurrentPlayer(), piece,
                    playCard);
            move = askMove();
            System.out.println("You will play piece (" + piece.getI() + "," + piece.getJ() + ") with card "
                    + playCard.getName() + " at spot (" + move.getI() + "," + move.getJ() + ")");
            eng.getGameConfiguration().setMarked(false);
            System.out.println("Confirm move?: (type Confirm)");
            ans = scanner.nextLine();
        }
        return;
    }
    public Position askPlayerPiece()
    {
        if (eng.getPlayer().getCurrentPlayer() == 0) {
            System.out.println("Choose a red pawn in the table (enter i,j)");
        } else {
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
        char[][] board = eng.getGameConfiguration().getBoard();
        char c = board[i][j];
        if (eng.getPlayer().getCurrentPlayer() == 0 && (c == RED_PAWN || c == RED_KING))
            return true;
        else if (eng.getPlayer().getCurrentPlayer() == 1 && (c == BLUE_PAWN || c == BLUE_KING))
            return true;
        return false;
    }
    
    public Card askPlayerCard()
    {
        PlayerHand ph;
        if (eng.getPlayer().getCurrentPlayer() == 0)
            ph = eng.getGameConfiguration().getPlayer1Hand();
        else
            ph = eng.getGameConfiguration().getPlayer2Hand();
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

    public Position getPiece()
    {
        return piece;
    }

    public Card getCard()
    {
        return playCard;
    }

    public Position getMove()
    {
        return move;
    }
}
