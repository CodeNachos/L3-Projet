package Model;

import java.io.Serializable;
import java.util.*;

public class Card implements Serializable{
    String name;
    List<Movement> blueMovement;
    List<Movement> redMovement;

    public Card()
    {
        name = "";
        blueMovement = new ArrayList<>();
        redMovement = new ArrayList<>();
    }

    public void setString(String str)
    {
        this.name = str;
    }

    public void addBlue(Movement movement) {
        blueMovement.add(movement);
        return;
    }

    public void addRed(Movement movement) {
        redMovement.add(movement);
        return;
    }

    public String getName() {
        return name;
    }

    public List<Movement> getBlueMovement() {
        return blueMovement;
    }

    public List<Movement> getRedMovement() {
        return redMovement;
    }

    public void setBlueMovement(List<Movement> bl)
    {
        this.blueMovement = bl;
    }

    public void setRedMovement(List<Movement>rl)
    {
        this.redMovement = rl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(name, card.name) &&
                Objects.equals(blueMovement, card.blueMovement) &&
                Objects.equals(redMovement, card.redMovement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, blueMovement, redMovement);
    }

    
    
}
