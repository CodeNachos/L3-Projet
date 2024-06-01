package Onitama.src.Scenes.GameScene.Scripts.AI;

import java.util.Hashtable;
import java.util.Random;

/**
 * Training
 */
public class Training {
    static int difficulty = 1;
    static int populationSize = 40;
    static int survivalSize = 10;
    static int numberStep = 1000;
    static int newMemberSize = 4;
    static int maxWeight = 1000;
    static Random random = new Random();
    static SmartAI[] population = new SmartAI[populationSize];
    static SmartAI[] survivors = new SmartAI[survivalSize];
    static Hashtable<SmartAI, Double> fitness = new Hashtable<>();

    static SmartAI generateAI() {
        int[] weights = new int[SmartAI.NB_METHOD];
        for (int i = 0; i < SmartAI.NB_METHOD; i++)
            weights[i] = random.nextInt(maxWeight);
        return new SmartAI(difficulty, 0, weights);
    }

    static SmartAI mate(SmartAI father, SmartAI mother) {
        int[] weights = new int[SmartAI.NB_METHOD];
        for (int i = 0; i < SmartAI.NB_METHOD; i++)
            if (random.nextBoolean())
                weights[i] = father.weights[i];
            else
                weights[i] = mother.weights[i];
        return new SmartAI(difficulty, 0, weights);
    }

    static SmartAI mutate(SmartAI original) {
        int[] weights = new int[SmartAI.NB_METHOD];
        for (int i = 0; i < SmartAI.NB_METHOD; i++)
            weights[i] = original.weights[i];

        for (int i = 0; i < SmartAI.NB_METHOD; i++) {
            if (random.nextBoolean())
                weights[i] += random.nextInt(weights[i]/100 + 1);
            else
                weights[i] -= random.nextInt(weights[i]/100 + 1); 
        }
        
        return new SmartAI(difficulty, 0, weights);
    }

    static void calculateFitness() {
        fitness.clear();
        for (int i = 0; i < populationSize; i++)
            fitness.put(population[i], 0.);

        for (int i = 0; i < populationSize-1; i++) {
            SmartAI target = population[i];
            for (int j = i+1; j < populationSize; j++) {
                SmartAI opponent = population[j];
                Match match = new Match(target, opponent);
                match.fight();
                if (match.length == 100)
                    continue;
                else if (target == match.winner){
                    fitness.put(target, fitness.get(target)+(100-match.length));
                    fitness.put(opponent, fitness.get(opponent)+(-100+match.length));
                } else {
                    fitness.put(target, fitness.get(target)+(-100+match.length));
                    fitness.put(opponent, fitness.get(opponent)+(100-match.length));
                }
            }
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
            if (i < newMemberSize)
                population[i] = generateAI();
            else {
                int fatherIndex = random.nextInt(survivalSize);
                int motherIndex = random.nextInt(survivalSize);
                population[i] = mate(survivors[fatherIndex], survivors[motherIndex]);
            }
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