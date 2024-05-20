package Onitama.src.Scenes.GameScene.Scripts.AI;

import java.util.Hashtable;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * Training
 */
public class Training {
    static Random random = new Random();
    static int difficulty = 2;
    static int populationSize = 20;
    static int survivalSize = 5;
    static int numberStep = 100;
    static SmartAI[] population = new SmartAI[populationSize];
    static SmartAI[] survivors = new SmartAI[survivalSize];
    static Hashtable<SmartAI, Double> fitness = new Hashtable<>();
    
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
        int variation = random.nextInt(abs(original.weights[index])/100);
        if (random.nextBoolean())
            original.weights[index] += variation;
        else
            original.weights[index] -= variation; 
        return original;
    }

    static void calculateFitness() {
        fitness.clear();
        for (int i = 0; i < populationSize; i++) {
            SmartAI target = population[i];
            double score = 0;
            for (int j = 0; j < populationSize; j++) {
                Match match = new Match(target, population[j]);
                match.fight();
                if (match.length == 100)
                    score = 0;
                else if (target == match.winner)
                    score += 1;
                else
                    score += -1;
            }
            fitness.put(target, score);
        }
    }

    static SmartAI extractBest() {
        SmartAI best = null;
        double highest = Double.NEGATIVE_INFINITY;
        for (SmartAI ai : fitness.keySet()) {
            if (fitness.get(ai) > highest) {
                best = ai;
                highest = fitness.get(ai);
            }
        }
        fitness.remove(best);
        return best;
    }

    static void selection() {
        for (int i = 0; i < survivalSize; i++)
            survivors[i] = extractBest();
    }
    
    static void crossover() {
        for (int i = 0; i < populationSize; i++) {
            int fatherIndex = random.nextInt(survivalSize);
            int motherIndex = random.nextInt(survivalSize);
            population[i] = mate(survivors[fatherIndex], survivors[motherIndex]);
        }
    }

    static void mutation() {
        for (int i = 0; i < populationSize; i++) 
            population[i] = mutate(population[i]);
    }

    public static void main(String[] args) {
        // Intialize population
        for (int i = 0; i < populationSize; i++)
            population[i] = generateAI();

        for (int step = 0; step < numberStep; step++) {
            System.out.println("Generation " + step);
            calculateFitness();
            selection();
            crossover();
            mutation();
        }

        int[] weights = extractBest().weights;
        String weightsText = "";
        for (int i = 0; i < SmartAI.NB_METHOD; i++) {
            weightsText += weights[i];
            if (i < SmartAI.NB_METHOD - 1)
                weightsText += ", "; 
        }
        System.out.println("Best weight vector: [ " + weightsText + " ]");
    }
}