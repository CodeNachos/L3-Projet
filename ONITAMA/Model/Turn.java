package Model;

import java.io.Serializable;

//Turn = card they'll play + piece they'll move + actual movement

public class Turn implements Serializable {
    Card playCard;
    Piece piece;
    Piece move;

    public Turn(Card playCard, Piece piece, Piece move)
    {
        this.playCard = playCard;
        this.piece = piece;
        this.move = move;
    }

    public Card getCard()
    {
        return playCard;
    }

    public Piece getPiece()
    {
        return piece;
    }

    public Piece getMove()
    {
        return move;
    }

    //Other things... (Setters -> change something in your turn?)
}
