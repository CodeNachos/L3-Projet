package Onitama.src.GameScene.Entities.Cards;

import java.awt.event.MouseEvent;

import Engine.Entities.GameObject;
import Engine.Structures.Sprite;
import Engine.Structures.Vector2D;

public class Card extends GameObject {
    String name;
    PlayerHand hand = null;

    // Visual settings
    private Vector2D originalScale;
    private double zoomFactor = 1.1;
    private Vector2D zoomMovement = new Vector2D(0,0);

    public Card(String name, Vector2D position, Sprite sprite) {
        super(position, sprite);
        
        zoomMovement = new Vector2D(
            (1-zoomFactor)*getSize().width/2,
            (1-zoomFactor)*getSize().height/2
        );

        originalScale = getScale();
        
    }

    public Card(String name, Vector2D position, Sprite sprite, PlayerHand hand) {
        super(position, sprite);
        
        zoomMovement = new Vector2D(
            (1-zoomFactor)*getSize().width/2,
            (1-zoomFactor)*getSize().height/2
        );

        originalScale = getScale();
        
        this.hand = hand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHand(PlayerHand hand) {
        this.hand = hand;
    }

    public PlayerHand getHand() {
        return hand;
    }

    private void zoomIn() {
        originalScale = getScale();
        setScale(getScale().multiply(zoomFactor));
        setPos(getPos().add(zoomMovement));
    }

    private void zoomOut() {
        setScale(originalScale);
        setPos(getPos().subtract(zoomMovement));
    }

    @Override
    public void input(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_ENTERED) {
            zoomIn();
        } else if (e.getID() == MouseEvent.MOUSE_EXITED) {
            zoomOut();
        } else if (e.getID() == MouseEvent.MOUSE_CLICKED && hand != null) {
            hand.setSelectedCard(this);
        }
    }
}
