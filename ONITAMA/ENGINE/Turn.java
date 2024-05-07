package ENGINE;

import java.io.Serializable;

//Turn = card they'll play + piece they'll move + actual movement

public class Turn implements Serializable {
    Card playCard;
    Position piece;
    Position move;

    Turn(Card playCard, Position piece, Position move)
    {
        this.playCard = playCard;
        this.piece = piece;
        this.move = move;
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
