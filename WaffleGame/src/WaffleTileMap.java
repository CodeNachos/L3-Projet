package WaffleGame.src;

import java.awt.Image;
import java.awt.Dimension;

import Engine.Entities.TileMap.TileMap;
import Engine.Structures.Vector2D;

public class WaffleTileMap extends TileMap {
    public Vector2D tileClicked;

    public WaffleTileMap(int lines, int columns, Dimension area) {
        super(lines, columns, area);
    }

    public WaffleTileMap(int lines, int columns, Dimension area, Vector2D offset) {
        super(lines, columns, area, offset);
    }

    public void populateWaffle(Image waffleSprite) {
        for (int l = 0; l < mapDimension.height; l++) {
            for (int c = 0; c < mapDimension.width; c++) {
                WaffleTile tile = new WaffleTile(this, l, c, waffleSprite);
                addTile(l, c, tile);
            }
        }
    }

    @Override
    public void process() {
        if (tileClicked != null) {
            for (int i = (int)tileClicked.x; i < mapDimension.height; i++) {
                for (int j = (int)tileClicked.y; j < mapDimension.width; j++) {
                    gridmap[i][j] = null;
                }
            }
            tileClicked = null;
        }
    }
    
}
