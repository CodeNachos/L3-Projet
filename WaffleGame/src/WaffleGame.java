package WaffleGame.src;

import Engine.Entities.GameObject;

public class WaffleGame extends GameObject {

    static int currentPlayer; 
    
    public WaffleGame() {
        currentPlayer = 0;

        System.out.println("Player " + currentPlayer + " turn");
    }

    @Override
    public void process() {
        if (isGameOver()) {
            System.out.println("Game Over : Player " + (currentPlayer + 1) % 2 + " won");
            Main.engine.stop();
        }

        if (Main.map.next_player) {
            nextPlayer();
            System.out.println("Player " + currentPlayer + " turn");
            Main.map.next_player = false;
        }
    }

    static boolean isGameOver() {
        return (Main.map.gridmap[0][0] == null);
    }

    static void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % 2; // for 2 players
    }
}
