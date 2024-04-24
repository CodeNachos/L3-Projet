package Engine.Core.Engines;

import Engine.Core.Renderer.GameFrame;
import Engine.Core.Renderer.Scene;
import Engine.Entities.GameObject;
import Engine.Global.Settings;

import java.awt.Dimension;

public class GameEngine implements Runnable {
    
    // Engine settings 
    private boolean running = false;

    private final int fixupdate = 60;

    double targetUpdateTime = 1000000000.0 / fixupdate;
    double targetFrameTime = 1000000000.0 / Settings.fixfps;

    private GameFrame gframe;

    // Game settings
    private Scene mainScene;
    private Scene currentScene;

    public GameEngine() {
        mainScene = currentScene = null;
        gframe = new GameFrame();
    }

    public void setMainScene(Scene scn) {
        mainScene = scn;
        if (currentScene == null) {
            currentScene = mainScene;
        }
    }

    public void setCurrentScene(Scene scn) {
        // loaded on next frame
        currentScene = scn;
        gframe.setScene(currentScene);
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setResolution(Dimension res) {
        Settings.resolution = res;
    }

    public Dimension getResolution() {
        return Settings.resolution;
    }

    public void start() {
        running = true;
        new Thread(this).start();
        gframe.start();
    }

    @Override
    public void run() {
        long previousTime = System.nanoTime();

        int fps = 0; // frame per second counter
        int ups = 0; // updates per second counter

        long lastCheck = System.currentTimeMillis();

        double deltaFrame = 0;
        double deltaUpdate = 0;

        while(running) {
            long currentTime = System.nanoTime();

            deltaUpdate += (currentTime - previousTime) / targetUpdateTime;
            deltaFrame += (currentTime - previousTime) / targetFrameTime;
            previousTime = currentTime;

            if (deltaUpdate >= 1) { // update time interval occured
                updateScene();
                ups++;
                deltaUpdate--;
            }

            if (deltaFrame >= 1) {
                gframe.refresh();
                fps++;
                deltaFrame--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + fps + " | UPS : " + ups);
                fps = 0;
                ups = 0;
            }
        }

        System.exit(0);
    }

    public void stop() {
        System.exit(0);
    }

    public void pause() {
        running = false;
    }

    private void updateScene() {
        for (GameObject obj : currentScene.components) {
            obj.process();
        }
    }
}
