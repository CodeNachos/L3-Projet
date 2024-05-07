package BOT;

import ENGINE.GameConfiguration;
import ENGINE.Turn;

/**
 * AI
 */
public interface AI {
    public Turn play(GameConfiguration current);
}