package Onitama.src.Scenes.GameScene.Scripts.AI;

import Onitama.src.Scenes.GameScene.GameScene;

public class Testing {
    public static void main(String[] args) {
            SmartAI red = new SmartAI(5, GameScene.PLAYER1, new int[] {4, 3, 2, 1});
            SmartAI blue = new SmartAI(5, GameScene.PLAYER2, new int[] {1, 1, 1, 1});
        int nbRed = 0, nbBlue = 0;
        for (int round = 0; round < 100; round++) {
            Match match = new Match(red, blue);
            match.fight();
            if (match.winner == red)
                nbRed++;
            else if (match.winner == blue)
                nbBlue++;
            System.out.println("Finished round " + round);
        }
        System.out.println("Red won: " + nbRed + " games");
        System.out.println("Blue won: " + nbBlue + " games");
    }
}
