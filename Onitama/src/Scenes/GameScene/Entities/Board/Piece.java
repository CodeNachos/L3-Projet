package Onitama.src.Scenes.GameScene.Entities.Board;

import Engine.Entities.TileMap.Tile;
import Engine.Entities.TileMap.TileMap;
import Engine.Structures.Sprite;

public class Piece extends Tile {

    static final int RED_KING = 0;
    static final int RED_PAWN = 1;
    static final int BLUE_KING = 2;
    static final int BLUE_PAWN = 3;

    public int type;

    public Piece(TileMap map, int line, int column, Sprite sprite, int type) {
        super(map, line, column, sprite);
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public boolean isBlue() {
        return type == BLUE_KING || type == BLUE_PAWN;
    }

    public boolean isRed() {
        return type == RED_KING || type == RED_PAWN;
    }
    
}
