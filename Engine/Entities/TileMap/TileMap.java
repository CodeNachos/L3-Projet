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
        super();
        this.setSize(area);

        mapDimension = new Dimension(lines, columns);
        gridmap = new Tile[lines][columns];
        tileDimension = new Dimension((int)(area.width/columns), (int)(area.height/lines));
    }

    public TileMap(int lines, int columns, Dimension area, Vector2D offset) {
        super(offset);
        this.setSize(area);
        
        mapDimension = new Dimension(lines, columns);
        gridmap = new Tile[lines][columns];
        tileDimension = new Dimension((int)(area.width/columns), (int)(area.height/lines));
    }

    public void addTile(int l, int c, Image sprite) {
        gridmap[l][c] = new Tile(this, l, c, sprite);
    }

    public void addTile(int l, int c, Tile newTile) {
        gridmap[l][c] = newTile;
    }
    
    
    @Override
    public void setScale(Vector2D newscale) {
        scale.x = newscale.x;
        scale.y = newscale.y;
        updateSize();
    }

    private void updateSize() {
        this.setSize(
            (int)Math.ceil(getWidth()*scale.x), 
            (int)Math.ceil(getHeight()*scale.y)
        );
    }
}