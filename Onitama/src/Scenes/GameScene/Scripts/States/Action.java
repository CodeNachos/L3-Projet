package Onitama.src.Scenes.GameScene.Scripts.States;

import java.io.Serializable;

import Engine.Structures.Vector2D;

//Turn = card they'll play + piece they'll move + actual movement

public class Action implements Serializable {
    String playCard;
    Vector2D piece;
    Vector2D move;

    public Action(String card, Vector2D piece, Vector2D move) {
        this.playCard = card;
        this.piece = piece;
        this.move = move;
    }

    public String getCard() {
        return playCard;
    }

    public Vector2D getPiece() {
        return piece;
    }

    public Vector2D getMove() {
        return move;
    }
}
