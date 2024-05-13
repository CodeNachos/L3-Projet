package Onitama.src.Scenes.GameScene.Scripts.Piece;

import java.io.Serializable;

import Engine.Structures.Vector2D;

public class Piece implements Serializable {
    
    public PieceType type;
    public Vector2D position;

    public Piece(PieceType type, Vector2D position) {
        this.type = type;
        this.position = position.clone();
    }

    public PieceType getType() {
        return type;
    }

    public boolean isBlue() {
        return type == PieceType.BLUE_KING || type == PieceType.BLUE_PAWN;
    }

    public boolean isRed() {
        return type == PieceType.RED_KING || type == PieceType.RED_PAWN;
    }

    public int getLine() {
        return position.getIntY();
    }

    public int getColumn() {
        return position.getIntX();
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D newPos) {
        position = newPos.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        
        if (! (o instanceof Piece))
            return false;
        
        Piece p = (Piece) o;
        return (p.getType() == this.getType() && p.getPosition().equals(this.getPosition()));

    }

    @Override
    public Piece clone() {
        Piece clone = new Piece(type, position);
        return clone;
    }
}
