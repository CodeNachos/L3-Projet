package Onitama.src.Scenes.GameScene.Scripts.AI;

import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Scripts.States.Action;
import Onitama.src.Scenes.GameScene.Scripts.States.State;
import Onitama.src.Main;

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
        this.red.selfID = GameScene.RED_PLAYER;
        this.blue.selfID = GameScene.BLUE_PLAYER;
    }

    void fight() {
        State game = Main.gameScene.getGameState();
        Action turn;

        length = 0;
        while (!game.isGameOver() && length < 100) {
            if (game.getCurrentPlayer() == GameScene.RED_PLAYER)
                turn = red.play(game);
            else
                turn = blue.play(game);
            game = game.nextConfig(turn);
            length++;
        }

        if (length > 9000)
            winner = null;
        else if (game.getNextPlayer() == GameScene.RED_PLAYER)
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