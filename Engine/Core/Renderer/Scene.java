package Engine.Core.Renderer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.LinkedList;

import javax.swing.JPanel;

import Engine.Core.Controller.Controller;
import Engine.Entities.GameObject;
import Engine.Entities.TileMap.TileMap;
import Engine.Entities.UI.ColorBackground;
import Engine.Entities.UI.UIFrame;
import Engine.Entities.UI.UIObject;
import Engine.Global.Settings;
import Engine.Structures.Vector2D;


public class Scene extends JPanel {
    private static final String UIFrame = null;

    // Scene settings
    Controller control;

    // Scene composition
    public LinkedList<GameObject> components;


    public Scene() {
        components = new LinkedList<>();
        control = new Controller(this);
        addMouseListener(control);
        addKeyListener(control);

        if (Settings.fullscreen) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            setPreferredSize(screenSize);
        } else {
            setPreferredSize(Settings.resolution);
        }
    }

    public void addComponent(GameObject comp) {
        components.add(comp);
    }

    public void addComponent(int index, GameObject comp) {
        components.add(index, comp);
    }

    public void removeComponent(int index) {
        components.remove(index);
    }

    public void removeComponent(GameObject comp) {
        components.remove(comp);
    }

    public void refresh() {
        this.repaint();
    }

    protected void paintComponent(Graphics g) {
        for (GameObject obj : components) {
            // paint scene components
            if (obj instanceof TileMap) {
                TileMap mapObj = (TileMap)obj;
                paintTileMap(mapObj, g);
            } else if (obj instanceof ColorBackground) {
                ColorBackground cbkgdObj = (ColorBackground)obj;
                g.setColor(cbkgdObj.color);
                g.fillRect((int)cbkgdObj.position.x, (int)cbkgdObj.position.y, cbkgdObj.getWidth(), cbkgdObj.getHeight());
            } else if (obj instanceof UIFrame) {
                ((UIFrame)(obj)).draw();
            } else {
                if (obj.sprite != null)
                    g.drawImage(obj.sprite, obj.getLocation().x, obj.getLocation().y, obj.getSize().width, obj.getSize().height,null);
            }
        }
    }

    private void paintTileMap(TileMap mapObj, Graphics g) {
        for (int l = 0; l < mapObj.mapDimension.height; l++) {
            for (int c = 0; c < mapObj.mapDimension.width; c++) {
                if (mapObj.gridmap[l][c] != null && mapObj.gridmap[l][c].sprite != null)
                    g.drawImage(mapObj.gridmap[l][c].sprite, mapObj.gridmap[l][c].getLocation().x, mapObj.gridmap[l][c].getLocation().y, mapObj.gridmap[l][c].getSize().width, mapObj.gridmap[l][c].getSize().height,null);
            }
        }
    }

    protected void resizeComponents(Vector2D ratio) {
        for (GameObject obj : components) {
            if (obj instanceof TileMap) {
                TileMap mapObj = (TileMap)obj;
                resizeMapTiles(mapObj, ratio);
            } else if (obj instanceof UIObject) {
                resizeUIObject((UIObject)obj, ratio);
            } else {
                resizeGameObject(obj, ratio);
            }
        }
    }

    private void resizeGameObject(GameObject obj, Vector2D ratio) {
        Vector2D updatedValues = new Vector2D();
        // set relative position
        updatedValues.setCoord(obj.position.x * ratio.x, obj.position.y * ratio.y);
        obj.setPos(updatedValues);
        // set relative scaling
        updatedValues.setCoord(obj.scale.x * ratio.x, obj.scale.y * ratio.y);
        obj.setScale(updatedValues);
    }

    private void resizeUIObject(UIObject uiObj, Vector2D ratio) {
        if (uiObj.frame != null)
            uiObj.setSize(uiObj.frame.getSize());
        else {
            uiObj.setSize(getSize());
        }
    }

    private void resizeMapTiles(TileMap mapObj, Vector2D ratio) {
        // resize tile map
        resizeGameObject(mapObj, ratio);
        // recalculate tile dimentions
        mapObj.tileDimension = new Dimension(
            (int)(ratio.x * mapObj.getSize().width/mapObj.mapDimension.width), 
            (int)(ratio.y * mapObj.getSize().height/mapObj.mapDimension.height)
        );
        // update tiles
        for (int l = 0; l < mapObj.mapDimension.height; l++) {
            for (int c = 0; c < mapObj.mapDimension.width; c++) {
                if (mapObj.gridmap[l][c] != null) {
                    // resize tiles
                    resizeGameObject(mapObj.gridmap[l][c], ratio);
                }
            }
        }
    }
}