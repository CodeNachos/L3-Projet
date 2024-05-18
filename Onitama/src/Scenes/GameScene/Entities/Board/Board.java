package Onitama.src.Scenes.GameScene.Entities.Board;

import java.awt.Dimension;
import java.util.List;

import Engine.Entities.TileMap.TileMap;
import Engine.Structures.Sprite;
import Engine.Structures.Texture;
import Engine.Structures.Vector2D;
import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Entities.Card.Card;
import Onitama.src.Main;

public class Board extends TileMap {
    Sprite tileSprite;
    Sprite selectedTileSprite;
    Sprite actionTileSprite;
    Sprite selectedActionTileSprite;
    Sprite hoverTileSprite;
    Sprite hoverSelectedTileSprite;
    Sprite hoverActionTileSprite;
    Sprite allowedActionTileSprite;
    Sprite hoverAllowedActionTileSprite;
    Sprite hoverSelectedActionTileSprite;

    private boolean highlighting = false; 

    BoardTile hoveringTile = null;

    Card lastCardSelected = null;

    // Turn info
    BoardTile selectedTile = null;
    BoardTile selectedAction = null;

    public Board(Dimension area, Vector2D offset) {
        super(5, 5, area, offset);
        
        tileSprite = new Sprite(new Texture(Main.Palette.background, tileDimension.width, tileDimension.height));
        tileSprite.setBorder(5, Main.Palette.selection, 10);

        selectedTileSprite = new Sprite(new Texture(Main.Palette.selection, tileDimension.width, tileDimension.height,10));
        selectedTileSprite.setBorder(5, Main.Palette.highlight, 10);

        actionTileSprite = new Sprite(new Texture(Main.Palette.background.brighter(), tileDimension.width, tileDimension.height,10));
        actionTileSprite.setBorder(5, Main.Palette.selection.brighter(), 10);

        selectedActionTileSprite = new Sprite(new Texture(Main.Palette.background.brighter().brighter(), tileDimension.width, tileDimension.height,10));
        selectedActionTileSprite.setBorder(5, Main.Palette.green, 10);

        hoverTileSprite = new Sprite(new Texture(Main.Palette.background.brighter(), tileDimension.width, tileDimension.height,10));
        hoverTileSprite.setBorder(5, Main.Palette.selection.brighter(), 10);

        hoverSelectedTileSprite = new Sprite(new Texture(Main.Palette.selection.brighter(), tileDimension.width, tileDimension.height,10));
        hoverSelectedTileSprite.setBorder(5, Main.Palette.highlight.brighter(), 10);

        hoverActionTileSprite = new Sprite(new Texture(Main.Palette.background.brighter().brighter(), tileDimension.width, tileDimension.height,10));
        hoverActionTileSprite.setBorder(5, Main.Palette.highlight, 10);

        allowedActionTileSprite = new Sprite(new Texture(Main.Palette.background.brighter().brighter(), tileDimension.width, tileDimension.height,10));
        allowedActionTileSprite.setBorder(5, Main.Palette.orange, 10);

        hoverAllowedActionTileSprite = new Sprite(new Texture(Main.Palette.orange, tileDimension.width, tileDimension.height,10));
        hoverAllowedActionTileSprite.setBorder(5, Main.Palette.orange.brighter(), 10);

        hoverSelectedActionTileSprite = new Sprite(new Texture(Main.Palette.background.brighter(), tileDimension.width, tileDimension.height,10));
        hoverSelectedActionTileSprite.setBorder(5, Main.Palette.green.brighter(), 10);

        populateBoard();

    }

    public void setIteractable(boolean state) {
        for (int l = 0 ; l < mapDimension.height; l++) {
            for (int c = 0 ; c < mapDimension.width; c++) {
                ((BoardTile)getTile(l, c)).setIteractable(state);;
            }
        }
    }

    @Override
    public void process(double delta) {
        if ((isTileSelected() || isHoveringTile()) && GameScene.isCardSelected()) {
            if ((lastCardSelected != null) && GameScene.getSelectedCard() != lastCardSelected) {
                lastCardSelected = GameScene.getSelectedCard();
                setSelectedAction(null);
            }
            clearHightlighting();
            highlightMoves(GameScene.getSelectedCard().getName());
        } else if (!GameScene.isCardSelected()) {
            lastCardSelected = null;
            setSelectedAction(null);
            clearHightlighting();
        } else if (highlighting) {
            clearHightlighting();
        }
    }

    public Vector2D getSelectedTile() {
        if (selectedTile == null) {
            return null;
        }
        return selectedTile.getMapPosition();
    }

    public void setSelectedTile(Vector2D t) {
        if (t == null) {
            selectedTile = null;
        } else {
            if (selectedTile != null)
                selectedTile.toggleSelected();
            selectedTile = (BoardTile)getTile(t.getIntY(), t.getIntX());
        }

        setSelectedAction(null);
    }

    public boolean isSelectedTile(Vector2D t) {
        if (selectedTile == null) {
            return false;
        }

        return selectedTile.getMapPosition().equals(t);
    }

    public boolean isTileSelected() {
        return selectedTile != null;
    }

    public Vector2D getHoveringTile() {
        if (hoveringTile == null) {
            return null;
        }
        return hoveringTile.getMapPosition();
    }

    public void setHoveringTile(Vector2D t) {
        if (t == null) {
            hoveringTile = null;
        } else {
            hoveringTile = (BoardTile)getTile(t.getIntY(), t.getIntX());
        }
    }

    public boolean isHoveredTile(Vector2D t) {
        if (hoveringTile == null) {
            return false;
        }

        return hoveringTile.getMapPosition().equals(t);
    }

    public boolean isHoveringTile() {
        return hoveringTile != null;
    }

    public Vector2D getSelectedAction() {
        if (selectedAction == null)
            return null;

        return selectedAction.getMapPosition(); 
    }

    public void setSelectedAction(Vector2D t) {
        if (t == null) {
            selectedAction = null;
        } else {
            selectedAction = (BoardTile)getTile(t.getIntY(), t.getIntX());
        }
    }

    public boolean isSelectedAction(Vector2D t) {
        if (selectedAction == null) {
            return false;
        }

        return selectedAction.getMapPosition().equals(t);
    }

    public boolean isSelectedAction() {
        return selectedAction != null;
    }


    public void highlightMoves(String card) {
        Piece currPiece;
        if (isTileSelected()) {
            currPiece = GameScene.getPiece(getSelectedTile());
        } else if (isHoveringTile()) {
            currPiece = GameScene.getPiece(getHoveringTile());
        } else {
            return;
        }

        if (currPiece == null)
                return;

        List<Vector2D> redMoves, blueMoves;
        redMoves =  GameScene.getGameCards().get(card).getRedMovement(); 
        blueMoves = GameScene.getGameCards().get(card).getBlueMovement();
        
        Vector2D actionPos;
        for (int m = 0; m < redMoves.size(); m++) {
            if (currPiece.isRed()) {
                actionPos = new Vector2D(
                    currPiece.getLine() + redMoves.get(m).getIntX(),
                    currPiece.getColumn() + redMoves.get(m).getIntY()
                );
            } else {
                actionPos = new Vector2D(
                    currPiece.getLine() + blueMoves.get(m).getIntX(),
                    currPiece.getColumn() + blueMoves.get(m).getIntY()
                );
            }
            if (!isValidTile(actionPos))
                continue;

            Piece pieceAtAction =  (GameScene.getPiece(actionPos.getIntX(), actionPos.getIntY()));
            if (pieceAtAction == null) {
                ((BoardTile)getTile(actionPos.getIntX(), actionPos.getIntY())).setHighlighted(true);
            } else {
                if ((currPiece.isRed() && pieceAtAction.isBlue()) || 
                    (currPiece.isBlue() && pieceAtAction.isRed())
                ) {
                    ((BoardTile)getTile(actionPos.getIntX(), actionPos.getIntY())).setHighlighted(true);
                }
            }

        }

        highlighting = true;
    }

    public void clearHightlighting() {
        for (int l = 0 ; l < mapDimension.height; l++) {
            for (int c = 0 ; c < mapDimension.width; c++) {
                ((BoardTile)getTile(l, c)).setHighlighted(false);
            }
        }
        highlighting = false;
    }

    
    public boolean isValidTile(Vector2D t) {
        if (t.getIntX() < 0 || t.getIntX() >= 5)
            return false;

        if (t.getIntY() < 0 || t.getIntY() >= 5)
            return false;

        return true;
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
