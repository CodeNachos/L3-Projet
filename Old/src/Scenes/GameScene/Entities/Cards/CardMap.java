package Old.src.Scenes.GameScene.Entities.Cards;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import Engine.Entities.TileMap.Tile;
import Engine.Entities.TileMap.TileMap;
import Engine.Structures.Sprite;
import Engine.Structures.Texture;
import Engine.Structures.Vector2D;
import Old.src.Main;
import Old.src.Scenes.GameScene.GameScene;
import Old.src.Scenes.GameScene.Scripts.GameConfiguration;
import Old.src.Scenes.GameScene.Scripts.Card.CardInfo;

public class CardMap extends TileMap {

    Texture centerTileTexture;
    Texture actionTileTexture;
    Texture emptyTileTexture;

    Sprite centerTileSprite;
    Sprite actionTileSprite;
    Sprite emptyTileSprite;

    public CardMap(Dimension area) {
        super(5, 5, area);

        centerTileTexture = new Texture(Main.Palette.foreground, tileDimension.width, tileDimension.height, 5);
        centerTileSprite = new Sprite(centerTileTexture);
        centerTileSprite.setBorder(1, Main.Palette.selection.brighter(), 5);

        actionTileTexture = new Texture(Main.Palette.orange, tileDimension.width, tileDimension.height, 5);
        actionTileSprite = new Sprite(actionTileTexture);
        actionTileSprite.setBorder(1, Main.Palette.selection.brighter(), 5);

        emptyTileTexture = new Texture(new Color(0,0,0,0), tileDimension.width, tileDimension.height, 5);
        emptyTileSprite = new Sprite(emptyTileTexture);
        emptyTileSprite.setBorder(1, Main.Palette.selection.brighter(), 5);
    }

    public void populateActions(String name, int player) {
        CardInfo cardInfo = GameScene.game.gameCards.get(name);
        List<Vector2D> actions = player == GameConfiguration.PLAYER1 ? cardInfo.getRedMovement() : cardInfo.getBlueMovement();

        for (int l = 0; l < mapDimension.height ; l++) {
            for (int c = 0; c < mapDimension.width; c++) {
                gridmap[l][c] = new Tile(this, l, c, emptyTileSprite);
            }
        }

        gridmap[2][2] = new Tile(this, 2, 2, centerTileSprite);

        for (Vector2D action : actions) {
            gridmap[2 + action.getIntX()][2 + action.getIntY()] = new Tile(this, 2 + action.getIntX(), 2 + action.getIntY(), actionTileSprite);
        }


    }
    
}
