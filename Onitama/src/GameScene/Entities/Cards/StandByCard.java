package Onitama.src.GameScene.Entities.Cards;


import java.awt.event.MouseEvent;

import Engine.Structures.Sprite;
import Engine.Structures.Vector2D;

public class StandByCard extends Card {

    public StandByCard(Vector2D position, Sprite sprite) {
        super(position, sprite);
    }

    @Override
    public void input(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_ENTERED) {
            zoomIn();
        } else if (e.getID() == MouseEvent.MOUSE_EXITED) {
            zoomOut();
        }
    }


    
    
}
