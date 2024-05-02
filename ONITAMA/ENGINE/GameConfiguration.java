package ENGINE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* 
 Game configuration = board + (player1,card1,card2) + (player2,card1,card2) + card_on_standby
*/
public class GameConfiguration {
    char[][] board;
    PlayerHand player1Hand;
    PlayerHand player2Hand;
    Card on_standby;
    int rows;
    int cols;
    boolean marked;

    public GameConfiguration(char[][] board, PlayerHand ph1, PlayerHand ph2, Card stb) {
        this.board = board;
        this.player1Hand = ph1;
        this.player2Hand = ph2;
        this.on_standby = stb;
        this.rows = board.length;
        this.cols = board[0].length;
        marked = false;
    }

    public void displayConfig()
    {
        displayBoard();
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

    public void displayMark(int player,Position piece, Card card)
    {
        char[][] cpy = new char[board.length][];
        for (int i = 0; i < board.length; i++) {
            cpy[i] = Arrays.copyOf(board[i], board[i].length);
        }
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
    
    public void updateConfig(Turn turn) {
        applyMove(turn.getPlayer(), turn.getPiece(), turn.getMove());
        exchangeCards(turn.getPlayer(), turn.getCard());
    }
    
    public void exchangeCards(int player, Card playerCard)
    {
        Card tmp = playerCard;
        if(player==0)
        {
            if(player1Hand.getFirstCard().getName().equals(playerCard.getName()))
            {
                player1Hand.setFirstCard(on_standby);
            }
            else {
                player1Hand.setSecondCard(on_standby);
            }
            on_standby = tmp;
        }
        else {
            if (player2Hand.getFirstCard().getName().equals(playerCard.getName())) {
                player2Hand.setFirstCard(on_standby);
            }
            else {
                player2Hand.setSecondCard(on_standby);
            }
            on_standby = tmp;
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

}
