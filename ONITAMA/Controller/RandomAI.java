package Controller;

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

        List<Card> cards = config.availableCards();
        Card chosenCard = cards.get(random.nextInt(cards.size()));

        List<Piece> pawns = config.allyPieces();
        Piece chosenPawn = pawns.get(random.nextInt(pawns.size()));

        List<Position> positions = config.possiblePositions(chosenPawn.getPosition(), chosenCard);
        Position chosenPosition = positions.get(random.nextInt(positions.size()));
        Piece chosenMove = new Piece(chosenPawn.getType(), chosenPosition);

        return new Turn(chosenCard, chosenPawn, chosenMove);
    }
}