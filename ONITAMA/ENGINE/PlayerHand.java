package ENGINE;


/*
 * PlayerHand = cards on a player
 */
public class PlayerHand {
    int player;
    Card card1;
    Card card2;

    public PlayerHand(int player, Card card1, Card card2) {
        this.player = player;
        this.card1 = card1;
        this.card2 = card2;
    }

    public void displayInfo() {
        if (player == 0) {
            System.out.println("Red currently has cards: " + card1.getName() + " and " + card2.getName());
        } else {
            System.out.println("Blue currently has cards: " + card1.getName() + " and " + card2.getName());
        }
    }
    
    public void setPlayer(int player)
    {
        this.player = player;
    }

    public void setFirstCard(Card card) {
        this.card1 = card;
    }

    public void setSecondCard(Card card) {
        this.card2 = card;
    }

    public Card getFirstCard() {
        return card1;
    }

    public Card getSecondCard() {
        return card2;
    }


}
