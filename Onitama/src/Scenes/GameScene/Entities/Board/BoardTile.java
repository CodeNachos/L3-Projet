package Onitama.src.Scenes.GameScene.Entities.Board;


import java.awt.event.MouseEvent;

import Engine.Core.Engines.GameEngine;
import Engine.Entities.TileMap.Tile;
import Engine.Entities.TileMap.TileMap;
import Engine.Structures.Sprite;
import Onitama.src.Scenes.GameScene.GameScene;

public class BoardTile extends Tile {

    private boolean hovering = false;
    private boolean highlighted = false;

    private boolean interactable = true;

    public BoardTile(TileMap map, int line, int column, Sprite sprite) {
        super(map, line, column, sprite);
    }

    public void setIteractable(boolean state) {
        interactable = state;
    }
    
    @Override
    public void input(MouseEvent e) {
        if (!interactable)
            return;
        
        if (e.getID() == MouseEvent.MOUSE_CLICKED) {
            toggleSelected();
        } else if (e.getID() == MouseEvent.MOUSE_ENTERED) {
            hovering = true;
            ((Board)parentMap).setHoveringTile(mapPosition);
        } else if (e.getID() == MouseEvent.MOUSE_EXITED) {
            hovering = false;
            if (((Board)parentMap).isHoveredTile(mapPosition)) 
                ((Board)parentMap).setHoveringTile(null);
        }
    }

    @Override
    public void process(double delta) {
        if (((Board)parentMap).isSelectedAction(mapPosition)) {
            if (hovering) {
                sprite = ((Board)parentMap).hoverSelectedActionTileSprite;
            } else {
                sprite = ((Board)parentMap).selectedActionTileSprite;
            }
        } else if (highlighted) {
            boolean pieceOwned;
            Piece tilePiece;
            
            if (hovering) {
                if (GameScene.getSelectedCard().getPlayer() == GameScene.getCurrentPlayer() && !GameScene.getSelectedCard().isStandBy()) {
                    sprite = ((Board)parentMap).selectedActionTileSprite; 
                } else {
                    sprite = ((Board)parentMap).hoverActionTileSprite;
                }
            } else {
                tilePiece = ((Board)parentMap).getSelectedTile() == null ? 
                    ((Board)parentMap).getHoveringTile() == null ? 
                        null : 
                        GameScene.getPiece(((Board)parentMap).getHoveringTile().getIntY(), ((Board)parentMap).getHoveringTile().getIntX()) : 
                    GameScene.getPiece(((Board)parentMap).getSelectedTile().getIntY(), ((Board)parentMap).getSelectedTile().getIntX());
                
                if (tilePiece == null) {
                    pieceOwned = false;
                } else { 
                    pieceOwned = (
                        (GameScene.getCurrentPlayer() == GameScene.PLAYER1 && tilePiece.isRed()) ||
                        (GameScene.getCurrentPlayer() == GameScene.PLAYER2 && tilePiece.isBlue())
                    );
                }
                
                if (pieceOwned && GameScene.getSelectedCard().getPlayer() == GameScene.getCurrentPlayer() && !GameScene.getSelectedCard().isStandBy()) {
                    sprite = ((Board)parentMap).allowedActionTileSprite; 
                } else {
                    sprite = ((Board)parentMap).hoverActionTileSprite;
                }
            }
        } else if (((Board)parentMap).isSelectedTile(mapPosition)) {
            if (hovering) {
                sprite = ((Board)parentMap).hoverSelectedTileSprite;
            } else {
                sprite = ((Board)parentMap).selectedTileSprite;
            }
        } else if (hovering ) {
            sprite = ((Board)parentMap).hoverTileSprite;
        } else {
            sprite = ((Board)parentMap).tileSprite;
        }
    }

    public void toggleSelected() {
        Piece tilePiece = GameScene.getPiece(mapPosition);

        if (highlighted && GameScene.getSelectedCard().getPlayer() == GameScene.getCurrentPlayer() && 
            !(GameScene.getSelectedCard().isStandBy())) {
            if (!((Board)parentMap).isSelectedAction(mapPosition)) {
                ((Board)parentMap).setSelectedAction(mapPosition);
                GameScene.updateMatch();
            } else {
                ((Board)parentMap).setSelectedAction(null);
            }

        }

        else if (tilePiece != null &&
            (GameScene.getCurrentPlayer() == GameScene.PLAYER1 && tilePiece.isRed() ||
            GameScene.getCurrentPlayer() == GameScene.PLAYER2 && tilePiece.isBlue())
        ) {
            
            if (!((Board)parentMap).isSelectedTile(mapPosition)) {
                ((Board)parentMap).setSelectedTile(mapPosition);
            } else {
                ((Board)parentMap).setSelectedTile(null);
            }
            
        } 
    }

    public void setHighlighted(boolean highlight) {
        highlighted = highlight;
    }

    public void toggleHovering(boolean hovering) {
        
        if (hovering) { 
            ((Board)parentMap).setHoveringTile(mapPosition);
        } else {
            ((Board)parentMap).setHoveringTile(null);
        }

        this.hovering = hovering;


    }

}
