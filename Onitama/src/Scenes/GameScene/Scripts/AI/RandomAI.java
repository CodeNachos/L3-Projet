package Onitama.src.Scenes.GameScene.Scripts.AI;

import java.util.*;

import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Scripts.GameConfiguration;
import Onitama.src.Scenes.GameScene.Scripts.Turn;

/**
 * RandomAI
 */
public class RandomAI extends AI {
    Random random;  

    public RandomAI() {
        this.random = new Random();
    }

    @Override
    public Turn play() {
        GameConfiguration config = GameScene.game;
        List<Turn> turns = possibleTurns(config);
        return turns.get(random.nextInt(turns.size()));
    }
}
