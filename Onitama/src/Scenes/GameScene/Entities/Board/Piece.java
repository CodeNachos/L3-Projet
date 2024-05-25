package Onitama.src.Scenes.GameScene.Entities.Board;

import java.awt.event.MouseEvent;

import Engine.Entities.TileMap.Tile;
import Engine.Structures.Sprite;
import Engine.Structures.Vector2D;
import Onitama.src.Main;
import Onitama.src.Scenes.GameScene.GameScene;

public class Piece extends Tile {

    public static enum PieceType {
        RED_KING,
        RED_PAWN,
        BLUE_KING,
        BLUE_PAWN,
        EMPTY
    };

    PieceType type;

    boolean interactable;

    Vector2D[] animation = null;
    int animationStep = 0;
    double timeCounter = 0.;

    public Piece(PieceMap map, PieceType type, Vector2D position, Sprite sprite) {
        super(map, position.getIntY(), position.getIntX(), sprite);
        this.type = type;
    }

    public PieceType getType() {
        return type;
    }

    public boolean isBlue() {
        return type == PieceType.BLUE_KING || type == PieceType.BLUE_PAWN;
    }

    public boolean isRed() {
        return type == PieceType.RED_KING || type == PieceType.RED_PAWN;
    }
    
    public Vector2D getPosition() {
        return mapPosition;
    }

    public void setInteractable(boolean state) {
        interactable = state;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        
        if (! (o instanceof Piece))
            return false;
        
        Piece p = (Piece) o;
        return (p.getType() == this.getType() && p.getPosition().equals(this.getPosition()));

    }

    @Override
    public Piece clone() {
        Piece clone = new Piece((PieceMap)parentMap, type, position, sprite);
        return clone;
    }

    @Override
    public void input(MouseEvent e) {
        if (!interactable) 
            return;
        
        if (e.getID() == MouseEvent.MOUSE_CLICKED) {
            if (
                (Main.gameScene.getCurrentPlayer() == GameScene.PLAYER1 && isBlue()) ||
                (Main.gameScene.getCurrentPlayer() == GameScene.PLAYER2 && isRed())
            ) {
                animation = animations[0];
                timeCounter = 0.02;
            }
        }
    }

    @Override
    public void process(double delta) {
        if (animation != null) {
            if (timeCounter <= 0) {
                setPos(getPos().add(animation[animationStep]));
                animationStep++;
                if (animationStep == animation.length) {
                    animation = null;
                    animationStep = 0;
                } else {
                    timeCounter = 0.02;
                }
            } else {
                timeCounter -= delta;
            }
        }
    }

    private Vector2D[] invalidAnimation = {
        new Vector2D(-2, 0),
        new Vector2D(4, 0),
        new Vector2D(-2,0)
    };

    private Vector2D[][] animations = {invalidAnimation};
}
