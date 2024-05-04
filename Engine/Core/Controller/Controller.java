package Engine.Core.Controller;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Engine.Core.Renderer.Scene;

public class Controller {
    // Controller scene
    protected Scene scene;

    // Adapters
    MouseAdapter mouseAdapter;
    MotionAdapter motionAdapter;
    KeyboardAdapter keyAdapter;


    public Controller(Scene scn) {
        this.scene = scn;

        mouseAdapter = new MouseAdapter(this);
        motionAdapter = new MotionAdapter(this);
        keyAdapter = new KeyboardAdapter(this);
    }

    public MouseListener getMouseListener() {
        return mouseAdapter;
    }

    public MouseMotionListener getMotionListener() {
        return motionAdapter;
    }

    public KeyListener getKeyListener() {
        return keyAdapter;
    }
    
}