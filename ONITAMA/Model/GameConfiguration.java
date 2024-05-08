package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* 
 Game configuration = board + (player1,card1,card2) + (player2,card1,card2) + card_on_standby
*/
public class GameConfiguration implements Serializable {
    static final Position RED_THRONE = new Position(4, 2);
    static final Position BLUE_THRONE = new Position(0, 2);
    static final int RED = 0;
    static final int BLUE = 1;

    Board board;
    PlayerHand player1Hand;
    PlayerHand player2Hand;
    Card on_standby;
    boolean marked;
    int currentPlayer;
    private static final int ROWS = 5;
    private static final int COLS = 5;

    public GameConfiguration(Board board, PlayerHand ph1, PlayerHand ph2, Card stb) {
        this.board = board;
        this.player1Hand = ph1;
        this.player2Hand = ph2;
        this.on_standby = stb;
        marked = false;
        this.currentPlayer = 0;
    }

    public void displayConfig() {
        displayBoard();
        System.out.println("Player that is currently playing: " + currentPlayer);
        System.out.println("Red's cards:" + player1Hand.getFirstCard().getName() + " and "
                + player1Hand.getSecondCard().getName());
        System.out.println("Blue's cards:" + player2Hand.getFirstCard().getName() + " and "
                + player2Hand.getSecondCard().getName());
        System.out.println("On standby: " + on_standby.getName());
    }

    public void displayBoard() {
        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLS; ++j) {
                boolean pieceFound = false;
                for (Piece piece : board.getBoard()) {
                    Position piece_pos = piece.getPosition();
                    if (piece_pos.getI() == i && piece_pos.getJ() == j) {
                        pieceFound = true;
                        switch (piece.getType()) {
                            case RED_PAWN:
                                if (j == COLS - 1) {
                                    System.out.print("r");
                                } else {
                                    System.out.print("r ");
                                }
                                break;
                            case BLUE_PAWN:
                                if (j == COLS - 1) {
                                    System.out.print("b");
                                } else {
                                    System.out.print("b ");
                                }
                                break;
                            case RED_KING:
                                if (j == COLS - 1) {
                                    System.out.print("R");
                                } else {
                                    System.out.print("R ");
                                }
                                break;
                            case MARKED:
                                if (j == COLS - 1) {
                                    System.out.print("M");
                                } else {
                                    System.out.print("M ");
                                }
                                break;
                            default:
                                if (j == COLS - 1) {
                                    System.out.print("b");
                                } else {
                                    System.out.print("B ");
                                }
                        }
                        break;
                    }
                }
                if (pieceFound)
                    continue;
                System.out.print(". ");
            }
            System.out.println();
        }
    }

    public void displayBoard(Board copy) {
        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLS; ++j) {
                boolean pieceFound = false;
                for (Piece piece : copy.getBoard()) {
                    Position piece_pos = piece.getPosition();
                    if (piece_pos.getI() == i && piece_pos.getJ() == j) {
                        pieceFound = true;
                        switch (piece.getType()) {
                            case RED_PAWN:
                                if (j == COLS - 1) {
                                    System.out.print("r");
                                } else {
                                    System.out.print("r ");
                                }
                                break;
                            case BLUE_PAWN:
                                if (j == COLS - 1) {
                                    System.out.print("b");
                                } else {
                                    System.out.print("b ");
                                }
                                break;
                            case RED_KING:
                                if (j == COLS - 1) {
                                    System.out.print("R");
                                } else {
                                    System.out.print("R ");
                                }
                                break;
                            case MARKED:
                                if (j == COLS - 1) {
                                    System.out.print("M");
                                } else {
                                    System.out.print("M ");
                                }
                                break;
                            default:
                                if (j == COLS - 1) {
                                    System.out.print("b");
                                } else {
                                    System.out.print("B ");
                                }
                        }
                        break;
                    }
                }
                if (pieceFound)
                    continue;
                System.out.print(". ");
            }
            System.out.println();
        }
    }

    public Board getBoard() {
        return board;
    }

    public PlayerHand getPlayer1Hand() {
        return player1Hand;
    }

    public PlayerHand getPlayer2Hand() {
        return player2Hand;
    }

    public Card getOnStandby() {
        return on_standby;
    }

    public Board copyBoard() {
        Board cpy = new Board();
        for (Piece piece : board.getBoard()) {
            cpy.getBoard().add(piece);
        }
        return cpy;
    }

    public void displayMark(Piece piece, Card card) {
        Board cpy = copyBoard();
        List<Position> possiblePositions = possiblePositions(piece.getPosition(), card);
        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLS; ++j) {
                Position possible = new Position(i, j);
                if (possiblePositions.contains(possible)) {
                    if (currentPlayer == RED && !cpy.getRedPositions().contains(possible)) {
                        marked = true;
                        if (cpy.checkPosition(possible))
                            cpy.remove(possible);
                        
                        cpy.getBoard().add(new Piece(Type.MARKED, possible));
                    } else if (currentPlayer == BLUE && !cpy.getBluePositions().contains(possible)) {
                        marked = true;
                        if (cpy.checkPosition(possible))
                            cpy.remove(possible);
                        cpy.getBoard().add(new Piece(Type.MARKED, possible));
                    }
                    /* 
                    if (player == 0 && (cpy[i][j] != 'r' && cpy[i][j] != 'R')) {
                        cpy[i][j] = 'M';
                        marked = true;
                    } else if (player == 1 && (cpy[i][j] != 'b' && cpy[i][j] != 'B')) {
                        cpy[i][j] = 'M';
                        marked = true;
                    }
                    */
                }
            }
        }
        displayBoard(cpy);

    }

    public void applyMove(Piece old_piece, Piece new_piece) {
        board.updateBoard(old_piece, new_piece);
        /* 
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
        */
    }

    //Method overload
    public Board applyMove(Board cpy, Piece piece, Piece move) {
        board.updateBoard(cpy, piece, piece);
        /*
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
        */
        return cpy;
    }

    public void updateConfig(Turn turn) {
        applyMove(turn.getPiece(), turn.getMove());
        exchangeCards(turn.getCard());
    }

    /*
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
    */

    //Method overload
    /*
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
    */
    
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
    
    public void changePlayer()
    {
    currentPlayer = (currentPlayer + 1) % 2;
    }
    
    public GameConfiguration copyConfig()
    {
    Board cpy = copyBoard();
    PlayerHand ph1 = new PlayerHand(0);
    PlayerHand ph2 = new PlayerHand(1);
    Card cpy_stdby = new Card();
    ph1.setFirstCard(player1Hand.getFirstCard());
    ph1.setSecondCard(player1Hand.getSecondCard());
    ph2.setFirstCard(player2Hand.getFirstCard());
    ph2.setSecondCard(player2Hand.getSecondCard());
    cpy_stdby.setString(on_standby.getName());
    cpy_stdby.setBlueMovement(on_standby.getBlueMovement());
    cpy_stdby.setRedMovement(on_standby.getRedMovement());
    GameConfiguration gc_cpy = new GameConfiguration(cpy, ph1, ph2, cpy_stdby);
    gc_cpy.setCurrentPlayer(currentPlayer);
    return gc_cpy;
    }

    /*
     * Method: gameOver
     * Specs: Indicates whether the game is over or not
     * Return: boolean
     */
    public boolean gameOver() {
        return conqueredKing() || capturedKing();
    }

    /*
     * Method: ConqueredKing
     * Specs: Checks whether a red king has conquered the throne of the blue king, and vice versa
     * Return: boolean
     */
    public boolean conqueredKing() {
        int red_i = RED_THRONE.getI();
        int red_j = RED_THRONE.getJ();
        int blue_i = BLUE_THRONE.getI();
        int blue_j = BLUE_THRONE.getJ();
        for (Piece piece : board.getBoard())
        {
            Type t = piece.getType();
            Position pos = piece.getPosition();
            if (t == Type.RED_KING && (pos.getI() == blue_i && pos.getJ() == blue_j)){
                System.out.println("Player 1 has won!");
                return true; 
            }
            else if (t == Type.BLUE_KING && (pos.getI() == red_i && pos.getJ() == red_j)) {
                System.out.println("Player 2 has won!");
                return true;
            }
        }

    /* 
    int red_i = RED_THRONE.getI();
    int red_j = RED_THRONE.getJ();
    int blue_i = BLUE_THRONE.getI();
    int blue_j = BLUE_THRONE.getJ();
    if (board[red_i][red_j] == BLUE_KING) {
        System.out.println("Player 2 has won!");
        return true;
    } else if (board[blue_i][blue_j] == RED_KING) {
        System.out.println("Player 1 has won");
        return true;
    }
    */
    return false;
    }
    
    /*
    * Method capturedKing
    * Specs: Checks whether a king has been eaten or not
    * Return: boolean
    */
    public boolean capturedKing() {

        return ((!checkPresence(Type.BLUE_KING) && checkPresence(Type.RED_KING))
                || (checkPresence(Type.BLUE_KING) && !checkPresence(Type.RED_KING)));
    }

    /*
     * Method: checkPresence
     * Specs: Checks whether a king is still on the board or not
     * Args: char king
     * Return: boolean
     */
    public boolean checkPresence(Type king) {
        for (Piece piece : board.getBoard())
        {
            Type t = piece.getType();
            if (t == king)
                return true;
        }
        return false;
    }
    
    /* Start of AI's interface */
    /* Please discuss with Duc if you want to change something */

    public List<Position> allyPositions() {
        if (currentPlayer == RED)
            return board.getRedPositions();
        else
            return board.getBluePositions();
    }

    public List<Position> enemyPositions() {
        if (currentPlayer == BLUE)
            return board.getRedPositions();
        else
            return board.getBluePositions();
    }

    public Position allyKing() {
        if (currentPlayer == RED)
            return board.getRedKing();
        else
            return board.getBlueKing();
    }

    public Position enemyKing() {
        if (currentPlayer == BLUE)
            return board.getRedKing();
        else
            return board.getBlueKing();
    }

    public List<Card> availableCards() {
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

    public List<Position> possiblePositions(Position piece, Card card) {
        List<Position> allies = allyPositions();
        List<Movement> curMovement;
        if (currentPlayer == 0)
            curMovement = card.getRedMovement();
        else
            curMovement = card.getBlueMovement();

        List<Position> newPossiblePos = new ArrayList<>();
        for (Movement movement : curMovement) {
            int deltaRow = movement.getDeltaRow();
            int deltaCol = movement.getDeltaCol();
            Position possPosition = new Position(piece.getI() + deltaRow,
                    piece.getJ() + deltaCol);
            if (0 <= possPosition.i && possPosition.i < 5 &&
                    0 <= possPosition.j && possPosition.j < 5 &&
                    !allies.contains(possPosition))
                newPossiblePos.add(possPosition);
        }
        return newPossiblePos;
    }

    public GameConfiguration nextConfig(Turn turn) {
        Board cpy = copyBoard();
        PlayerHand ph1 = new PlayerHand(0);
        PlayerHand ph2 = new PlayerHand(1);
        Card new_stdby = new Card();
        cpy = applyMove(cpy, turn.getPiece(), turn.getMove());
        exchangeCards(ph1, ph2, turn.getCard(), new_stdby);
        //updatePawnList(new_bluePositions, new_redPositions, turn.getPiece(), turn.getMove());
        //updateKings(new_REDKINGPOS, new_BLUEKINGPOS, turn.getPiece(), turn.getMove());
        GameConfiguration gc_nxt = new GameConfiguration(cpy, ph1, ph2, new_stdby);
        gc_nxt.setCurrentPlayer(nextPlayer());
        return gc_nxt;
    }

    /* End of AI's interface */
}
