package hr.fer.zemris.nenr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.nenr.genetic.algorithms.EliminationAlgorithm;
import hr.fer.zemris.nenr.genetic.algorithms.GenerationAlgorithm;
import hr.fer.zemris.nenr.genetic.api.ICrossing;
import hr.fer.zemris.nenr.genetic.api.IFunction;
import hr.fer.zemris.nenr.genetic.api.IMutation;
import hr.fer.zemris.nenr.genetic.api.ISelection;
import hr.fer.zemris.nenr.genetic.impl.*;
import hr.fer.zemris.nenr.network.api.IReadOnlyDataset;
import hr.fer.zemris.nenr.network.api.ITransferFunction;
import hr.fer.zemris.nenr.network.impl.Dataset;
import hr.fer.zemris.nenr.network.impl.IdentityTransferFunction;
import hr.fer.zemris.nenr.network.impl.SigmoidTransferFunction;

public class Main {


//    zad4-dataset1.txt 200 0.000001 1000 true 0.2 false
//    zad4-dataset2.txt 200 0.000001 1000 true 0.2 false
//    zad4-dataset1.txt 200 0.000001 1000 false 0.5 false
//    zad4-dataset2.txt 200 0.000001 1000 false 0.5 false
//    zad4-dataset1.txt 20 0.000001 10000 false 0.2 true
//    zad4-dataset2.txt 20 0.000001 10000 false 0.2 true

    public static void main(String[] args) throws IOException {


        IReadOnlyDataset dataset = new Dataset("dataset.txt");
        System.out.println("Imamo uzoraka za ucenje: " + dataset.numberOfSamples());
        FFANN ffann = new FFANN(new int[]{2, 8, 3},
                new ITransferFunction[]{new IdentityTransferFunction(), new SigmoidTransferFunction(),
                        new SigmoidTransferFunction()}, dataset);


        IFunction<double[]> function = new AFFunction(ffann);

        int populationSize = 10;

        double[][] startingPopulation = generatePopulation(populationSize, ffann.getWeightsCount());

        double minError = 0.001;
        int maxIterations = 10000;

        ISelection<double[]> selectionGeneration = new RouletteWheel();

        ISelection<double[]> selectionElimination = new NTournamentDoubleArray(3);

        boolean elite = true;

        double sigma = 0.2;
        IMutation<double[]> mutation = new SigmaMutation(sigma);

        ICrossing<double[]> crossing = new BLX(0.1);

        boolean elimination = false;

        double[] best = null;

        if (!elimination) {
            GenerationAlgorithm<double[]> algorithm = new GenerationAlgorithm<>(startingPopulation, function,
                    crossing, mutation, selectionElimination, elite, minError, maxIterations);
            best = algorithm.run();
        } else {
            EliminationAlgorithm<double[]> algorithm = new EliminationAlgorithm<>(startingPopulation, function,
                    crossing, mutation, selectionElimination, minError, maxIterations);

            best = algorithm.run();
        }


        ffann.statistics(best);
    }

    private static double[][] generatePopulation(int populationSize, int weightsCount) {
        double[][] population = new double[populationSize][weightsCount];
        for (int i = 0; i < populationSize; i++) {
            population[i] = generateSolution(weightsCount);
        }
        return population;
    }

    private static double[] generateSolution(int weightCount) {
        Random rand = new Random();
        double[] solution = new double[weightCount];
        for (int i = 0; i < weightCount; i++) {
            solution[i] = -4 + rand.nextDouble() * 8;
        }
        return solution;
    }

}
