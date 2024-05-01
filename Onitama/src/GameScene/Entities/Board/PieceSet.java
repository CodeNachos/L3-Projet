package Onitama.src.GameScene.Entities.Board;

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
        Image redKing = Util.getImage("Onitama/res/redKing.png");
        Image redPawn = Util.getImage("Onitama/res/redPawn.png");
        Image blueKing = Util.getImage("Onitama/res/blueKing.png");
        Image bluePawn = Util.getImage("Onitama/res/bluePawn.png");
        
        addTile(4, 0, new Sprite(redPawn));
        addTile(4, 1, new Sprite(redPawn));
        addTile(4, 2, new Sprite(redKing));
        addTile(4, 3, new Sprite(redPawn));
        addTile(4, 4, new Sprite(redPawn));

        addTile(0, 0, new Sprite(bluePawn));
        addTile(0, 1, new Sprite(bluePawn));
        addTile(0, 2, new Sprite(blueKing));
        addTile(0, 3, new Sprite(bluePawn));
        addTile(0, 4, new Sprite(bluePawn));
    }
    
}
