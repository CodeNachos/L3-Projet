package Onitama.src.GameScene.Interface;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Vector2D;

public class Card extends MenuFrame {

    private Vector2D originalScale;
    private Vector2D zoomMovement = new Vector2D(30,-10);

    public Card(Dimension area, Vector2D offset) {
        super(area, offset);

        setMainColor(new Color(0,0,0,0));
        setAccentColor(Color.GRAY);

        setCurvature(20, 20);
        
        originalScale = getScale();

        setOpaque(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                zoomIn();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                zoomOut();
            }
        });
        
    }

    public void zoomIn() {
        originalScale = getScale();
        setScale(getScale().multiply(1.5));
        setPos(getPos().add(zoomMovement));
    }

    public void zoomOut() {
        setScale(originalScale);
        setPos(getPos().subtract(zoomMovement));
    }


    
}
