package WaffleGame.src;

import java.awt.Image;
import java.awt.event.MouseEvent;

import Engine.Entities.TileMap.Tile;
import Engine.Entities.TileMap.TileMap;

public class WaffleTile extends Tile {

    public WaffleTile(TileMap map, int line, int column, Image sprite) {
        super(map, line, column, sprite);
    }

    @Override
    public void input(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_CLICKED) {
            //System.out.println(this.getBounds());
            ((WaffleTileMap)(parentMap)).tileClicked = mapPosition;
        }
    }
    
}
