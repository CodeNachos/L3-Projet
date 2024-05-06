package ENGINE;

import java.io.Serializable;

//Turn = player that is playing + card they'll play + piece they'll move + actual movement

public class Turn implements Serializable {
    int player;
    Card playCard;
    Position piece;
    Position move;

    Turn(int player, Card playCard, Position piece, Position move)
    {
        this.player = player;
        this.playCard = playCard;
        this.piece = piece;
        this.move = move;
    }

    public int getPlayer()
    {
        return player;
    }

    public Card getCard()
    {
        return playCard;
    }

    public Position getPiece()
    {
        return piece;
    }

    public Position getMove()
    {
        return move;
    }

    //Other things... (Setters -> change something in your turn?)
}
