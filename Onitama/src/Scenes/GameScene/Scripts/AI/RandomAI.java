package Onitama.src.Scenes.GameScene.Scripts.AI;

import java.util.*;

import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Scripts.States.Action;
import Onitama.src.Scenes.GameScene.Scripts.States.State;

/**
 * RandomAI
 */
public class RandomAI extends AI {
    Random random;  

    public RandomAI() {
        this.random = new Random();
    }

    @Override
    public Action play() {
        State config = GameScene.getGameState();
        List<Action> turns = possibleActions(config);
        return turns.get(random.nextInt(turns.size()));
    }
}
