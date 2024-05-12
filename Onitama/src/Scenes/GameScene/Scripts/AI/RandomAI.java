package Onitama.src.Scenes.GameScene.Scripts.AI;

import java.util.*;

import Engine.Structures.Vector2D;
import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Scripts.GameConfiguration;
import Onitama.src.Scenes.GameScene.Scripts.Turn;
import Onitama.src.Scenes.GameScene.Scripts.Card.CardInfo;
import Onitama.src.Scenes.GameScene.Scripts.Piece.Piece;

/**
 * RandomAI
 */
public class RandomAI implements Player {
    Random random;  

    public RandomAI() {
        this.random = new Random();
    }
    public Turn play() {
        GameConfiguration config = GameScene.game;

        List<Turn> turns = possibleTurns(config);

        return turns.get(random.nextInt(turns.size()));
    }

    private List<Turn> possibleTurns(GameConfiguration config) {
        List<Turn> result = new ArrayList<>();
        for (CardInfo card : config.availableCards())
            for (Piece piece : config.allyPieces())
                for (Vector2D position : config.possiblePositions(piece.getPosition(), card)) {
                    result.add(new Turn(card, piece, position));
                }
        return result;
    }
}
