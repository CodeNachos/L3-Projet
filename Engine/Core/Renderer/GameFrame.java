package Engine.Core.Renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Engine.Global.Settings;
import Engine.Global.Util;
import Engine.Structures.Vector2D;

/**
 * The GameFrame class represents the main window frame of the game.
 * It manages the rendering settings, scene transitions, and frame resizing.
 */
public class GameFrame extends JFrame {

    // Renderer Settings
    private boolean fullscreen = Settings.fullscreen; // Indicates whether the game is running in fullscreen mode
    private Scene scene; // The current active scene

    /**
     * Constructs a new GameFrame instance.
     * Initializes the JFrame and sets up event listeners for resizing.
     */
    public GameFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        setMinimumSize(new Dimension(100,100));
        setTitle(Settings.applicationName); // Set window title
        setResizable(Settings.resizable); // Set window resizable according to settings
        getContentPane().setBackground(Color.BLACK);
    }

    /**
     * Sets the current active scene of the game.
     * @param scn The scene to set as current
     */
    public void setScene(Scene scn) {
        if (scn == null) {
            Util.printError("null reference to scene");
        }
        this.scene = scn; // Set the current scene
        getContentPane().removeAll(); // Remove all components from the content pane
        getContentPane().add(scene, BorderLayout.CENTER); // Add the scene to the center of the content pane
        scene.setFocusable(true); // Set the scene focusable
        scene.requestFocusInWindow(); // Request focus for the scene
        revalidate(); // Revalidate the frame
    }

    /**
     * Toggles fullscreen mode of the game window.
     * If fullscreen is enabled, switches to windowed mode; otherwise, switches to fullscreen.
     */
    public void toggleFullscreen() {
        if (!Settings.resizable)
            return;

        if (this.fullscreen) { // If currently in fullscreen mode
            dispose(); // Dispose the frame
            setExtendedState(JFrame.NORMAL); // Set extended state to normal
            setUndecorated(false); // Set undecorated mode to false
            //setPreferredSize(Settings.resolution); // Set preferred size to default resolution
            pack(); // Pack the frame
            setVisible(true); // Set visibility to true

        } else { // If currently in windowed mode
            dispose(); // Dispose the frame
            setExtendedState(JFrame.MAXIMIZED_BOTH); // Set extended state to maximized
            setUndecorated(true); // Set undecorated mode to true
            setVisible(true); // Set visibility to true
        }
    }

    /**
     * Starts rendering the game.
     * If fullscreen mode is enabled, sets the frame to fullscreen; otherwise, sets it to windowed mode.
     */
    public void start() {
        if (scene == null) {
            Util.printError("Scene not set. Please set the scene before start rendering.");
            return;
        }
        if (fullscreen) {
            setExtendedState(JFrame.MAXIMIZED_BOTH); // Set extended state to maximized
            setUndecorated(true); // Set undecorated mode to true
        } else {
            //setPreferredSize(Settings.resolution); // Set preferred size to default resolution
            pack(); // Pack the frame
        }

        // Add component listener for resizing events
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(() -> {
                    if (Settings.stretch) { 
                        JFrame frame = (JFrame) e.getComponent();
                        Vector2D resizeRatio = new Vector2D(
                            (double) frame.getContentPane().getSize().width / (double) Settings.resolution.width,
                            (double) frame.getContentPane().getSize().height / (double) Settings.resolution.height
                        );
                        resizeScene(resizeRatio); // Resize the scene components
                    }
                    Settings.resolution = ((JFrame) e.getComponent()).getContentPane().getSize(); // Update screen resolution in settings
                });
            }
        });

        setVisible(true); // Set visibility to true
    }

    /**
     * Refreshes the game window by updating the scene.
     */
    public void refresh() {
        if (scene == null) {
            Util.printError("Scene not set. Please set the scene before refreshing.");
            return;
        }
        if (Settings.fullscreen != this.fullscreen) {
            toggleFullscreen(); // Toggle fullscreen mode if settings have changed
            this.fullscreen = Settings.fullscreen; // Update fullscreen mode
        }
        //scene.requestFocusInWindow(); // Request focus for the scene
        scene.refresh(); // Refresh the scene
    }

    /**
     * Resizes the scene components based on the given resize ratio.
     * @param ratio The ratio by which to resize the scene components
     */
    private void resizeScene(Vector2D ratio) {
        scene.resizeComponents(ratio); // Resize the scene components
    }
}
