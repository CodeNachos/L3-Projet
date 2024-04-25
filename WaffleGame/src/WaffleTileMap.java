package WaffleGame.src;

import java.awt.Dimension;
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

    // Animation parameters
    /** Flag indicating whether an animation is being processed */
    public boolean animating = false;
    private double updateDelay = 0.02; // in seconds
    private double timeCounter;

    

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
        WaffleTile tile;
        for (int l = 0; l < mapDimension.height; l++) {
            for (int c = 0; c < mapDimension.width; c++) {
                if (l == 0 && c == 0) {
                    tile = new WaffleTile(this, l, c, Main.poisonSprite); // Create poison waffle tile
                } else {
                    tile = new WaffleTile(this, l, c, Main.waffleSprite); // Create regular waffle tile
                }
                addTile(l, c, tile); // Add tile to the map
            }
        }
    }

    /**
     * Overrides the process method to handle game-specific logic.
     */
    @Override
    public void process(double delta) {
        if (animating) {
            processAnimation(delta);
        } else if (tileClicked != null && !next_player) { // Check if a tile is clicked and its not next player's turn
            playAction(tileClicked);
        }
    }

    public void playAction(Vector2D action) {
        Main.actionHistory.addAction();

        removeAndUpdate(tileClicked.getIntX(), tileClicked.getIntY());
        animating = true;

        tileClicked = null; // Reset the clicked tile position
        next_player = true; // Set the flag indicating it's the next player's turn
    }

    /**
     * Processes animation updates based on the elapsed time since the last frame.
     * 
     * @param delta The time elapsed since the last update in seconds.
     */
    public void processAnimation(double delta) {
        timeCounter += delta; // Increment the time counter by the elapsed time
        if (timeCounter >= updateDelay) { // If the time counter exceeds the frame delay
            timeCounter = 0; // Reset the time counter
        } else {
            return; // Skip the animation processing if it's not time yet
        }

        animating = false; // Initialize the animation flag to false
        for(int l = mapDimension.height-1; l >= 0 ; l--) { // Loop through each row of the map
            for (int c = mapDimension.width-1; c >= 0; c--) { // Loop through each column of the map
                if (gridmap[l][c] != null && !((WaffleTile)gridmap[l][c]).state) { // If the tile is not null and not in its animation state
                    removeAndUpdate(l, c); // Remove and update the tile
                    animating |= true; // Set the animation flag to true
                }
            }
        }
    }

    /**
     * Removes a tile from the grid and updates the state of neighboring tiles.
     * 
     * @param l The row index of the tile to be removed.
     * @param c The column index of the tile to be removed.
     */
    public void removeAndUpdate(int l, int c) {
        if (gridmap[l][c] != null) { // Check if the tile is not null
            removeTile(l, c); // Remove the tile from the grid
        }

        if (l < mapDimension.height-1 && gridmap[l+1][c] != null) { // Check if there is a tile below and it's not null
            ((WaffleTile)gridmap[l+1][c]).state = false; // Set the state of the tile below to false (animation state)
        }
        if (c < mapDimension.width-1 && gridmap[l][c+1] != null) { // Check if there is a tile to the right and it's not null
            ((WaffleTile)gridmap[l][c+1]).state = false; // Set the state of the tile to the right to false (animation state)
        }
    }
}
