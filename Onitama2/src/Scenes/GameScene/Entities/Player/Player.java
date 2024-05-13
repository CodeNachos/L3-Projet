package Onitama2.src.Scenes.GameScene.Entities.Player;


import java.util.List;

import Engine.Entities.GameObject;
import Onitama.src.Scenes.GameScene.Entities.Bots.Bot;
import Onitama2.src.Scenes.GameScene.Entities.Board.Piece;
import Onitama2.src.Scenes.GameScene.Entities.Card.Card;

public class Player extends GameObject {
    int playerId;

    Card card1, card2;
    List<Piece> pieces;
    Bot ai = null;

    public Player(int playerId) {
        this.playerId = playerId;
    }

    public Player(int playerId, Card card1, Card card2, List<Piece> pieces) {
        this.playerId = playerId;
        this.card1 = card1;
        this.card2 = card2;
        this.pieces = pieces;
    }

    public void enableAI(int difficulty) {
        ai = new Bot(playerId, difficulty);
    }

     
}

