package Onitama2.src.Scenes.GameScene.Scripts.Card;


import java.util.*;

import Engine.Structures.Vector2D;

public class CardInfo {
    String name;
    List<Vector2D> blueMovement;
    List<Vector2D> redMovement;

    public CardInfo() {
        name = "";
        blueMovement = new ArrayList<>();
        redMovement = new ArrayList<>();
    }

    public void setString(String str) {
        this.name = str;
    }

    public void addBlue(Vector2D movement) {
        blueMovement.add(movement);
        return;
    }

    public void addRed(Vector2D movement) {
        redMovement.add(movement);
        return;
    }

    public String getName() {
        return name;
    }

    public List<Vector2D> getBlueMovement() {
        return blueMovement;
    }

    public List<Vector2D> getRedMovement() {
        return redMovement;
    }
    
}
