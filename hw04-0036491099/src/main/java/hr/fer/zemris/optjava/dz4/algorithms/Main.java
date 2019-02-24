package hr.fer.zemris.optjava.dz4.algorithms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz4.api.ICrossing;
import hr.fer.zemris.optjava.dz4.api.IFunction;
import hr.fer.zemris.optjava.dz4.api.IMutation;
import hr.fer.zemris.optjava.dz4.api.ISelection;
import hr.fer.zemris.optjava.dz4.impl.*;

public class Main {


//    zad4-dataset1.txt 200 0.000001 1000 true 0.2 false
//    zad4-dataset2.txt 200 0.000001 1000 true 0.2 false
//    zad4-dataset1.txt 200 0.000001 1000 false 0.5 false
//    zad4-dataset2.txt 200 0.000001 1000 false 0.5 false
//    zad4-dataset1.txt 20 0.000001 10000 false 0.2 true
//    zad4-dataset2.txt 20 0.000001 10000 false 0.2 true

    public static void main(String[] args) throws IOException {

        if (args.length != 7) {
            System.out.println("Neispravan broj argumenta");
            System.exit(1);
        }

        List<String> rows = Files.readAllLines(Paths.get(args[0]));
        double[] consts = new double[rows.size()];
        double[][] inputs = new double[rows.size()][2];

        for (int i = 0; i < rows.size(); i++) {
            String[] values = rows.get(i).split("\\s+");
            for (int j = 0; j < values.length - 1; j++) {
                inputs[i][j] = Double.valueOf(values[j]);
            }
            consts[i] = Double.valueOf(values[values.length - 1]);

        }

        IFunction<double[]> function = new AFFunction(inputs, consts);

        int populationSize = Integer.valueOf(args[1]);
        double[][] startingPopulation = generatePopulation(populationSize);

        double minError = Double.valueOf(args[2]);
        int maxIterations = Integer.valueOf(args[3]);

        ISelection<double[]> selectionGeneration = new RouletteWheel();

        ISelection<double[]> selectionElimination = new NTournamentDoubleArray(3);

        boolean elite = Boolean.valueOf(args[4]);

        double sigma = Double.valueOf(args[5]);
        IMutation<double[]> mutation = new SigmaMutation(sigma);

        ICrossing<double[]> crossing = new BLX(0.1);

        boolean elimination = Boolean.valueOf(args[6]);

        if (!elimination) {
            GenerationAlgorithm<double[]> algorithm = new GenerationAlgorithm<>(startingPopulation, function,
                    crossing, mutation, selectionGeneration, elite, minError, maxIterations);
            algorithm.run();
        } else {
            EliminationAlgorithm<double[]> algorithm = new EliminationAlgorithm<>(startingPopulation, function,
                    crossing, mutation, selectionElimination, minError, maxIterations);

            algorithm.run();
        }
    }

    private static double[][] generatePopulation(int populationSize) {
        double[][] population = new double[populationSize][6];
        for (int i = 0; i < populationSize; i++) {
            population[i] = generateSolution();
        }
        return population;
    }

    private static double[] generateSolution() {
        Random rand = new Random();
        double[] solution = new double[5];
        for (int i = 0; i < 5; i++) {
            solution[i] = -4 + rand.nextDouble() * 8;
        }
        return solution;
    }

}
