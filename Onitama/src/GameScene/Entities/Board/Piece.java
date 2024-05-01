package Onitama.src.GameScene.Entities.Board;

import Engine.Entities.TileMap.Tile;
import Engine.Entities.TileMap.TileMap;
import Engine.Structures.Sprite;

public class Piece extends Tile {

    static final int KING = 0;
    static final int PAWN = 1;

    public int type;

    public Piece(TileMap map, int line, int column, Sprite sprite, int type) {
        super(map, line, column, sprite);
        this.type = type;
    }
    
}
