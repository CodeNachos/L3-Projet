package WaffleGame.src.Scenes.GameScene.Entities;

import Engine.Entities.GameObject;
import Engine.Entities.UI.MenuFrame;
import WaffleGame.src.Main;
import WaffleGame.src.Scenes.GameScene.GameScene;
import WaffleGame.src.Scenes.GameScene.Interface.GameOverMenu;

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
    public void process(double delta) { 
        if (gameOver) 
            return;

        if (isGameOver()) {
            System.out.println("Game Over : Player " + (((currentPlayer + 1) % 2) + 1) + " won");
            
            MenuFrame gameOverMenu = new GameOverMenu();
            Main.gameScene.add(gameOverMenu);
            Main.gameScene.setComponentZOrder(gameOverMenu, 0);
            GameScene.statsMenu.playerLabel.setText("GAME OVER : PLAYER " + (((currentPlayer + 1) % 2) + 1) + " WON");
            
            gameOver = true;
        }

        if (GameScene.map.next_player && !gameOver) {
            nextPlayer();
            System.out.println("Player " + (currentPlayer + 1) + " turn");
            GameScene.statsMenu.playerLabel.setText("PLAYER " + (currentPlayer + 1) + " TURN");
            GameScene.map.next_player = false;
        }
    }

    /**
     * Checks if the game is over.
     * @return True if the top-left tile of the map is null (indicating game over), otherwise false.
     */
    static boolean isGameOver() {
        return (GameScene.map.gridmap[0][0] == null);
    }

    /**
     * Updates the current player to the next player.
     */
    public void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % 2; // for 2 players
    }
}
