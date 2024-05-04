package Onitama.src.Scenes.GameScene.Entities.Board;

import java.awt.Dimension;

import Engine.Entities.TileMap.TileMap;
import Engine.Structures.Sprite;
import Engine.Structures.Texture;
import Engine.Structures.Vector2D;
import Onitama.src.Main;
import Onitama.src.Scenes.GameScene.Scripts.Match;

public class Board extends TileMap{

    Sprite tileSprite;
    Sprite selectedTileSprite;

    BoardTile selectedTile;

    PieceSet pieces;

    public Board(Dimension area, Vector2D offset, PieceSet pieces) {
        super(5, 5, area, offset);
        
        tileSprite = new Sprite(new Texture(Main.Palette.background, tileDimension.width, tileDimension.height));
        tileSprite.setBorder(5, Main.Palette.selection, 10);

        selectedTileSprite = new Sprite(new Texture(Main.Palette.selection, tileDimension.width, tileDimension.height,10));
        selectedTileSprite.setBorder(5, Main.Palette.selection.brighter(), 10);

        this.pieces = pieces;

        populateBoard();

    }

    private void populateBoard() {
        BoardTile tile;
        for (int l = 0 ; l < mapDimension.height; l++) {
            for (int c = 0 ; c < mapDimension.width; c++) {
                tile = new BoardTile(this, l, c, tileSprite);
                addTile(l, c, tile);
            }
        }
    }
    
}
