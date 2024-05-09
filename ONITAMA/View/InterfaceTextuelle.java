package View;

import java.util.List;
import java.util.Scanner;

import Controller.RandomAI;
import Model.*;
import GLOBAL.*;

public class InterfaceTextuelle {
    
    Configurations config;
    Engine eng;
    int firstNumber;
    int secondNumber;
    Scanner scanner;
    List<Position> posPositions;
    Piece piece;
    Card playCard;
    Piece move;
    RandomAI rAi;
    Type t;
    /* 
    private final static char BLUE_PAWN = 'b';
    private final static char RED_PAWN = 'r';
    private final static char BLUE_KING = 'B';
    private final static char RED_KING = 'R';
    */

    public InterfaceTextuelle(Configurations conf, Engine eng, RandomAI rAi)
    {
        this.config = conf;
        this.eng = eng;
        scanner = new Scanner(System.in);
        this.rAi = rAi;
    }

    public void display()
    {
        String mode = config.getMode();
        switch (mode) {
            case "Normal":
                askPlayer();
                break;
            case "AI":
                if(eng.getCurrentPlayer()==0) // if player == 0 -> human
                {
                    askPlayer();
                }
                else {
                    Turn turn = rAi.play();
                    piece = turn.getPiece();
                    playCard = turn.getCard();
                    move = turn.getMove();
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
            System.out.println("You want to play piece (" + piece.getPosition().getI() + "," + piece.getPosition().getJ() + ") with card "
                    + playCard.getName());
            System.out.println("These are the possibilities:");
            eng.getGameConfiguration().displayMark(piece, playCard);
            boolean mark = eng.getGameConfiguration().getMarked();
            if (!mark) {
                System.out.println("As you can see, no marks are found. Therefore, you must change cards!");
                continue;
            }
            posPositions = eng.getGameConfiguration().possiblePositions(piece.getPosition(),
                    playCard);
            move = askMove();
            System.out.println("You will play piece (" + piece.getPosition().getI() + "," + piece.getPosition().getJ() + ") with card "
                    + playCard.getName() + " at spot (" + move.getPosition().getI() + "," + move.getPosition().getJ() + ")");
            eng.getGameConfiguration().setMarked(false);
            System.out.println("Confirm move?: (type Confirm)");
            ans = scanner.nextLine();
        }
        return;
    }
    public Piece askPlayerPiece()
    {
        if (eng.getGameConfiguration().getCurrentPlayer() == 0) {
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
        Position pos = new Position(firstNumber, secondNumber);
        Piece piece = new Piece(t, pos);
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
        Board board = eng.getGameConfiguration().getBoard();
        Position pos = new Position(i, j);
        t = board.giveType(pos);
        if (eng.getGameConfiguration().getCurrentPlayer() == 0 && (t == Type.RED_PAWN || t == Type.RED_KING))
            return true;
        else if (eng.getGameConfiguration().getCurrentPlayer()== 1 && (t == Type.BLUE_PAWN || t == Type.BLUE_KING))
            return true;
        return false;
    }
    
    public Card askPlayerCard()
    {
        PlayerHand ph;
        if (eng.getGameConfiguration().getCurrentPlayer() == 0)
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
    
    public Piece askMove()
    {
        System.out.println("Choose a marked spot: (i,j)");
        String move = scanner.nextLine();
        parse(move);
        while (firstNumber == -1 || !validMove(firstNumber, secondNumber)) {
            System.out.println("Invalid move! please try again!");
            move = scanner.nextLine();
            parse(move);
        }

        return new Piece(t,new Position(firstNumber, secondNumber));

    }
    
    public boolean validMove(int firstNumber, int secondNumber)
    {
        Position position = new Position(firstNumber, secondNumber);
        if (posPositions.contains(position))
            return true;
        else
            return false;

    }

    public Piece getPiece()
    {
        return piece;
    }

    public Card getCard()
    {
        return playCard;
    }

    public Piece getMove()
    {
        return move;
    }
}
