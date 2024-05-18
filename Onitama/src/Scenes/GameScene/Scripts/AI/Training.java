package Onitama.src.Scenes.GameScene.Scripts.AI;

import java.util.Random;

/**
 * Training
 */
public class Training {
    static Random random = new Random();
    static int difficulty = 6;
    static int populationSize = 100;
    
    static SmartAI generateAI() {
        int[] weights = new int[SmartAI.NB_METHOD];
        for (int i = 0; i < SmartAI.NB_METHOD; i++)
            weights[i] = random.nextInt();
        return new SmartAI(difficulty, 0, weights);
    }

    static SmartAI mate(SmartAI father, SmartAI mother) {
        int[] weights = new int[SmartAI.NB_METHOD];
        for (int i = 0; i < SmartAI.NB_METHOD; i++)
            weights[i] = (father.weights[i] + mother.weights[i]) / 2;
        return new SmartAI(difficulty, 0, weights);
    }

    static SmartAI mutate(SmartAI original) {
        int index = random.nextInt(SmartAI.NB_METHOD);
        int variation = random.nextInt(original.weights[index]);
        if (random.nextBoolean())
            original.weights[index] += variation;
        else
            original.weights[index] -= variation; 
        return original;
    }

    public static void main(String[] args) {
        // Intialize population
        SmartAI[] population = new SmartAI[populationSize];
        for (int i = 0; i < populationSize; i++)
            population[i] = generateAI();

        // TODO
    }
}