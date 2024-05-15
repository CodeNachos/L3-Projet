package Old.src.Scenes.GameScene.Scripts;

import java.io.Serializable;

import Engine.Structures.Vector2D;
import Old.src.Scenes.GameScene.Scripts.Card.CardInfo;
import Old.src.Scenes.GameScene.Scripts.Piece.Piece;

//Turn = card they'll play + piece they'll move + actual movement

public class Turn implements Serializable {
    CardInfo playCard;
    Piece piece;
    Vector2D move;

    public Turn(CardInfo playCard, Piece piece, Vector2D move) {
        this.playCard = playCard;
        this.piece = piece;
        this.move = move;
    }

    public CardInfo getCard() {
        return playCard;
    }

    public Piece getPiece() {
        return piece;
    }

    public Vector2D getMove() {
        return move;
    }

    //Other things... (Setters -> change something in your turn?)
}
