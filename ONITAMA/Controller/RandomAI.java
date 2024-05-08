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

        List<Position> pawns = config.allyPawns();
        Position chosenPawn = pawns.get(random.nextInt(pawns.size()));

        List<Position> moves = config.possiblePositions(chosenPawn, chosenCard);
        Position chosenMove = moves.get(random.nextInt(moves.size()));

        return new Turn(chosenCard, chosenPawn, chosenMove);
    }
}