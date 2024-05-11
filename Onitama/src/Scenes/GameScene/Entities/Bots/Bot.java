package Onitama.src.Scenes.GameScene.Entities.Bots;

import Engine.Entities.GameObject;
import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Scripts.GameConfiguration;
import Onitama.src.Scenes.GameScene.Scripts.Turn;
import Onitama.src.Scenes.GameScene.Scripts.Bots.Player;
import Onitama.src.Scenes.GameScene.Scripts.Bots.RandomAI;

public class Bot extends GameObject {
    Player bot;
    Turn botTurn;
    int counter;

    public Bot() {
        bot = new RandomAI();
    }

    @Override
    public void process(double delta) {
        if (botTurn != null) {
            if (counter > 0) {
                counter--;
                return;
            }
            GameScene.updateMatch();
            botTurn = null;
        }

        if (GameScene.game.getCurrentPlayer() == GameConfiguration.PLAYER2) {
            botTurn = bot.play();
            GameScene.game.setSelectedPiece(botTurn.getPiece().getPosition());
            GameScene.game.setSelectedCard(botTurn.getCard().getName());
            GameScene.game.setSelectedAction(botTurn.getMove());
            counter = 25;
            return;
            
        } else {
            botTurn = null;
        }
    }
}
