package WaffleGame.src.Scenes.GameScene.Entities.AI;

import java.util.Random;

import Engine.Structures.Vector2D;
import WaffleGame.src.Scenes.GameScene.GameScene;

public class RandomAI {

    public static Vector2D getAction() {
        Vector2D action =  new Vector2D();
        Random random = new Random();
        int l, c;
        do {
            l = random.nextInt(GameScene.map.mapDimension.height);
            c = random.nextInt(GameScene.map.mapDimension.width);
        } while (GameScene.map.gridmap[l][c] == null); // Repeat if the numbers are equal
        
        action.setCoord(l, c);
        System.out.println(action);

        return action;
    }
}
