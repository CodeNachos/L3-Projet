package Engine.Entities.UI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SpringLayout.Constraints;

import Engine.Entities.GameObject;
import Engine.Global.Util;
import Engine.Structures.Vector2D;

public class UIFrame extends GameObject {
    public Vector2D position = new Vector2D(0,0);
    public Vector2D scale = new Vector2D(1,1);

    private JPanel frame;
    
    public UIFrame(Dimension area) {
        this.setSize(area);
        updateVisuals();
        frame = new JPanel();
        this.add(frame);
    } 

    public UIFrame(Dimension area, Vector2D position) {
        this.position = position.clone();
        this.setSize(area);
        updateVisuals();
        frame = new JPanel();
        this.add(frame);
    }

    public Vector2D getPos() {
        return position.clone();
    }

    public Vector2D getScale() {
        return scale.clone();
    }

    public void setPos(Vector2D newpos) {
        position.x = newpos.x;
        position.y = newpos.y;
        this.setLocation((int)position.x, (int)position.y);
    }

    public void setScale(Vector2D newscale) {
        scale.x = newscale.x;
        scale.y = newscale.y;
        updateSize();
    }

    public void setSprite(Image sprite) {
        Util.printError("Unsupported operation: Updates to come.");
    }

    private void updateSize() {
        this.setSize(new Dimension(
            (int)(getSize().width * scale.x), 
            (int)(getSize().height * scale.y)
        ));
    }

    private void updateVisuals() {
        setLocation((int)position.x, (int)position.y);
        updateSize();
    }

    public void setLayout(LayoutManager layout) {
        frame.setLayout(layout);
    }

    public void addUIComponent(JComponent obj) {
        frame.add(obj);
    }

    public void addUIComponent(JComponent obj, Constraints layout) {
        frame.add(obj, layout);
    }

    public void removeUIComponent(JComponent obj) {
        frame.remove(obj);
    }

    public void draw() {
        Component[] components = frame.getComponents();
        for (Component component : components) {
            component.repaint();
        }
        }
}
