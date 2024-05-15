package Onitama.src.Scenes.GameScene.Entities.Card;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import Engine.Core.Renderer.Scene;
import Engine.Entities.GameObject;
import Engine.Entities.UI.MenuFrame;
import Engine.Structures.Sprite;
import Engine.Structures.Vector2D;
import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Main;
import Onitama.src.Scenes.GameScene.Entities.Player.Player;

public class Card extends GameObject {
    Player player;
    
    String name;
    
    CardMap cardMap;
    MenuFrame cardLabel;

    // Visual settings
    private Vector2D originalScale;
    private double zoomFactor = 1.1;
    private Vector2D zoomMovement = new Vector2D(0,0);

    private boolean standBy = false;

    private boolean interactable = true;


    public Card(String name, Vector2D position, Sprite sprite, Player player) {
        super(position, sprite);

        this.player = player;

        zoomMovement = new Vector2D(
            (1-zoomFactor)*getSize().width/2,
            (1-zoomFactor)*getSize().height/2
        );

        originalScale = getScale().clone();
        
        this.name = name;

        createCardMap();
        createCardLabel();

        updateCard();
    }

    public void setName(String name) {
        this.name = name;
        ((JLabel)cardLabel.getComponent(0)).setText(name);
        updateCard();
    }

    public void setinteractable(boolean state) {
        this.interactable = state;
    }

    public String getName() {
        return name;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPlayer() {
        return player.getPlayerId();
    }

    @Override
    public void process(double delta) {
        if (GameScene.isCardSelected() && GameScene.getSelectedCard() == this) {
            if (sprite != player.selectedCardSprite)
                sprite = player.selectedCardSprite;
        } else if (sprite != player.idleCardSprite) {
            sprite = player.idleCardSprite;
        }
    }

    @Override
    public void input(MouseEvent e) {
        if (!interactable)
            return;
        
        if (e.getID() == MouseEvent.MOUSE_ENTERED) {
            zoomIn();
        } else if (e.getID() == MouseEvent.MOUSE_EXITED) {
            zoomOut();
        } else if (e.getID() == MouseEvent.MOUSE_CLICKED && player != null) {
            toggleSelected();
        }
    }

    public void toggleSelected() {
        if (!GameScene.isCardSelected()) {
            player.setSelectedCard(this);
            sprite = player.selectedCardSprite;
        } else if (GameScene.isCardSelected() && GameScene.getSelectedCard() != this) {
            GameScene.clearSelectedCard();
            player.setSelectedCard(this);
            sprite = player.selectedCardSprite;
        } else {
            GameScene.clearSelectedCard();
            sprite = player.idleCardSprite;
        }
    }

    public void updateCard() {
        if (player == null)
            cardMap.populateActions(name, GameScene.PLAYER1);
        else 
            cardMap.populateActions(name, player.getPlayerId());
    }

    public void setStandBy(boolean stb) {
        standBy = stb;
    }

    public boolean isStandBy() {
        return standBy;
    }

    public void addCardToScene(Scene scene) {
        scene.addComponent(cardLabel);
        scene.addComponent(cardMap);
        scene.addComponent(this);
    }

    private void createCardMap() {
        cardMap = new CardMap(new Dimension(
            (int)(getSize().width * 0.6),
            (int)(getSize().height * 0.6)
        ));
        cardMap.setPos(new Vector2D(
            position.x + getSize().width * 0.2,
            position.y + getSize().height * 0.12
        ));
    }

    private void createCardLabel() {
        cardLabel = new MenuFrame(new Dimension(
            (int)(getSize().width * 0.7),
            (int)(getSize().height * 0.2)
        ));
        cardLabel.setPos(new Vector2D(
            position.x + getSize().width * 0.15,
            position.y + getSize().height * 0.7 
        ));

        cardLabel.setMainColor(new Color(0,0,0,0));
        cardLabel.setAccentColor(new Color(0,0,0,0));
        
        JLabel label = new JLabel(name);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setForeground(Main.Palette.foreground);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        cardLabel.add(label);
    }

    private void zoomIn() {
        originalScale = getScale();
        setScale(getScale().multiply(zoomFactor));
        setPos(getPos().add(zoomMovement));

        cardLabel.setScale(scale);
        cardLabel.setPos(new Vector2D(
            position.x + getSize().width * 0.15,
            position.y + getSize().height * 0.7 
        ));
        Font font = ((JLabel)cardLabel.getComponent(0)).getFont();
        ((JLabel)cardLabel.getComponent(0)).setFont(font.deriveFont(font.getStyle(), 17));

        cardMap.setScale(scale);
        cardMap.setPos(new Vector2D(
            position.x + getSize().width * 0.2,
            position.y + getSize().height * 0.12
        ));
    }

    private void zoomOut() {
        setScale(originalScale);
        setPos(getPos().subtract(zoomMovement));

        cardLabel.setScale(scale);
        cardLabel.setPos(new Vector2D(
            position.x + getSize().width * 0.15,
            position.y + getSize().height * 0.7 
        ));
        Font font = ((JLabel)cardLabel.getComponent(0)).getFont();
        ((JLabel)cardLabel.getComponent(0)).setFont(font.deriveFont(font.getStyle(), 15));

        cardMap.setScale(scale);
        cardMap.setPos(new Vector2D(
            position.x + getSize().width * 0.2,
            position.y + getSize().height * 0.12
        ));
    }

}