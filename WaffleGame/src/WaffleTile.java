package WaffleGame.src;

import java.awt.Image;
import java.awt.event.MouseEvent;
import Engine.Entities.TileMap.Tile;
import Engine.Entities.TileMap.TileMap;

/**
 * The WaffleTile class represents a tile specific to the WaffleGame.
 * It extends the Tile class to provide customized behavior for the game.
 */
public class WaffleTile extends Tile {

    /**
     * Constructs a new WaffleTile instance with the specified parameters.
     * @param map The parent TileMap to which the tile belongs
     * @param line The line position of the tile in the map
     * @param column The column position of the tile in the map
     * @param sprite The image sprite associated with the tile
     */
    public WaffleTile(TileMap map, int line, int column, Image sprite) {
        super(map, line, column, sprite); // Call superclass constructor with parameters
    }

    /**
     * Overrides the input method to handle mouse events specific to the WaffleGame.
     * @param e The MouseEvent representing the input event
     */
    @Override
    public void input(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_CLICKED) { // Check if the event is a mouse click
            // Set the clicked tile position in the WaffleTileMap
            ((WaffleTileMap)(parentMap)).tileClicked = mapPosition;
        }
    }
}
