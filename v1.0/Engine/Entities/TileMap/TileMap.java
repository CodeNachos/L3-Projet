package Engine.Entities.TileMap;

import java.awt.Dimension;
import java.awt.Image;

import Engine.Entities.GameObject;
import Engine.Structures.Vector2D;

public class TileMap extends GameObject {
    public Tile[][] gridmap;
    public Dimension mapDimension;
    public Dimension tileDimension;

    public TileMap(int lines, int columns, Dimension area) {
        mapDimension = new Dimension(lines, columns);
        gridmap = new Tile[lines][columns];
        tileDimension = new Dimension((int)(area.width/columns), (int)(area.height/lines));
        this.setSize(area);
    }

    public TileMap(int lines, int columns, Dimension area, Vector2D offset) {
        mapDimension = new Dimension(lines, columns);
        gridmap = new Tile[lines][columns];
        tileDimension = new Dimension((int)(area.width/columns), (int)(area.height/lines));
        setPos(offset);
        this.setLocation((int)offset.x, (int)offset.y);
        this.setSize(area);
    }

    public void addTile(int l, int c, Image sprite) {
        gridmap[l][c] = new Tile(this, l, c, sprite);
    }

    public void addTile(int l, int c, Tile newTile) {
        gridmap[l][c] = newTile;
    }
}