package Onitama.src.Scenes.GameScene.Scripts.AI;

import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Scripts.States.Action;
import Onitama.src.Scenes.GameScene.Scripts.States.State;

/**
 * Match
 * simulates game for ai training
 */
public class Match {
    SmartAI red, blue, winner;
    State game;
    boolean captured, conquered;
    int length;

    Match(SmartAI red, SmartAI blue) {
        this.red = red;
        this.blue = blue;
        this.red.selfID = GameScene.PLAYER1;
        this.blue.selfID = GameScene.PLAYER2;
    }

    void fight() {
        State game = (new GameScene()).getGameState();
        Action turn;

        length = 0;
        while (!game.isGameOver() && length < 100) {
            if (game.getCurrentPlayer() == GameScene.PLAYER1)
                turn = red.play(game);
            else
                turn = blue.play(game);
            game = game.nextConfig(turn);
            length++;
        }

        if (length > 9000)
            winner = null;
        else if (game.getNextPlayer() == GameScene.PLAYER1)
            winner = red;
        else
            winner = blue;

        if (game.isKingCaptured()) {
            captured = true;
            conquered = false;
        } else {
            captured = false;
            conquered = true;
        }
    }
}