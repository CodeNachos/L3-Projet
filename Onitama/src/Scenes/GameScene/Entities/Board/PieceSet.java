package Onitama.src.Scenes.GameScene.Entities.Board;

import java.awt.Dimension;
import java.awt.Image;

import Engine.Entities.TileMap.TileMap;
import Engine.Global.Util;
import Engine.Structures.Sprite;
import Engine.Structures.Vector2D;

public class PieceSet extends TileMap {

    public PieceSet(Dimension area, Vector2D offset) {
        super(5, 5, area, offset);
        initPieces();
    }

    private void initPieces() {
        Image redKing = Util.getImage("Onitama/res/Sprites/redKing.png");
        Image redPawn = Util.getImage("Onitama/res/Sprites/redPawn.png");
        Image blueKing = Util.getImage("Onitama/res/Sprites/blueKing.png");
        Image bluePawn = Util.getImage("Onitama/res/Sprites/bluePawn.png");
        
        addTile(4, 0, new Piece(this, 4,0, new Sprite(redPawn), Piece.RED_PAWN));
        addTile(4, 1, new Piece(this, 4,1, new Sprite(redPawn), Piece.RED_PAWN));
        addTile(4, 2, new Piece(this, 4,2, new Sprite(redKing), Piece.RED_KING));
        addTile(4, 3, new Piece(this, 4,3, new Sprite(redPawn), Piece.RED_PAWN));
        addTile(4, 4, new Piece(this, 4,4, new Sprite(redPawn), Piece.RED_PAWN));

        addTile(0, 0, new Piece(this, 0,0, new Sprite(bluePawn), Piece.BLUE_PAWN));
        addTile(0, 1, new Piece(this, 0,1, new Sprite(bluePawn), Piece.BLUE_PAWN));
        addTile(0, 2, new Piece(this, 0,2, new Sprite(blueKing), Piece.BLUE_KING));
        addTile(0, 3, new Piece(this, 0,3, new Sprite(bluePawn), Piece.BLUE_PAWN));
        addTile(0, 4, new Piece(this, 0,4, new Sprite(bluePawn), Piece.BLUE_PAWN));
    }

    public Piece getPiece(int l, int c) {
        return (Piece)gridmap[l][c];
    }
    
}
