package Engine.Core.Renderer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.Queue;

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

    // Queued objects to be added
    private Queue<GameObject> addQueue; 
    // Qeueud objects to be removed
    private Queue<GameObject> rmvQueue;

    /**
     * Constructs a new Scene instance.
     * Initializes the scene composition and sets up the controller for input events.
     */
    public Scene() {
        addQueue = new LinkedList<>();  // Initialize the add queue
        rmvQueue = new LinkedList<>();  // Initialize the remove queue
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
        addQueue.add(comp); // Add component to queue to be added on next update
    }

    /**
     * Removes the specified game object from the scene.
     * @param comp The game object to remove
     */
    public void removeComponent(GameObject comp) {
        rmvQueue.add(comp); // Add component to queue to be removed on next update
    }

    /**
     * Updates scene by processing each game object.
     * 
     * @param delta The time since the last update in seconds
     */
    public void update(double delta) {
        if (!rmvQueue.isEmpty()) {
            for (GameObject comp : rmvQueue) {
                components.remove(comp); // Remove the specified component from the list of components
                remove(comp);   // Effectively removes from JPanel
            }
            rmvQueue.clear();
            revalidate();
        }

        if (!addQueue.isEmpty()) {
            for (GameObject comp : addQueue) {
                add(comp); // Add the component to the panel
                components.add(comp); // Add the component to the list of components
            }
            addQueue.clear();
        }

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
