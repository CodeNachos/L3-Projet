package Engine.Entities.TileMap;

import java.awt.Image;

import Engine.Entities.GameObject;
import Engine.Structures.Vector2D;

public class Tile extends GameObject {
    public TileMap parentMap;
    public Vector2D mapPosition;
    
    public Tile(TileMap map, int line, int column, Image sprite) {
        super(
            new Vector2D(
                map.position.x + column * map.tileDimension.width,
                map.position.y + line * map.tileDimension.height
            ),
            new Vector2D(
                (double)map.tileDimension.width/(double)sprite.getWidth(null),
                (double)map.tileDimension.height/(double)sprite.getHeight(null)
            ), 
            sprite 
        );
        parentMap = map;
        mapPosition = new Vector2D(line, column);
    }
}