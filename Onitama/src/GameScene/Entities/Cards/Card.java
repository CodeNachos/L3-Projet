package Onitama.src.GameScene.Entities.Cards;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Engine.Entities.GameObject;
import Engine.Structures.Sprite;
import Engine.Structures.Vector2D;
import Onitama.src.Main;

public class Card extends GameObject {

    private Vector2D originalScale;
    private double zoomFactor = 1.1;
    private Vector2D zoomMovement = new Vector2D();

    private boolean selected = false;

    public Card(Vector2D position, Sprite sprite) {
        super(position, sprite);
        
        zoomMovement = new Vector2D(
            (1-zoomFactor)*getSize().width/2,
            (1-zoomFactor)*getSize().height/2
        );

        originalScale = getScale();

        
        
    }

    public void zoomIn() {
        originalScale = getScale();
        setScale(getScale().multiply(zoomFactor));
        setPos(getPos().add(zoomMovement));
    }

    public void zoomOut() {
        setScale(originalScale);
        setPos(getPos().subtract(zoomMovement));
    }

    @Override
    public void process(double delta) {
        if (selected && sprite != Main.selectedCardSprite) {
            sprite = Main.selectedCardSprite;
        } else if (!selected && sprite != Main.idleCardSprite) {
            sprite = Main.idleCardSprite;
        }
    }

    @Override
    public void input(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_CLICKED) {
            if (selected) {
                selected = false;
            } else {
                selected = true;
            }
        } else if (e.getID() == MouseEvent.MOUSE_ENTERED) {
            zoomIn();
        } else if (e.getID() == MouseEvent.MOUSE_EXITED) {
            zoomOut();
        }
    }
}
