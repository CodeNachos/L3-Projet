package Onitama.src.GameScene.Entities.Cards;

import Engine.Core.Renderer.Scene;
import Onitama.src.GameScene.GameScene;

public class PlayerHand {
    int player;
    Card card1;
    Card card2;

    Card selectedCard = null;

    public PlayerHand(int player, Card card1, Card card2) {
        this.player = player;
        this.card1 = card1;
        this.card2 = card2;

        this.card1.hand = this;
        this.card2.hand = this;
    }

    public void printInfo() {
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

    public void setSelectedCard(Card c) {
        if (c == null) {
            card1.sprite = GameScene.idleCardSprite;
            card2.sprite = GameScene.idleCardSprite;
            return;
        }

        if (GameScene.currentPlayer != player)
            return;
        if (selectedCard == c)
            return;
            
        if (selectedCard != null)
            selectedCard.sprite = GameScene.idleCardSprite;
        
        selectedCard = c;
        selectedCard.sprite = GameScene.selectedCardSprite;
    }

    public void addHandToScene(Scene scene) {
        card1.addCardToScene(scene);
        card2.addCardToScene(scene);
    }

    public void updateCards() {
        card1.updateCard();
        card2.updateCard();
    }
}
