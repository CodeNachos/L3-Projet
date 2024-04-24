package WaffleGame.src;

import java.awt.Image;

import javax.swing.JButton;

import java.awt.Dimension;

import Engine.Entities.TileMap.TileMap;
import Engine.Global.Util;
import Engine.Structures.Vector2D;

public class WaffleTileMap extends TileMap {
    public Vector2D tileClicked;
    public boolean next_player = false;

    public WaffleTileMap(int lines, int columns, Dimension area) {
        super(lines, columns, area);
    }

    public WaffleTileMap(int lines, int columns, Dimension area, Vector2D offset) {
        super(lines, columns, area, offset);
    }

    public void populateWaffle() {
        Image waffleSprite = Util.getImage("waffleTile.png");
        Image poisonSprite = Util.getImage("poison_waffleTile.png");

        WaffleTile tile;
        for (int l = 0; l < mapDimension.height; l++) {
            for (int c = 0; c < mapDimension.width; c++) {
                if (l == 0 && c == 0) {
                    tile = new WaffleTile(this, l, c, poisonSprite);
                } else {
                    tile = new WaffleTile(this, l, c, waffleSprite);
                }
                addTile(l, c, tile);
            }
        }
    }

    @Override
    public void process() {
        if (tileClicked != null && !next_player) {
            if (tileClicked.x == 0 && tileClicked.y == 0){
            }
            for (int i = (int)tileClicked.x; i < mapDimension.height; i++) {
                for (int j = (int)tileClicked.y; j < mapDimension.width; j++) {
                    removeTile(i, j);
                }
            }

            tileClicked = null;
            next_player = true;
        }
    }

    
}
