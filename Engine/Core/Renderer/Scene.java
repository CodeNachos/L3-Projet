package Engine.Core.Renderer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.LinkedList;

import javax.swing.JPanel;

import Engine.Core.Controller.Controller;
import Engine.Entities.GameObject;
import Engine.Entities.TileMap.TileMap;
import Engine.Global.Settings;
import Engine.Structures.Vector2D;

/**
 * The Scene class represents a scene in the game.
 * It manages the composition of game objects within the scene and handles rendering.
 */
public class Scene extends JPanel {

    // Scene settings
    Controller control; // Controller for handling input events

    // Scene composition
    public LinkedList<GameObject> components; // List of game objects in the scene

    /**
     * Constructs a new Scene instance.
     * Initializes the scene composition and sets up the controller for input events.
     */
    public Scene() {
        components = new LinkedList<>(); // Initialize the list of components
        control = new Controller(this); // Create a new controller instance for input handling
        setLayout(null); // Set layout to null for manual component positioning
        addMouseListener(control.getMouseListener()); // Add mouse listener to handle mouse events
        addMouseMotionListener(control.getMotionListener()); // Add mouse motion listener to handle motion events
        addKeyListener(control.getKeyListener()); // Add key listener to handle key events

        // Set preferred size based on fullscreen setting
        if (Settings.fullscreen) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Get screen size
            setPreferredSize(screenSize); // Set preferred size to screen size
        } else {
            //setPreferredSize(Settings.resolution); // Set preferred size to configured resolution
        }

        setPreferredSize(Settings.resolution);
    }

    public Scene(Dimension resolution) {
        components = new LinkedList<>(); // Initialize the list of components
        control = new Controller(this); // Create a new controller instance for input handling
        setLayout(null); // Set layout to null for manual component positioning
        addMouseListener(control.getMouseListener()); // Add mouse listener to handle mouse events
        addMouseMotionListener(control.getMotionListener()); // Add mouse motion listener to handle motion events
        addKeyListener(control.getKeyListener()); // Add key listener to handle key events

        // Set preferred size based on fullscreen setting
        if (Settings.fullscreen) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Get screen size
            setPreferredSize(screenSize); // Set preferred size to screen size
        } else {
            setPreferredSize(resolution); // Set preferred size to configured resolution
        }
    }

    /**
     * Adds a game object to the scene.
     * @param comp The game object to add
     */
    public void addComponent(GameObject comp) {
        add(comp); // Add the component to the panel
        components.add(comp); // Add the component to the list of components
    }

    /**
     * Adds a game object to the scene at the specified index.
     * @param index The index at which to add the game object
     * @param comp The game object to add
     */
    public void addComponent(int index, GameObject comp) {
        components.add(index, comp); // Add the component to the list of components at the specified index
    }

    /**
     * Removes the game object at the specified index from the scene.
     * @param index The index of the game object to remove
     */
    public void removeComponent(int index) {
        components.remove(index); // Remove the component from the list of components at the specified index
    }

    /**
     * Removes the specified game object from the scene.
     * @param comp The game object to remove
     */
    public void removeComponent(GameObject comp) {
        components.remove(comp); // Remove the specified component from the list of components
    }

    /**
     * Updates scene by processing each game object.
     * 
     * @param delta The time since the last update in seconds
     */
    public void update(double delta) {
        for (GameObject obj : components) { // Iterate through all game objects in the scene
            if (obj instanceof TileMap) {
                ((TileMap)obj).update(delta);
            } else {
                obj.process(delta); // Process each game object
            }
        }
    }

    /**
     * Refreshes the scene by repainting it.
     */
    public void refresh() {
        this.repaint(); // Repaint the scene
    }

    /**
     * Overrides the paintComponent method to render the scene.
     * @param g The graphics context to render the scene
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass's paintComponent method
    }
    
    /**
     * Resizes all components in the scene based on the given resize ratio.
     * @param ratio The ratio by which to resize the components
     */
    protected void resizeComponents(Vector2D ratio) {
        for (GameObject obj : components) { // Iterate through all components in the scene
            obj.resize(ratio); // Resize each component
        }
    }
}
