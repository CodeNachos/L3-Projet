package Onitama.src.Scenes.GameOverScene;

import java.awt.Dimension;

import Engine.Core.Renderer.Scene;
import Engine.Entities.UI.ColorArea;
import Engine.Structures.Vector2D;
import Onitama.src.Main;

public class GameOverScene extends Scene {
    SceneFrame gameSceneFrame;
    public GameOverScene() {

        createGameSceneFrame();

        // Add background
        ColorArea background = new ColorArea(Main.Palette.background, new Dimension(Main.engine.getResolution().width, Main.engine.getResolution().height));
        addComponent(background);
    }

    public void createGameSceneFrame() {
        Dimension sceneDim = new Dimension(
           (int)(Main.gameScene.getWidth() * 0.9),
            (int)(Main.gameScene.getHeight() * 0.9)
        );

        Vector2D sceneOffset = new Vector2D(
            (getWidth()/2),
            (getHeight()/2) - (sceneDim.height/2)
        );

        gameSceneFrame = new SceneFrame(Main.gameScene, sceneDim, new Vector2D());
        addComponent(gameSceneFrame);
    }
}
