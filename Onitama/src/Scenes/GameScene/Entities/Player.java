package Onitama.src.Scenes.GameScene.Entities;

import java.util.List;

import Engine.Entities.GameObject;
import Onitama.src.Scenes.GameScene.Entities.Bots.Bot;
import Onitama.src.Scenes.GameScene.Scripts.Card.PlayerHand;
import Onitama.src.Scenes.GameScene.Scripts.Piece.Piece;

public class Player extends GameObject {
    int playerId;

    PlayerHand hand;
    List<Piece> pieces;
    Bot ai = null;

    public Player(int playerId) {
        this.playerId = playerId;
    }

    public Player(int playerId, PlayerHand hand, List<Piece> pieces) {
        this.playerId = playerId;
        this.hand = hand;
        this.pieces = pieces;
    }

    public void enableAI(int difficulty) {
        ai = new Bot(playerId, difficulty);
    }

     
}
