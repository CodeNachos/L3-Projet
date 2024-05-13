package Onitama.src.Scenes.GameScene.Scripts.AI;

import java.util.ArrayList;
import java.util.List;

import Engine.Structures.Vector2D;
import Onitama.src.Scenes.GameScene.Scripts.GameConfiguration;
import Onitama.src.Scenes.GameScene.Scripts.Turn;
import Onitama.src.Scenes.GameScene.Scripts.Card.CardInfo;
import Onitama.src.Scenes.GameScene.Scripts.Piece.Piece;

/**
 * AI
 */
public abstract class AI {
    public abstract Turn play();

    List<Turn> possibleTurns(GameConfiguration config) {
        List<Turn> result = new ArrayList<>();
        for (CardInfo card : config.availableCards())
            for (Piece piece : config.allyPieces())
                for (Vector2D position : config.possiblePositions(piece.getPosition(), card))
                    result.add(new Turn(card, piece, position));
        return result;
    }
}
