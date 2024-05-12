package Onitama.src.Scenes.GameScene.Entities.Board;

import java.awt.event.MouseEvent;

import Engine.Entities.TileMap.Tile;
import Engine.Entities.TileMap.TileMap;
import Engine.Structures.Sprite;
import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Scripts.GameConfiguration;

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
        if (isSelectedAction()) {
            if (hovering) {
                sprite = ((Board)parentMap).hoverSelectedActionTileSprite;
            } else {
                sprite = ((Board)parentMap).selectedActionTileSprite;
            }
        } else if (highlighted) {
            if (hovering) {
                sprite = ((Board)parentMap).hoverActionTileSprite;
            } else {
                sprite = ((Board)parentMap).actionTileSprite;
            }
        } else if (isSelectedPiece()) {
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
        PieceVisual tilePiece = ((Board)parentMap).pieces.getPiece(getLine(), getColumn());
        
        if (highlighted) {
            if (!isSelectedAction()) {
                GameScene.game.setSelectedAction(mapPosition);
            } else {
                GameScene.game.setSelectedAction(null);
            }

        }

        else if (tilePiece != null && 
            (GameScene.game.getCurrentPlayer() == GameConfiguration.PLAYER1 && tilePiece.isRed() ||
            GameScene.game.getCurrentPlayer() == GameConfiguration.PLAYER2 && tilePiece.isBlue())
        ) {
            
            if (!isSelectedPiece()) {
                GameScene.game.setSelectedPiece(mapPosition);
            } else {
                GameScene.game.setSelectedPiece(null);
            }
            
        } 
    }

    public void setHighlighted(boolean highlight) {
        highlighted = highlight;
    }

    private boolean isSelectedPiece() {
        if (!GameScene.game.isPieceSelected())
            return false;
        return GameScene.game.getSelectedPiece().equals(mapPosition);
    }

    private boolean isSelectedAction() {
        if (!GameScene.game.isActionSelected())
            return false;
        return GameScene.game.getSelectedAction().equals(mapPosition);
    }
    
    

}
