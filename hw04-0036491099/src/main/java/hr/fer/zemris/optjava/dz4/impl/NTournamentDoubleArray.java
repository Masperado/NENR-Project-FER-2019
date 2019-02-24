package hr.fer.zemris.optjava.dz4.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz4.api.ISelection;

public class NTournamentDoubleArray implements ISelection<double[]> {

	private int n;
	Random random = new Random();

	public NTournamentDoubleArray(int n) {
		this.n = n;
	}

	@Override
	public double[][] select(double[][] population, double[] values) {
		int indexes[] = randomIndexes(population.length, n);

		double[][] tournamentPopulation = new double[n][population[0].length];
		double[] tournamentValues = new double[n];

		for (int i=0;i<n;i++){
		    tournamentPopulation[i]=population[indexes[i]];
		    tournamentValues[i] = values[indexes[i]];
        }

        bubbleSort(tournamentPopulation,tournamentValues);

		return tournamentPopulation;

	}

    public void bubbleSort(double[][] population, double[] values) {
        boolean swapped = true;
        int j = 0;
        double[] tmp;
        double tmp2;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < population.length - j; i++) {
                if (values[i] > values[i + 1]) {
                    tmp = population[i];
                    population[i] = population[i + 1];
                    population[i + 1] = tmp;

                    tmp2 = values[i];
                    values[i] = values[i + 1];
                    values[i + 1] = tmp2;

                    swapped = true;
                }
            }
        }
    }

	private int[] randomIndexes(int length, int n) {
		List<Integer> indexes = new ArrayList<>();
		for (int i = 0; i < n; i++) {
		    int index = random.nextInt(length);
		    if (!indexes.contains(index)){
		        indexes.add(index);
            } else{
		        i--;
            }
		}
		return indexes.stream().mapToInt(i->i).toArray();
	}

}
