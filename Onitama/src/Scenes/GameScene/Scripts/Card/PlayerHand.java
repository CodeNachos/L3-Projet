package Onitama.src.Scenes.GameScene.Scripts.Card;

public class PlayerHand {
    int player;
    CardInfo card1;
    CardInfo card2;

    public PlayerHand(int player, CardInfo card1, CardInfo card2) {
        this.player = player;
        this.card1 = card1;
        this.card2 = card2;
    }

    public void printInfo() {
        if (player == 0) {
            System.out.println("Red currently has cards: " + card1.getName() + " and " + card2.getName());
        } else {
            System.out.println("Blue currently has cards: " + card1.getName() + " and " + card2.getName());
        }
    }
    
    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }

    public void setFirstCard(CardInfo card) {
        this.card1 = card;
    }

    public void setSecondCard(CardInfo card) {
        this.card2 = card;
    }

    public CardInfo getFirstCard() {
        return card1;
    }

    public CardInfo getSecondCard() {
        return card2;
    }
}
