package Model;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
     public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turn turn = (Turn) o;
        return Objects.equals(playCard, turn.playCard) &&
                Objects.equals(piece, turn.piece) &&
                Objects.equals(move, turn.move);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playCard, piece, move);
    }

    //Other things... (Setters -> change something in your turn?)
}
