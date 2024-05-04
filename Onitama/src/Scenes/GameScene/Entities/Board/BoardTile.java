package Onitama.src.Scenes.GameScene.Entities.Board;

import java.awt.event.MouseEvent;

import Engine.Entities.TileMap.Tile;
import Engine.Entities.TileMap.TileMap;
import Engine.Structures.Sprite;
import Onitama.src.Scenes.GameScene.Scripts.Match;

public class BoardTile extends Tile {

    public BoardTile(TileMap map, int line, int column, Sprite sprite) {
        super(map, line, column, sprite);
    }

    @Override
    public void input(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_CLICKED) {
            toggleSelected();
        }
    }

    @Override
    public void process(double delta) {
        if (Match.getSelectedPiece() == null)
            return;
        
        if (Match.getSelectedPiece().equals(mapPosition)) {
            if (sprite != ((Board)parentMap).selectedTileSprite)
                sprite = ((Board)parentMap).selectedTileSprite;
        } else if (sprite != ((Board)parentMap).tileSprite) {
            sprite = ((Board)parentMap).tileSprite;
        }
    }

    private void toggleSelected() {
        Piece tilePiece = ((Board)parentMap).pieces.getPiece(getLine(), getColumn());

        if (tilePiece == null)
            return;
        
        if (Match.getCurrentPlayer() == Match.PLAYER1 && tilePiece.isRed() ||
            Match.getCurrentPlayer() == Match.PLAYER2 && tilePiece.isBlue()
        ) {
            if (sprite == ((Board)parentMap).tileSprite) {
                Match.setSelectedPiece(mapPosition);
                sprite = ((Board)parentMap).selectedTileSprite;
            } else {
                Match.setSelectedPiece(null);
                sprite = ((Board)parentMap).tileSprite;
            }
        } 
    }
    
}
