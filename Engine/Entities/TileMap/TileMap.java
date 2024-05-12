package Engine.Entities.TileMap;

import java.awt.Dimension;
import java.awt.Graphics;

import Engine.Entities.GameObject;
import Engine.Global.Util;
import Engine.Structures.Sprite;
import Engine.Structures.Vector2D;

/**
 * The TileMap class represents a tile-based map in the game world.
 * It manages the grid of tiles and handles rendering and resizing.
 */
public class TileMap extends GameObject {

    public Tile[][] gridmap; // Grid of tiles
    public Dimension mapDimension; // Dimension of the map
    public Dimension tileDimension; // Dimension of each tile

    // DO NOT CHANGE AFTER CONSTRUCTOR INITIALIZATION
    private Dimension initialArea; // Initial area size of the map

    /**
     * Constructs a new TileMap instance with the specified dimensions and area.
     * @param lines The number of lines (rows) in the map
     * @param columns The number of columns in the map
     * @param area The area size of the map
     */
    public TileMap(int lines, int columns, Dimension area) {
        super();
        this.setSize(area); // Set size of the TileMap component

        mapDimension = new Dimension(columns, lines); // Initialize map dimension
        gridmap = new Tile[lines][columns]; // Initialize grid of tiles
        tileDimension = new Dimension((int)(area.width / columns), (int)(area.height / lines)); // Calculate tile dimension

        // DO NOT CHANGE VALUE
        initialArea = area; // Store initial area size of the map
    }

    /**
     * Constructs a new TileMap instance with the specified dimensions, area, and offset.
     * @param lines The number of lines (rows) in the map
     * @param columns The number of columns in the map
     * @param area The area size of the map
     * @param offset The offset position of the map
     */
    public TileMap(int lines, int columns, Dimension area, Vector2D offset) {
        super(offset);
        this.setSize(area); // Set size of the TileMap component

        mapDimension = new Dimension(columns, lines); // Initialize map dimension
        gridmap = new Tile[lines][columns]; // Initialize grid of tiles
        tileDimension = new Dimension((int)(area.width / columns), (int)(area.height / lines)); // Calculate tile dimension

        // DO NOT CHANGE VALUE
        initialArea = area; // Store initial area size of the map
    }

    /**
     * Adds a tile to the specified position in the map with the given sprite.
     * @param l The line (row) index of the tile
     * @param c The column index of the tile
     * @param sprite The sprite image of the tile
     */
    public void addTile(int l, int c, Sprite sprite) {
        gridmap[l][c] = new Tile(this, l, c, sprite); // Create a new Tile instance and add it to the grid
    }

    /**
     * Adds a tile to the specified position in the map.
     * @param l The line (row) index of the tile
     * @param c The column index of the tile
     * @param newTile The tile object to add
     */
    public void addTile(int l, int c, Tile newTile) {
        gridmap[l][c] = newTile; // Add the specified tile object to the grid
    }

    /**
     * Removes the tile at the specified position from the map.
     * @param l The line (row) index of the tile
     * @param c The column index of the tile
     */
    public void removeTile(int l, int c) {
        gridmap[l][c] = null; // Remove the tile at the specified position
    }

    /**
     * Removes all tiles from the map.
     */
    public void clearMap() {
        for (int l = 0; l < mapDimension.height; l++) {
            for (int c = 0; c < mapDimension.width; c++) {
                gridmap[l][c] = null;
            }
        }
    }

    /**
     * Recuperates the tile at coordinates (l,c)
     * @param l The line (row) index of the tile
     * @param c The column index of the tile
     * @return Tile at coordinates (l,c), null if empty
     */
    public Tile getTile(int l, int c) {
        return gridmap[l][c];
    }

    @Override
    public void setSprite(Sprite sprite) {
        Util.printError("Unsupported operation: Updates to come.");
    }

    @Override
    public void setScale(Vector2D newscale) {
        Vector2D ratio = new Vector2D(newscale.x / scale.x, newscale.y/scale.y);
        resize(ratio);
        //scale.x = newscale.x; // Set scale x-coordinate
        //scale.y = newscale.y; // Set scale y-coordinate
        //updateSize(); // Update size based on scale
    }

    @Override
    public void resize(Vector2D ratio) {
        Vector2D updatedValues = new Vector2D();
        // set relative position
        updatedValues.setCoord(position.x * ratio.x, position.y * ratio.y);
        setPos(updatedValues);
        // set relative scaling
        updatedValues.setCoord(scale.x * ratio.x, scale.y * ratio.y);
        scale.x = updatedValues.x; // Set scale x-coordinate
        scale.y = updatedValues.y; // Set scale y-coordinate
        // Update size based on scale
        this.setSize(
            (int)(initialArea.width * scale.x), // Set width based on initial area width and scale
            (int)(initialArea.height * scale.y) // Set height based on initial area height and scale
        );

        // recalculate tile dimensions
        tileDimension = new Dimension(
            (int)Math.ceil(ratio.x * getSize().width / mapDimension.width), // Calculate new width based on ratio
            (int)Math.ceil(ratio.y * getSize().height / mapDimension.height) // Calculate new height based on ratio
        );
        // update tiles
        for (int l = 0; l < mapDimension.height; l++) {
            for (int c = 0; c < mapDimension.width; c++) {
                if (gridmap[l][c] != null) {
                    // resize tiles
                    gridmap[l][c].resize(ratio);
                }
            }
        }
    }

    /**
     * Overrides the paintComponent method to render the tile map.
     * @param g The Graphics context used for painting
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Call superclass's paintComponent method

        // Render each tile in the map
        for (int l = 0; l < mapDimension.height; l++) {
            for (int c = 0; c < mapDimension.width; c++) {
                if (gridmap[l][c] != null) {
                    gridmap[l][c].paintComponent(g); // Paint the tile
                }
            }
        }
    }

    /**
     * Updates tile map by updating every tile.
     * 
     * @param delta The time since the last update in seconds
     */
    public void update(double delta) {
        this.process(delta);

        for (int l = 0; l < mapDimension.height; l++) {
            for (int c = 0; c < mapDimension.width; c++) {
                if (gridmap[l][c] != null) {
                    gridmap[l][c].process(delta);
                }
            }
        }
    }
}
