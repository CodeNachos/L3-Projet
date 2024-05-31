package Engine.Entities.TileMap;

import java.awt.Graphics;

import Engine.Entities.GameObject;
import Engine.Global.Util;
import Engine.Structures.Sprite;
import Engine.Structures.Vector2D;

/**
 * The Tile class represents a tile in a tile-based map.
 * It inherits properties and functionalities from GameObject.
 */
public class Tile extends GameObject {

    public TileMap parentMap; // Reference to the parent tile map
    protected Vector2D mapPosition; // Position of the tile in the map grid

    /**
     * Constructs a new Tile instance with the specified map, position, and sprite.
     * @param map The parent tile map
     * @param line The line (row) index of the tile in the map
     * @param column The column index of the tile in the map
     * @param sprite The sprite image of the tile
     */
    public Tile(TileMap map, int line, int column, Sprite sprite) {
        super(
            new Vector2D(
                column * map.tileDimension.width, // Calculate x-coordinate based on column and tile width
                line * map.tileDimension.height // Calculate y-coordinate based on line and tile height
            ),
            new Vector2D(
                (double)map.tileDimension.width / (double)sprite.getWidth(), // Calculate x-scale based on tile width and sprite width
                (double)map.tileDimension.height / (double)sprite.getHeight() // Calculate y-scale based on tile height and sprite height
            ),
            sprite
        );
        parentMap = map; // Set reference to parent tile map
        mapPosition = new Vector2D(column, line); // Set position of the tile in the map grid
    }

    public Tile(int line, int column) {
        mapPosition = new Vector2D(column, line);
    }

    public int getLine() {
        return mapPosition.getIntY();
    }

    public int getColumn() {
        return mapPosition.getIntX();
    }

    public Vector2D getMapPosition() {
        return mapPosition.clone();
    }

    public void setMapPosition(Vector2D pos) {
        if (pos.getIntX() < 0 || pos.getIntX() > parentMap.mapDimension.width ||
            pos.getIntY() < 0 || pos.getIntY() > parentMap.mapDimension.height
        ) {
            Util.printError("Invalid tile destination position");
            return;
        }
        
        parentMap.removeTile(getLine(), getColumn());
        mapPosition = pos.clone();
        setPos(new Vector2D(
            getColumn() * parentMap.tileDimension.width, // Calculate x-coordinate based on column and tile width
            getLine() * parentMap.tileDimension.height // Calculate y-coordinate based on line and tile height
        ));
        parentMap.addTile(getLine(), getColumn(), this);
    }

    /**
     * Overrides the paintComponent method to render the tile.
     * @param g The Graphics context used for painting
     */
    public void paintComponent(Graphics g) {
        sprite.drawSprite(g, position.getIntX(), position.getIntY(), getSize().width, getSize().height); // Draw the tile sprite
    }
}
