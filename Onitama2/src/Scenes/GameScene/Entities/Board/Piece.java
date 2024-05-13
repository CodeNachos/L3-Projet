package Onitama2.src.Scenes.GameScene.Entities.Board;

import Engine.Structures.Vector2D;

public class Piece {

    public static enum PieceType {
        RED_KING,
        RED_PAWN,
        BLUE_KING,
        BLUE_PAWN
    };

    PieceType type;
    Vector2D position;

    public Piece(PieceType type, Vector2D position) {
        
    }
}
