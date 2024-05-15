package Old.src.Scenes.GameScene.Entities.Board;

import java.awt.Dimension;
import java.util.List;

import Engine.Entities.TileMap.TileMap;
import Engine.Structures.Sprite;
import Engine.Structures.Texture;
import Engine.Structures.Vector2D;
import Old.src.Main;
import Old.src.Scenes.GameScene.GameScene;
import Old.src.Scenes.GameScene.Scripts.GameConfiguration;

public class Board extends TileMap{

    Sprite tileSprite;
    Sprite selectedTileSprite;
    Sprite actionTileSprite;
    Sprite selectedActionTileSprite;
    Sprite hoverTileSprite;
    Sprite hoverSelectedTileSprite;
    Sprite hoverActionTileSprite;
    Sprite hoverSelectedActionTileSprite;

    BoardTile selectedTile;

    PieceSet pieces;

    private boolean highlighting = false;

    public Board(Dimension area, Vector2D offset, PieceSet pieces) {
        super(5, 5, area, offset);
        
        tileSprite = new Sprite(new Texture(Main.Palette.background, tileDimension.width, tileDimension.height));
        tileSprite.setBorder(5, Main.Palette.selection, 10);

        selectedTileSprite = new Sprite(new Texture(Main.Palette.selection, tileDimension.width, tileDimension.height,10));
        selectedTileSprite.setBorder(5, Main.Palette.highlight, 10);

        actionTileSprite = new Sprite(new Texture(Main.Palette.background.brighter(), tileDimension.width, tileDimension.height,10));
        actionTileSprite.setBorder(5, Main.Palette.orange, 10);

        selectedActionTileSprite = new Sprite(new Texture(Main.Palette.background.brighter(), tileDimension.width, tileDimension.height,10));
        selectedActionTileSprite.setBorder(5, Main.Palette.green, 10);

        hoverTileSprite = new Sprite(new Texture(Main.Palette.background.brighter(), tileDimension.width, tileDimension.height,10));
        hoverTileSprite.setBorder(5, Main.Palette.selection.brighter(), 10);

        hoverSelectedTileSprite = new Sprite(new Texture(Main.Palette.selection.brighter(), tileDimension.width, tileDimension.height,10));
        hoverSelectedTileSprite.setBorder(5, Main.Palette.highlight.brighter(), 10);

        hoverActionTileSprite = new Sprite(new Texture(Main.Palette.background.brighter().brighter(), tileDimension.width, tileDimension.height,10));
        hoverActionTileSprite.setBorder(5, Main.Palette.orange.brighter(), 10);

        hoverSelectedActionTileSprite = new Sprite(new Texture(Main.Palette.background.brighter(), tileDimension.width, tileDimension.height,10));
        hoverSelectedActionTileSprite.setBorder(5, Main.Palette.green.brighter(), 10);

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


    @Override
    public void process(double delta) {
        if (GameScene.game.isPieceSelected() && GameScene.game.isCardSelected()) {
            clearHightlighting();
            highlightMoves(GameScene.game.getSelectedCard());
        } else if (highlighting) {
            clearHightlighting();
        }
    }

    public void highlightMoves(String card) {
        List<Vector2D> moves;
        moves = GameScene.game.getCurrentPlayer() == GameConfiguration.PLAYER1 ? GameScene.game.gameCards.get(card).getRedMovement() : GameScene.game.gameCards.get(card).getBlueMovement(); 
        
        Vector2D actionPos;
        for (Vector2D m : moves) {
            actionPos = new Vector2D(
                GameScene.game.getSelectedPiece().getIntY() + m.getIntX(),
                GameScene.game.getSelectedPiece().getIntX() + m.getIntY()
            );
            
            if (!isValidTile(actionPos))
                continue;

            PieceVisual pieceAtAction =  ((PieceVisual)pieces.getTile(actionPos.getIntX(), actionPos.getIntY()));
            if (pieceAtAction == null) {
                ((BoardTile)getTile(actionPos.getIntX(), actionPos.getIntY())).setHighlighted(true);
            } else {
                if ((GameScene.game.getCurrentPlayer() == GameConfiguration.PLAYER1 && pieceAtAction.isBlue()) || 
                    (GameScene.game.getCurrentPlayer() == GameConfiguration.PLAYER2 && pieceAtAction.isRed())
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

    
}
