package WaffleGame.src;

import Engine.Entities.GameObject;
import Engine.Entities.UI.MenuFrame;

public class WaffleGame extends GameObject {

    static int currentPlayer; 

    boolean gameOver = false;
    
    public WaffleGame() {
        currentPlayer = 0;

        System.out.println("Player " + (currentPlayer + 1) + " turn");
    }

    @Override
    public void process() {
        if (gameOver) 
            return;

        if (isGameOver()) {
            System.out.println("Game Over : Player " + (((currentPlayer + 1) % 2) + 1) + " won");
            Main.playerLabel.setText("GAME OVER : PLAYER " + (((currentPlayer + 1) % 2) + 1) + " WON");
            
            MenuFrame gameOverMenu = Main.createGameOverMenu();
            Main.mainScene.add(gameOverMenu);
            Main.mainScene.setComponentZOrder(gameOverMenu, 0);
            
            gameOver = true;
        }

        if (Main.map.next_player) {
            nextPlayer();
            System.out.println("Player " + (currentPlayer + 1) + " turn");
            Main.playerLabel.setText("PLAYER " + (currentPlayer + 1) + " TURN");
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
