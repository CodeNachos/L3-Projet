package Onitama.src.Scenes.GameScene.Scripts.AI;

import java.util.Random;

/**
 * Training
 */
public class Training {
    Random random = new Random();
    
    SmartAI generateAI() {
        return null;
    }

    SmartAI mate(SmartAI father, SmartAI mother) {
        int[] weights = new int[SmartAI.NB_METHOD];
        for (int i = 0; i < SmartAI.NB_METHOD; i++)
            weights[i] = (father.weights[i] + mother.weights[i]) / 2;
        return new SmartAI(father.difficulty, father.selfID, weights);
    }

    SmartAI mutate(SmartAI original) {
        return null;
    }

    public static void main(String[] args) {
        
    }
}