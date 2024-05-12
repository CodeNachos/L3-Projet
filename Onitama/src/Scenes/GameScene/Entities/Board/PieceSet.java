package Onitama.src.Scenes.GameScene.Entities.Board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import Engine.Entities.TileMap.TileMap;
import Engine.Global.Util;
import Engine.Structures.Sprite;
import Engine.Structures.Texture;
import Engine.Structures.Vector2D;
import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Scripts.GameConfiguration;
import Onitama.src.Scenes.GameScene.Scripts.Piece.Piece;

public class PieceSet extends TileMap {

    Image redKingImage;    
    Image redPawnImage;
    Image blueKingImage;
    Image bluePawnImage;

    Sprite redKingSprite;    
    Sprite redPawnSprite;
    Sprite blueKingSprite;
    Sprite bluePawnSprite;
    Sprite emptySprite = new Sprite(new Texture(new Color(0,0,0,0), tileDimension.width, mapDimension.height));

    public PieceSet(Dimension area, Vector2D offset) {
        super(5, 5, area, offset);

        redKingImage = Util.getImage("Onitama/res/Sprites/redKing.png");
        redPawnImage = Util.getImage("Onitama/res/Sprites/redPawn.png");
        blueKingImage = Util.getImage("Onitama/res/Sprites/blueKing.png");
        bluePawnImage = Util.getImage("Onitama/res/Sprites/bluePawn.png");

        redKingSprite = new Sprite(redKingImage);        
        redPawnSprite = new Sprite(redPawnImage);
        blueKingSprite = new Sprite(blueKingImage);    
        bluePawnSprite = new Sprite(bluePawnImage);    

        updatePieces();
    }

    public void updatePieces() {
        clearMap();

        for (Piece p : GameScene.game.getPlayerPieces(GameConfiguration.PLAYER1)) {
            addTile(p.getLine(), p.getColumn(), new PieceVisual(this, p, chooseSprite(p)));
        }

        for (Piece p : GameScene.game.getPlayerPieces(GameConfiguration.PLAYER2)) {
            addTile(p.getLine(), p.getColumn(), new PieceVisual(this, p, chooseSprite(p)));
        }

    }

    public PieceVisual getPiece(int l, int c) {
        return (PieceVisual)gridmap[l][c];
    }

    private Sprite chooseSprite(Piece p) {
        switch (p.getType()) {
            case RED_KING:
                return redKingSprite;
            case RED_PAWN:
                return redPawnSprite;
            case BLUE_KING:
                return blueKingSprite;
            case BLUE_PAWN:
                return bluePawnSprite;

            default:
                return emptySprite;
        }
    }
    
}
