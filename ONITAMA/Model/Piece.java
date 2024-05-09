package Model;

import java.io.Serializable;
import java.util.Objects;

public class Piece implements Serializable {
    Type t;
    Position piece_position;

    public Piece(Type t, Position piece_position)
    {
        this.t = t;
        this.piece_position = piece_position;
    }

    public void setType(Type t)
    {
        this.t = t;
    }

    public void setPosition(Position pos)
    {
        this.piece_position = pos;
    }

    public Type getType()
    {
        return t;
    }

    public Position getPosition()
    {
        return piece_position;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Piece other = (Piece) obj;
        return t == other.t && piece_position.equals(other.piece_position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(t, piece_position);
    }

}
