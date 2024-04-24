package WaffleGame.src;

import java.awt.Dimension;
import java.awt.Image;
import Engine.Entities.TileMap.TileMap;
import Engine.Global.Util;
import Engine.Structures.Vector2D;

/**
 * The WaffleTileMap class represents a tile map specific to the WaffleGame.
 * It extends the TileMap class to provide customized behavior for the game.
 */
public class WaffleTileMap extends TileMap {

    /** The position of the last clicked tile */
    public Vector2D tileClicked;

    /** Flag indicating whether it's the next player's turn */
    public boolean next_player = false;

    /**
     * Constructs a new WaffleTileMap instance with the specified parameters.
     * @param lines The number of lines in the tile map
     * @param columns The number of columns in the tile map
     * @param area The area size of the tile map
     */
    public WaffleTileMap(int lines, int columns, Dimension area) {
        super(lines, columns, area); // Call superclass constructor with parameters
    }

    /**
     * Constructs a new WaffleTileMap instance with the specified parameters.
     * @param lines The number of lines in the tile map
     * @param columns The number of columns in the tile map
     * @param area The area size of the tile map
     * @param offset The offset position of the tile map
     */
    public WaffleTileMap(int lines, int columns, Dimension area, Vector2D offset) {
        super(lines, columns, area, offset); // Call superclass constructor with parameters
    }

    /**
     * Populates the WaffleTileMap with WaffleTile instances.
     * Uses different sprites for different types of tiles.
     */
    public void populateWaffle() {
        Image waffleSprite = Util.getImage("waffleTile.png"); // Load waffle tile sprite
        Image poisonSprite = Util.getImage("poison_waffleTile.png"); // Load poison waffle tile sprite

        WaffleTile tile;
        for (int l = 0; l < mapDimension.height; l++) {
            for (int c = 0; c < mapDimension.width; c++) {
                if (l == 0 && c == 0) {
                    tile = new WaffleTile(this, l, c, poisonSprite); // Create poison waffle tile
                } else {
                    tile = new WaffleTile(this, l, c, waffleSprite); // Create regular waffle tile
                }
                addTile(l, c, tile); // Add tile to the map
            }
        }
    }

    /**
     * Overrides the process method to handle game-specific logic.
     */
    @Override
    public void process() {
        if (tileClicked != null && !next_player) { // Check if a tile is clicked and it's not the next player's turn
            if (tileClicked.x == 0 && tileClicked.y == 0) {
                // Perform actions specific to the clicked tile position
            }
            for (int i = (int) tileClicked.x; i < mapDimension.height; i++) {
                for (int j = (int) tileClicked.y; j < mapDimension.width; j++) {
                    removeTile(i, j); // Remove tiles from the clicked position onward
                }
            }

            tileClicked = null; // Reset the clicked tile position
            next_player = true; // Set the flag indicating it's the next player's turn
        }
    }
}
