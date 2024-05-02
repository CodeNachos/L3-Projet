package ENGINE;

import java.util.List;
import java.util.Scanner;

public class Player {
    int player;

    Player(int player)
    {
        this.player = player;
    }

    public Turn getTurn(Position piece, Card playCard, Position move)
    {
        Turn turn = new Turn(player, playCard, piece, move);
        return turn;
    }

    public void changePlayer() {
        player = (player + 1) % 2;
        return;
    }

    public int getCurrentPlayer()
    {
        return player;
    }
     

}
