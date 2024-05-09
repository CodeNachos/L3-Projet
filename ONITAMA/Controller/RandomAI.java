package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Model.*;

/**
 * RandomAI
 */
public class RandomAI implements Player {
    Random random;
    Engine engine;

    public RandomAI(Engine engine) {
        this.random = new Random();
        this.engine = engine;
    }

    @Override
    public Turn play() {
        GameConfiguration config = engine.getGameConfiguration();

        List<Turn> turns = possibleTurns(config);

        return turns.get(random.nextInt(turns.size()));
    }

    private List<Turn> possibleTurns(GameConfiguration config) {
        List<Turn> result = new ArrayList<>();
        for (Card card : config.availableCards())
            for (Piece piece : config.allyPieces())
                for (Position position : config.possiblePositions(piece.getPosition(), card)) {
                    Piece move = new Piece(piece.getType(), position);
                    result.add(new Turn(card, piece, move));
                }
        return result;
    }
}