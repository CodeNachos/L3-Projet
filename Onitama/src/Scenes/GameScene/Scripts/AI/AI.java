package Onitama.src.Scenes.GameScene.Scripts.AI;

import java.util.ArrayList;
import java.util.List;

import Engine.Structures.Vector2D;
import Onitama.src.Scenes.GameScene.Scripts.States.Action;
import Onitama.src.Scenes.GameScene.Scripts.States.State;

/**
 * AI
 */
public abstract class AI {
    public abstract Action play();

    List<Action> possibleActions(State config) {
        List<Action> result = new ArrayList<>();
        for (String card : config.availableCards())
            for (Vector2D piece : config.allyPositions())
                for (Vector2D position : config.possiblePositions(piece, card))
                    result.add(new Action(card, piece, position));
        return result;
    }
}
