package Engine.Entities.TileMap;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import Engine.Entities.GameObject;
import Engine.Global.Util;
import Engine.Structures.Vector2D;

public class TileMap extends GameObject {
    public Tile[][] gridmap;
    public Dimension mapDimension;
    public Dimension tileDimension;

    // DO NOT CHANGE AFTER CONTRUCTOR INITIALIZATION
    private Dimension initialArea;

    public TileMap(int lines, int columns, Dimension area) {
        super();
        this.setSize(area);

        mapDimension = new Dimension(lines, columns);
        gridmap = new Tile[lines][columns];
        tileDimension = new Dimension((int)(area.width/columns), (int)(area.height/lines));

        // DO NOT CHANGE VALUE
        initialArea = area;
    }

    public TileMap(int lines, int columns, Dimension area, Vector2D offset) {
        super(offset);
        this.setSize(area);
        
        mapDimension = new Dimension(lines, columns);
        gridmap = new Tile[lines][columns];
        tileDimension = new Dimension((int)(area.width/columns), (int)(area.height/lines));

        // DO NOT CHANGE VALUE
        initialArea = area;
    }

    public void addTile(int l, int c, Image sprite) {
        gridmap[l][c] = new Tile(this, l, c, sprite);
    }

    public void addTile(int l, int c, Tile newTile) {
        gridmap[l][c] = newTile;
    }

    public void removeTile(int l, int c) {
        gridmap[l][c] = null;
    }
    
    @Override
    public void setSprite(Image sprite) {
        Util.printError("Unsupported operation: Updates to come.");
    }

    @Override
    public void setScale(Vector2D newscale) {
        scale.x = newscale.x;
        scale.y = newscale.y;
        updateSize();
    }

    private void updateSize() {
        this.setSize(
            (int)(initialArea.width*scale.x), 
            (int)(initialArea.height*scale.y)
        );
    }

    @Override
    public void resize(Vector2D ratio) {
        Vector2D updatedValues = new Vector2D();
        // set relative position
        updatedValues.setCoord(position.x * ratio.x, position.y * ratio.y);
        setPos(updatedValues);
        // set relative scaling
        updatedValues.setCoord(scale.x * ratio.x, scale.y * ratio.y);
        setScale(updatedValues);

        // recalculate tile dimentions
        tileDimension = new Dimension(
            (int)Math.ceil(ratio.x * getSize().width/mapDimension.width), 
            (int)Math.ceil(ratio.y * getSize().height/mapDimension.height)
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int l = 0; l < mapDimension.width; l++) {
            for (int c = 0; c < mapDimension.height; c++) {
                if (gridmap[l][c] != null) {
                    gridmap[l][c].paintComponent(g);
                }
            }
        }
    }
}