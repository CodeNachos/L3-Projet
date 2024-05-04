package Onitama.src.Scenes.GameScene.Entities.Board;

import java.awt.event.MouseEvent;

import Engine.Entities.TileMap.Tile;
import Engine.Entities.TileMap.TileMap;
import Engine.Structures.Sprite;
import Onitama.src.Scenes.GameScene.Scripts.Match;

public class BoardTile extends Tile {

    private boolean highlighted = false;
    private boolean hovering = false;

    public BoardTile(TileMap map, int line, int column, Sprite sprite) {
        super(map, line, column, sprite);
    }

    @Override
    public void input(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_CLICKED) {
            toggleSelected();
        } else if (e.getID() == MouseEvent.MOUSE_ENTERED) {
            hovering = true;
        } else if (e.getID() == MouseEvent.MOUSE_EXITED) {
            hovering = false;
        }
    }

    @Override
    public void process(double delta) {
        if (highlighted) {
            if (hovering) {
                sprite = ((Board)parentMap).hoverHighlightTileSprite;
            } else {
                sprite = ((Board)parentMap).moveHighlightTileSprite;
            }
        } else if (isSelected()) {
            if (hovering) {
                sprite = ((Board)parentMap).hoverSelectedTileSprite;
            } else {
                sprite = ((Board)parentMap).selectedTileSprite;
            }
        } else if (hovering) {
            sprite = ((Board)parentMap).hoverTileSprite;
        } else {
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
            if (!isSelected()) {
                Match.setSelectedPiece(mapPosition);
            } else {
                Match.setSelectedPiece(null);
            }
        } 
    }

    public void setHighlighted(boolean highlight) {
        highlighted = highlight;
    }

    private boolean isSelected() {
        if (!Match.isPieceSelected())
            return false;
        return Match.getSelectedPiece().equals(mapPosition);
    }
    
}
