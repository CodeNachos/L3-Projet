package WaffleGame.src;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.text.Position;

import Engine.Core.Engines.GameEngine;
import Engine.Core.Renderer.Scene;
import Engine.Entities.GameObject;
import Engine.Entities.UI.ColorBackground;
import Engine.Global.Settings;
import Engine.Structures.Vector2D;

public class WaffleGame {
    public static void main(String args[]) {
        
        GameEngine engine = new GameEngine();
        engine.setResolution(new Dimension(600,600));
        Scene mainScene = new Scene();

        Image waffleSprite = getImage("waffleTile.png");
        Image image = getImage("images.jpg");

        Vector2D tileMapOffset = new Vector2D(16,16);
        Dimension tileMapArea = new Dimension(
            Settings.resolution.width - 2*(int)tileMapOffset.x,
            Settings.resolution.height - 2*(int)tileMapOffset.y
        );

        WaffleTileMap map = new WaffleTileMap(4, 4, tileMapArea, tileMapOffset);
        map.populateWaffle(waffleSprite);
        
        ColorBackground background = new ColorBackground(new Color(108, 39, 8, 255), null);
        mainScene.addComponent(background);
        mainScene.addComponent(map);

        engine.setCurrentScene(mainScene);
        engine.start();
        
    }

    public static InputStream open(String s) {
		InputStream in = null;
		try {
			in = new FileInputStream("WaffleGame/res/" + s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        return in;
    }
		

    private static Image getImage(String path) {
        InputStream in = open(path);
        try {
            // Chargement d'une image utilisable dans Swing
            return ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
