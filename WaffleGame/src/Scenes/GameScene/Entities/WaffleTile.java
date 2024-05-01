package WaffleGame.src.Scenes.GameScene.Entities;

import java.awt.Image;
import java.awt.event.MouseEvent;
import Engine.Entities.TileMap.Tile;
import Engine.Entities.TileMap.TileMap;
import Engine.Structures.Sprite;
import WaffleGame.src.Scenes.GameScene.GameScene;

/**
 * The WaffleTile class represents a tile specific to the WaffleGame.
 * It extends the Tile class to provide customized behavior for the game.
 */
public class WaffleTile extends Tile {
    public boolean state = true;

    /**
     * Constructs a new WaffleTile instance with the specified parameters.
     * @param map The parent TileMap to which the tile belongs
     * @param line The line position of the tile in the map
     * @param column The column position of the tile in the map
     * @param sprite The image sprite associated with the tile
     */
    public WaffleTile(TileMap map, int line, int column, Image sprite) {
        super(map, line, column, new Sprite(sprite)); // Call superclass constructor with parameters
    }

    /**
     * Overrides the input method to handle mouse events specific to the WaffleGame.
     * @param e The MouseEvent representing the input event
     */
    @Override
    public void input(MouseEvent e) {
        if (!GameScene.game.inputEnabled)
            return;
        // Ignore inputs during animations
        if (((WaffleTileMap)parentMap).animating)
            return;
        
        if (e.getID() == MouseEvent.MOUSE_CLICKED) { // Check if the event is a mouse click
            // Set the clicked tile position in the WaffleTileMap
            ((WaffleTileMap)(parentMap)).tileClicked = mapPosition;
        }
    }
}
