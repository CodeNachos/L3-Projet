package Engine.Core.Engines;

import Engine.Core.Renderer.GameFrame;
import Engine.Core.Renderer.Scene;
import Engine.Global.Settings;
import Engine.Global.Util;

import java.awt.Dimension;
import java.awt.Image;

import javax.naming.OperationNotSupportedException;

/**
 * The GameEngine class represents the core engine of the game.
 * It manages the game loop, scene transitions, and updates.
 */
public class GameEngine implements Runnable {

    // Engine settings
    private boolean running = false; // Indicates whether the game loop is running or not

    private final int fixupdate = 60; // Number of updates per second

    double targetUpdateTime = 1000000000.0 / fixupdate; // Target time for one update (in millis)
    double targetFrameTime = 1000000000.0 / Settings.fixfps; // Target time for one frame (in millis)

    // FPS and UPS values
    int lastfps;
    int lastups;

    // Game frame (window)
    private GameFrame gframe; // Instance of the game frame

    // Game settings
    private Scene mainScene; // The main scene
    private Scene currentScene; // The currently active scene

    /**
     * Constructs a new GameEngine instance.
     */
    public GameEngine() {
        mainScene = currentScene = null; // Initialize scenes as null
        gframe = new GameFrame(); // Create a new game frame
        gframe.setSize(Settings.resolution);
    }

    /**
     * Sets the main scene of the game.
     * @param scn The main scene to set
     */
    public void setMainScene(Scene scn) {
        mainScene = scn; // Set the main scene
        if (currentScene == null) { // If no current scene is set, set it to the main scene
            currentScene = mainScene;
        }
    }

    /**
     * Sets the current active scene of the game.
     * @param scn The scene to set as current
     */
    public void setCurrentScene(Scene scn) {
        // loaded on next frame
        currentScene = scn; // Set the current scene
        gframe.setScene(currentScene); // Set the scene in the game frame
    }

    /**
     * Gets the current active scene of the game.
     * @return The current active scene
     */
    public Scene getCurrentScene() {
        return currentScene; // Get the current scene
    } 

    /**
     * Sets the resolution of the game window.
     * @param res The resolution to set
     */
    public void setResolution(Dimension res) {
        gframe.getContentPane().setSize(res);; // Set the screen resolution in the settings
    }

    /**
     * Gets the resolution of the game window.
     * @return The resolution of the game window
     */
    public Dimension getResolution() {
        return gframe.getContentPane().getSize(); // Get the window resolution from the settings
    }

    /**
     * Starts the game engine and the game loop.
     * @throws OperationNotSupportedException 
     */
    public void start() {
        if (currentScene == null && mainScene == null) {
            Util.printError("Scene not set. Please set the scene before starting the engine");
            stop();
        }

        // set scene
        if (mainScene != null) {
            setCurrentScene(mainScene);
        }

        running = true; // Start the game loop
        new Thread(this).start(); // Start a new thread for the game loop
        gframe.start(); // Start the game frame
    }

    @Override
    public void run() {

        long previousTime = System.nanoTime(); // Get the current time in nanoseconds

        int fps = 0; // Initialize the frames per second counter
        int ups = 0; // Initialize the updates per second counter

        long lastCheck = System.currentTimeMillis(); // Get the current time in milliseconds

        double deltaFrame = 0; // Initialize the time since the last frame
        double deltaUpdate = 0; // Initialize the time since the last update

        while(running) { // Main game loop
            long currentTime = System.nanoTime(); // Get the current time in nanoseconds

            double deltaTime = currentTime - previousTime;

            deltaUpdate += deltaTime / targetUpdateTime; // Calculate time since the last update
            deltaFrame += deltaTime / targetFrameTime; // Calculate time since the last frame
            previousTime = currentTime; // Update previous time

            if (deltaUpdate >= 1) { // If it's time for an update
                updateScene((deltaTime / fixupdate) / 100); // Update the scene
                ups++; // Increment updates per second counter
                deltaUpdate--; // Reset the update timer
            }

            if (deltaFrame >= 1) { // If it's time for a frame
                gframe.refresh(); // Refresh the game frame
                fps++; // Increment frames per second counter
                deltaFrame--; // Reset the frame timer
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) { // If one second has passed
                lastCheck = System.currentTimeMillis(); // Update last check time

                //System.out.println("FPS: " + fps + " | UPS : " + ups);
                lastfps = fps;
                lastups = ups;
                fps = 0; // Reset frames per second counter
                ups = 0; // Reset updates per second counter
            }
        }
    }

    /**
     * Stops the game engine and exits the program.
     */
    public void stop() {
        System.exit(0); // Stop the program
    }

    /**
     * Pauses the game loop.
     */
    public void pause() {
        running = false; // Stop the game loop
    }

    /**
     * Continues the game loop after paused.
     */
    public void resume() {
        running = true; // Continue the game loop
        run();

    }

    /**
     * 
     * @return number of frames per second
     */
    public int getFPS() {
        return lastfps;
    }

    /**
     * 
     * @return number of updates per second
     */
    public int getUPS() {
        return lastups;
    }
    
    /**
    * Sets the icon for the application window.
    * 
    * @param appicon the Image object representing the icon
    */
    public void setIcon(Image appicon) {
        gframe.setIconImage(appicon);
    }

    /**
     * Forces frame refresh
     */
    public void forceRefresh() {
        gframe.refresh();
    }

    /**
     * Forces scene update
     */
    public void forceUpdate() {
        updateScene(0);
    }

    /**
     * Updates the current scene by processing each game object.
     * 
     * @param delta The time since the last update in seconds
     */
    private void updateScene(double delta) {
        currentScene.update(delta);
    }
}
