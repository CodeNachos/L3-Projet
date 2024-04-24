package WaffleGame.src;

import Engine.Entities.GameObject;
import Engine.Entities.UI.MenuFrame;

/**
 * The WaffleGame class represents the main game logic for the WaffleGame.
 * It extends the GameObject class to provide game-specific behavior.
 */
public class WaffleGame extends GameObject {

    /** The current player (0 or 1) */
    static int currentPlayer; 

    /** Flag indicating whether the game is over */
    boolean gameOver = false;
    
    /**
     * Constructs a new WaffleGame instance.
     * Initializes the current player and prints the start message.
     */
    public WaffleGame() {
        currentPlayer = 0;
        System.out.println("Player " + (currentPlayer + 1) + " turn");
    }

    /**
     * Overrides the process method to handle game-specific logic.
     * Checks for game over condition and updates player turns.
     */
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

    /**
     * Checks if the game is over.
     * @return True if the top-left tile of the map is null (indicating game over), otherwise false.
     */
    static boolean isGameOver() {
        return (Main.map.gridmap[0][0] == null);
    }

    /**
     * Updates the current player to the next player.
     */
    static void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % 2; // for 2 players
    }
}
