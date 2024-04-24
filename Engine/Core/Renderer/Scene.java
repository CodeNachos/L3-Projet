package Engine.Core.Renderer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import Engine.Core.Controller.Controller;
import Engine.Entities.GameObject;
import Engine.Global.Settings;
import Engine.Structures.Vector2D;


public class Scene extends JPanel {

    // Scene settings
    Controller control;

    // Scene composition
    public LinkedList<GameObject> components;


    public Scene() {
        components = new LinkedList<>();
        control = new Controller(this);
        setLayout(null);
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
        add(comp);
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
        super.paintComponent(g);
    }
    
    protected void resizeComponents(Vector2D ratio) {
        for (GameObject obj : components) {
            obj.resize(ratio);
        }
    }

}