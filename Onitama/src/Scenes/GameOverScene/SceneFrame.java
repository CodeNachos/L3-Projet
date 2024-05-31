package Onitama.src.Scenes.GameOverScene;

import java.awt.Dimension;

import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;

public class SceneFrame extends MenuFrame {

    Scene scene;

    public SceneFrame(Scene scene, Dimension area, Vector2D offset) {
        super(area, offset);
        this.scene = scene;
        add(this.scene);

    }

    @Override
    public void process(double delta) {
        if (scene != null) {
            scene.update(delta);
            scene.refresh();
        }
    }
    
}
