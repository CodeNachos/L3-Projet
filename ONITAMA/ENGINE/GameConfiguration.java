package ENGINE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* 
 Game configuration = board + (player1,card1,card2) + (player2,card1,card2) + card_on_standby
*/
public class GameConfiguration implements Serializable {
    char[][] board;
    PlayerHand player1Hand;
    PlayerHand player2Hand;
    Card on_standby;
    int rows;
    int cols;
    boolean marked;
    int currentPlayer;

    public GameConfiguration(char[][] board, PlayerHand ph1, PlayerHand ph2, Card stb, int player) {
        this.board = board;
        this.player1Hand = ph1;
        this.player2Hand = ph2;
        this.on_standby = stb;
        this.rows = board.length;
        this.cols = board[0].length;
        marked = false;
        this.currentPlayer = player;
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
        List<Position> possiblePositions = getPossiblePositions(player, piece, card);
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
    
    public List<Position> getPossiblePositions(int player, Position piece, Card card)
    {
        List<Movement> curMovement;
        if (player == 0)
            curMovement = card.getRedMovement();
        else
            curMovement = card.getBlueMovement();

        List<Position> newPossiblePos = new ArrayList<>();
        for (Movement movement : curMovement) {
            int deltaRow = movement.getDeltaRow();
            int deltaCol = movement.getDeltaCol();
            Position possPosition = new Position(piece.getI() + deltaRow, piece.getJ() + deltaCol);
            newPossiblePos.add(possPosition);
        }
        return newPossiblePos;

    }


    public void applyMove(int player,Position piece, Position move)
    {
        int i = piece.getI();
        int j = piece.getJ();
        int new_i = move.getI();
        int new_j = move.getJ();
        if (player == 0) {
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
    public char[][] applyMove(char[][] cpy,int player,Position piece, Position move)
    {
        int i = piece.getI();
        int j = piece.getJ();
        int new_i = move.getI();
        int new_j = move.getJ();
        if (player == 0) {
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
        applyMove(turn.getPlayer(), turn.getPiece(), turn.getMove());
        exchangeCards(turn.getPlayer(), turn.getCard());
    }

    public GameConfiguration nextConfig(Turn turn)
    {
        char[][] cpy = copyBoard();
        PlayerHand ph1 = new PlayerHand(0);
        PlayerHand ph2 = new PlayerHand(1);
        Card new_stdby = new Card();
        cpy = applyMove(cpy, turn.getPlayer(), turn.getPiece(), turn.getMove());
        exchangeCards(ph1, ph2, turn.getPlayer(), turn.getCard(), new_stdby);
        return new GameConfiguration(cpy, ph1, ph2, new_stdby, (turn.getPlayer()+1)%2);

    }
    
    public void exchangeCards(int player, Card playerCard)
    {
        Card tmp = playerCard;
        if (player == 0) {
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
    public void exchangeCards(PlayerHand ph1, PlayerHand ph2,int player, Card playerCard, Card new_stdby)
    {
        if(player==0)
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

    public GameConfiguration copyConfig()
    {
        char [][] cpy = copyBoard();
        PlayerHand ph1 = new PlayerHand(0);
        PlayerHand ph2 = new PlayerHand(1);
        Card cpy_stdby = new Card();
        int cpy_player = currentPlayer;
        ph1.setFirstCard(player1Hand.getFirstCard());
        ph1.setSecondCard(player1Hand.getSecondCard());
        ph2.setFirstCard(player2Hand.getFirstCard());
        ph2.setSecondCard(player2Hand.getSecondCard());
        cpy_stdby.setString(on_standby.getName());
        cpy_stdby.setBlueMovement(on_standby.getBlueMovement());
        cpy_stdby.setRedMovement(on_standby.getRedMovement());
        return new GameConfiguration(cpy, ph1, ph2, cpy_stdby, cpy_player);
    }

}
