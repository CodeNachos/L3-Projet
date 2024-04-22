package Engine.Core.Renderer;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import Engine.Global.Settings;
import Engine.Structures.Vector2D;

public class GameFrame extends JFrame {
    // Renderer Settings
    private boolean fullscreen = Settings.fullscreen;
    private Scene scene;
    
    public GameFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout()); 
        setTitle(Settings.applicationName);
        setResizable(Settings.resizable);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // This method is called when the JFrame is resized
                if (Settings.stretch) {
                    Vector2D resizeRatio = new Vector2D(
                        (double)(e.getComponent().getSize().width)/(double)(Settings.resolution.width),
                        (double)(e.getComponent().getSize().height)/(double)(Settings.resolution.height)
                    );
                    resizeScene(resizeRatio);
                }
                Settings.resolution = e.getComponent().getSize();
            }
        });
    }

    public void setScene(Scene scn) {
        this.scene = scn;
        getContentPane().removeAll();
        getContentPane().add(scene, BorderLayout.CENTER); 
        scene.setFocusable(true);
        scene.requestFocusInWindow();
        revalidate();
    }

    public void toggleFullscreen() {
        if (this.fullscreen) {
            dispose();
            setExtendedState(JFrame.NORMAL);
            setUndecorated(false);
            setPreferredSize(Settings.resolution);
            pack();
            setVisible(true);
            
        } else {
            dispose();
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setUndecorated(true);
            setVisible(true);
        }
        this.fullscreen = Settings.fullscreen;
    }

    public void start() {
        if (scene == null) {
            System.err.println("Scene not set. Please set the scene before starting rendering.");
            return;
        }
        if (fullscreen) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setUndecorated(true);
        } else {
            setPreferredSize(Settings.resolution);
            pack();
        }

        setVisible(true);
    }

    public void refresh() {
        if (scene == null) {
            System.err.println("Scene not set. Please set the scene before refreshing.");
            return;
        }
        if (Settings.fullscreen != this.fullscreen) {
            toggleFullscreen();
        }
        scene.requestFocusInWindow();
        scene.refresh();
    }

    private void resizeScene(Vector2D ratio) {
        scene.resizeComponents(ratio);
    }
    
}