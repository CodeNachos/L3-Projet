package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* 
 Game configuration = board + (player1,card1,card2) + (player2,card1,card2) + card_on_standby
*/
public class GameConfiguration implements Serializable {
    static final int RED = 0;
    static final int BLUE = 1;
    
    char[][] board;
    PlayerHand player1Hand;
    PlayerHand player2Hand;
    List<Position> listOfRedPawns;
    List<Position> listOfBluePawns;
    Position RED_KINGPOS;
    Position BLUE_KINGPOS;
    Card on_standby;
    int rows;
    int cols;
    boolean marked;
    int currentPlayer;

    public GameConfiguration(char[][] board, PlayerHand ph1, PlayerHand ph2, Card stb, int player, List<Position>lBlue, List<Position>lRed, Position REDPOS, Position BLUEPOS) {
        this.board = board;
        this.player1Hand = ph1;
        this.player2Hand = ph2;
        this.on_standby = stb;
        this.rows = board.length;
        this.cols = board[0].length;
        marked = false;
        this.currentPlayer = player;
        listOfBluePawns = lBlue;
        listOfRedPawns = lRed;
        this.RED_KINGPOS = REDPOS;
        this.BLUE_KINGPOS = BLUEPOS;
    }

    public void displayConfig()
    {
        displayBoard();
        System.out.println("Player that is currently playing: " + currentPlayer);
        System.out.println("Red's cards:" + player1Hand.getFirstCard().getName() + " and "
                + player1Hand.getSecondCard().getName());
        System.out.println("Blue's cards:" + player2Hand.getFirstCard().getName() + " and "
                + player2Hand.getSecondCard().getName());
        System.out.println("On standby: " + on_standby.getName());
    }

    public void displayBoard()
    {
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (j == cols - 1) {
                    System.out.println(board[i][j]);
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
        }
    }

    public void displayBoard(char[][] copy)
    {
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (j == cols - 1) {
                    System.out.println(copy[i][j]);
                } else {
                    System.out.print(copy[i][j] + " ");
                }
            }
        }
    }

    public char[][] getBoard()
    {
        return board;
    }
    
    public PlayerHand getPlayer1Hand()
    {
        return player1Hand;
    }

    public PlayerHand getPlayer2Hand()
    {
        return player2Hand;
    }

    public Card getOnStandby()
    {
        return on_standby;
    }

    public char[][] copyBoard()
    {
        char[][] cpy = new char[board.length][];
        for (int i = 0; i < board.length; i++) {
            cpy[i] = Arrays.copyOf(board[i], board[i].length);
        }
        return cpy;
    }

    public void displayMark(int player,Position piece, Card card)
    {
        char[][] cpy = copyBoard();
        List<Position> possiblePositions = possiblePositions(piece, card);
        for(int i = 0; i<rows;++i)
        {
            for (int j = 0; j < cols; ++j) {
                Position possible = new Position(i, j);
                if (possiblePositions.contains(possible)) {
                    if(player==0 && (cpy[i][j]!='r' && cpy[i][j]!='R'))
                    {
                        cpy[i][j] = 'M';
                        marked = true;
                    }
                    else if(player==1 && (cpy[i][j]!='b' && cpy[i][j]!='B'))
                    {
                        cpy[i][j] = 'M';
                        marked = true;
                    }
                }
            }
        }
        displayBoard(cpy);

    }

    public void applyMove(Position piece, Position move)
    {
        int i = piece.getI();
        int j = piece.getJ();
        int new_i = move.getI();
        int new_j = move.getJ();
        if (currentPlayer == 0) {
            if (board[i][j] == 'r') {
                board[i][j] = '.';
                board[new_i][new_j] = 'r';

            } else {
                board[i][j] = '.';
                board[new_i][new_j] = 'R';
            }
        } else {
            if (board[i][j] == 'b') {
                board[i][j] = '.';
                board[new_i][new_j] = 'b';

            } else {
                board[i][j] = '.';
                board[new_i][new_j] = 'B';
            }
        }
    }
    //Method overload
    public char[][] applyMove(char[][] cpy,Position piece, Position move)
    {
        int i = piece.getI();
        int j = piece.getJ();
        int new_i = move.getI();
        int new_j = move.getJ();
        if (currentPlayer == 0) {
            if (cpy[i][j] == 'r') {
                cpy[i][j] = '.';
                cpy[new_i][new_j] = 'r';

            } else {
                cpy[i][j] = '.';
                cpy[new_i][new_j] = 'R';
            }
        } else {
            if (cpy[i][j] == 'b') {
                cpy[i][j] = '.';
                cpy[new_i][new_j] = 'b';

            } else {
                cpy[i][j] = '.';
                cpy[new_i][new_j] = 'B';
            }
        }
        return cpy;
    }
    
    public void updateConfig(Turn turn) {
        applyMove(turn.getPiece(), turn.getMove());
        exchangeCards(turn.getCard());
        updatePawnList(turn.getPiece(), turn.getMove());
        updateKings(turn.getPiece(), turn.getMove());
    }

    public void updatePawnList(Position piece, Position newPosition)
    {
        List<Position> l;
        if (currentPlayer == 0)
            l = listOfRedPawns;
        else
            l = listOfBluePawns;
        l.remove(piece);
        l.add(newPosition);
        return;
    }

    public void updateKings(Position piece, Position move)
    {
        if (piece.getI() == RED_KINGPOS.getI() && piece.getJ() == RED_KINGPOS.getJ()) {
            RED_KINGPOS.setI(move.getI());
            RED_KINGPOS.setJ(move.getJ());
        } else if (piece.getI() == BLUE_KINGPOS.getI() && piece.getJ() == RED_KINGPOS.getJ()) {
            BLUE_KINGPOS.setI(move.getI());
            BLUE_KINGPOS.setJ(move.getJ());
        }
    }
    
    //Method overload
    public void updateKings(Position new_RED, Position new_BLUE, Position piece, Position move)
    {
        if(piece.getI() == RED_KINGPOS.getI() && piece.getJ() == RED_KINGPOS.getJ())
        {
            new_RED.setI(move.getI());
            new_RED.setJ(move.getJ());
        }
        else if(piece.getI()==BLUE_KINGPOS.getI() && piece.getJ() == RED_KINGPOS.getJ())
        {
            new_BLUE.setI(move.getI());
            new_BLUE.setJ(move.getJ());
        }
    }

    //Method Overload
    public void updatePawnList(List<Position>l_blue,List<Position> l_red,Position piece, Position newPosition)
    {
        List<Position> l;
        if (currentPlayer == 0)
            l = l_red;
        else
            l = l_blue;
        l.remove(piece);
        l.add(newPosition);
        return;
    }

    public void copyList(List<Position> l, List<Position>l_cpy)
    {
        for(Position pos: l)
        {
            l_cpy.add(pos);
        }
    }
    
    public void exchangeCards(Card playerCard)
    {
        Card tmp = playerCard;
        if (currentPlayer == 0) {
            if (player1Hand.getFirstCard().getName().equals(playerCard.getName())) {
                player1Hand.setFirstCard(on_standby);
            } else {
                player1Hand.setSecondCard(on_standby);
            }
            on_standby = tmp;
        } else {
            if (player2Hand.getFirstCard().getName().equals(playerCard.getName())) {
                player2Hand.setFirstCard(on_standby);
            } else {
                player2Hand.setSecondCard(on_standby);
            }
            on_standby = tmp;
        }
    }
    
    //Method Overload
    public void exchangeCards(PlayerHand ph1, PlayerHand ph2, Card playerCard, Card new_stdby)
    {
        if(currentPlayer==0)
        {
            if(player1Hand.getFirstCard().getName().equals(playerCard.getName()))
            {
                //player1Hand.setFirstCard(on_standby);
                ph1.setFirstCard(on_standby);
                ph1.setSecondCard(player1Hand.getSecondCard());
                new_stdby.setString(player1Hand.getFirstCard().getName());
                new_stdby.setBlueMovement(player1Hand.getFirstCard().getBlueMovement());
                new_stdby.setRedMovement(player1Hand.getFirstCard().getRedMovement());
            }
            else {
                //player1Hand.setSecondCard(on_standby);
                ph1.setSecondCard(on_standby);
                ph1.setFirstCard(player1Hand.getFirstCard());
                new_stdby.setString(player1Hand.getSecondCard().getName());
                new_stdby.setBlueMovement(player1Hand.getSecondCard().getBlueMovement());
                new_stdby.setRedMovement(player1Hand.getSecondCard().getRedMovement());

            }
            ph2.setFirstCard(player2Hand.getFirstCard());
            ph2.setSecondCard(player2Hand.getSecondCard());
        }
        else {
            if (player2Hand.getFirstCard().getName().equals(playerCard.getName())) {
                //player2Hand.setFirstCard(on_standby);
                ph2.setFirstCard(on_standby);
                ph2.setSecondCard(player2Hand.getSecondCard());
                new_stdby.setString(player2Hand.getFirstCard().getName());
                new_stdby.setBlueMovement(player2Hand.getFirstCard().getBlueMovement());
                new_stdby.setRedMovement(player2Hand.getFirstCard().getRedMovement());
            }
            else {
                //player2Hand.setSecondCard(on_standby);
                ph2.setSecondCard(on_standby);
                ph2.setFirstCard(player2Hand.getFirstCard());
                new_stdby.setString(player2Hand.getSecondCard().getName());
                new_stdby.setBlueMovement(player2Hand.getSecondCard().getBlueMovement());
                new_stdby.setRedMovement(player2Hand.getSecondCard().getRedMovement());
            }
            ph1.setFirstCard(player1Hand.getFirstCard());
            ph1.setSecondCard(player1Hand.getSecondCard());
        }
    }

    public boolean getMarked()
    {
        return marked;
    }

    public void setMarked(boolean m)
    {
        marked = m;
    }

    public void setCurrentPlayer(int player)
    {
        this.currentPlayer = player;
    }

    public int getCurrentPlayer()
    {
        return currentPlayer;
    }

    public int nextPlayer()
    {
        return (currentPlayer + 1) % 2;
    }

    public GameConfiguration copyConfig()
    {
        char [][] cpy = copyBoard();
        PlayerHand ph1 = new PlayerHand(0);
        PlayerHand ph2 = new PlayerHand(1);
        Card cpy_stdby = new Card();
        List<Position> cpy_red = new ArrayList<>();
        List<Position> cpy_blue = new ArrayList<>();
        int cpy_player = currentPlayer;
        ph1.setFirstCard(player1Hand.getFirstCard());
        ph1.setSecondCard(player1Hand.getSecondCard());
        ph2.setFirstCard(player2Hand.getFirstCard());
        ph2.setSecondCard(player2Hand.getSecondCard());
        cpy_stdby.setString(on_standby.getName());
        cpy_stdby.setBlueMovement(on_standby.getBlueMovement());
        cpy_stdby.setRedMovement(on_standby.getRedMovement());
        copyList(listOfRedPawns, cpy_red);
        copyList(listOfBluePawns, cpy_blue);
        Position cpy_REDKING = new Position(RED_KINGPOS.getI(), RED_KINGPOS.getJ());
        Position cpy_BLUEKING = new Position(BLUE_KINGPOS.getI(), BLUE_KINGPOS.getJ());
        return new GameConfiguration(cpy, ph1, ph2, cpy_stdby, cpy_player,cpy_blue,cpy_red, cpy_REDKING, cpy_BLUEKING);
    }

    /* Start of AI's interface */
    /* Please discuss with Duc if you want to change something */

    public List<Position> allyPawns()
    {
        if (currentPlayer == RED)
            return listOfRedPawns;
        else
            return listOfBluePawns;
    }

    public List<Position> enemyPawns()
    {
        if (currentPlayer == BLUE)
            return listOfRedPawns;
        else
            return listOfBluePawns;
    }

    public Position allyKing()
    {
        if (currentPlayer == RED)
            return RED_KINGPOS;
        else
            return BLUE_KINGPOS;
    }

    public Position enemyKing()
    {
        if (currentPlayer == BLUE)
            return RED_KINGPOS;
        else
            return BLUE_KINGPOS;
    }

    public List<Card> availableCards()
    {
        List<Card> cards = new ArrayList<>();
        PlayerHand hand;
        if (currentPlayer == 0)
            hand = player1Hand;
        else
            hand = player2Hand;
        cards.add(hand.card1);
        cards.add(hand.card2);
        return cards;
    }

    public List<Position> possiblePositions(Position piece, Card card)
    {
        List<Position> allies = allyPawns();
        List<Movement> curMovement;
        if (currentPlayer == 0)
            curMovement = card.getRedMovement();
        else
            curMovement = card.getBlueMovement();

        List<Position> newPossiblePos = new ArrayList<>();
        for (Movement movement : curMovement) {
            int deltaRow = movement.getDeltaRow();
            int deltaCol = movement.getDeltaCol();
            Position possPosition = new Position(piece.getI() + deltaRow, piece.getJ() + deltaCol);
            if (0 <= possPosition.i && possPosition.i < 5 &&
                0 <= possPosition.j && possPosition.j < 5 &&
                !allies.contains(possPosition))
                newPossiblePos.add(possPosition);
        }
        return newPossiblePos;
    }

    public GameConfiguration nextConfig(Turn turn)
    {
        char[][] cpy = copyBoard();
        PlayerHand ph1 = new PlayerHand(0);
        PlayerHand ph2 = new PlayerHand(1);
        Card new_stdby = new Card();
        List<Position> new_redPositions = new ArrayList<>();
        List<Position> new_bluePositions = new ArrayList<>();
        Position new_REDKINGPOS = new Position(RED_KINGPOS.getI(),RED_KINGPOS.getJ());
        Position new_BLUEKINGPOS = new Position(BLUE_KINGPOS.getI(),BLUE_KINGPOS.getJ());
        copyList(listOfRedPawns, new_redPositions);
        copyList(listOfBluePawns, new_bluePositions);
        cpy = applyMove(cpy,turn.getPiece(), turn.getMove());
        exchangeCards(ph1, ph2,turn.getCard(), new_stdby);
        copyList(listOfRedPawns, new_redPositions);
        copyList(listOfBluePawns, new_bluePositions);
        updatePawnList(new_bluePositions, new_redPositions, turn.getPiece(), turn.getMove());
        updateKings(new_REDKINGPOS, new_BLUEKINGPOS,turn.getPiece(), turn.getMove());
        return new GameConfiguration(cpy, ph1, ph2, new_stdby, nextPlayer(),new_bluePositions,new_redPositions,new_REDKINGPOS,new_BLUEKINGPOS);
    }

    /* End of AI's interface */
}
