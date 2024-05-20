package Onitama.src.Scenes.GameScene.Entities.Board;

import Engine.Entities.TileMap.Tile;
import Engine.Structures.Sprite;
import Engine.Structures.Vector2D;

public class Piece extends Tile {

    public static enum PieceType {
        RED_KING,
        RED_PAWN,
        BLUE_KING,
        BLUE_PAWN,
        EMPTY
    };

    PieceType type;

    public Piece(PieceMap map, PieceType type, Vector2D position, Sprite sprite) {
        super(map, position.getIntY(), position.getIntX(), sprite);
        this.type = type;
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
    
    public Vector2D getPosition() {
        return mapPosition;
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
        Piece clone = new Piece((PieceMap)parentMap, type, position, sprite);
        return clone;
    }
}
