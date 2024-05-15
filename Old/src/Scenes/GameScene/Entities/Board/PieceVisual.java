package Old.src.Scenes.GameScene.Entities.Board;

import Engine.Entities.TileMap.Tile;
import Engine.Entities.TileMap.TileMap;
import Engine.Structures.Sprite;
import Old.src.Scenes.GameScene.Scripts.Piece.Piece;
import Old.src.Scenes.GameScene.Scripts.Piece.PieceType;

public class PieceVisual extends Tile {

    public Piece piece;

    public PieceVisual(TileMap map, Piece p, Sprite sprite) {
        super(map, p.getLine(), p.getColumn(), sprite);
        this.piece = p;
    }

    public PieceType getType() {
        return piece.getType();
    }

    public boolean isBlue() {
        return piece.getType() == PieceType.BLUE_KING || piece.getType() == PieceType.BLUE_PAWN;
    }

    public boolean isRed() {
        return piece.getType() == PieceType.RED_KING || piece.getType() == PieceType.RED_PAWN;
    }

    public Piece getPiece() {
        return piece;
    }
    
}
