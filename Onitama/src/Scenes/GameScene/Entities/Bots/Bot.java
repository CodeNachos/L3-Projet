package Onitama.src.Scenes.GameScene.Entities.Bots;

import Engine.Entities.GameObject;
import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Scripts.Turn;
import Onitama.src.Scenes.GameScene.Scripts.AI.AI;
import Onitama.src.Scenes.GameScene.Scripts.AI.SmartAI;

public class Bot extends GameObject {
    AI bot;
    Turn botTurn;
    int counter;
    int player;

    public Bot(int player) {
        this.player = player;
        bot = new SmartAI(5, this.player);
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

        if (GameScene.game.getCurrentPlayer() == this.player) {
            botTurn = bot.play();
            GameScene.game.setSelectedPiece(botTurn.getPiece().getPosition());
            GameScene.game.setSelectedCard(botTurn.getCard().getName());
            GameScene.game.setSelectedAction(botTurn.getMove());
            counter = 60;
            return;
            
        } else {
            botTurn = null;
        }
    }
}
