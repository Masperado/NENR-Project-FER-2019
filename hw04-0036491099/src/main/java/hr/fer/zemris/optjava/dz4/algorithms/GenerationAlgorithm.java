package hr.fer.zemris.optjava.dz4.algorithms;

import hr.fer.zemris.optjava.dz4.api.ICrossing;
import hr.fer.zemris.optjava.dz4.api.IFunction;
import hr.fer.zemris.optjava.dz4.api.IMutation;
import hr.fer.zemris.optjava.dz4.api.ISelection;

public final class GenerationAlgorithm<T> {

	private final T[] startingPopulation;
	private final IFunction<T> function;
	private final ICrossing<T> crossing;
	private final IMutation<T> mutation;
	private final ISelection<T> selection;
	private final double minError;
	private final int maxIterations;
	private boolean elite;

	public GenerationAlgorithm(T[] startingPopulation, IFunction<T> function, ICrossing<T> crossing,
                               IMutation<T> mutation, ISelection<T> selection, boolean elite, double minError, int maxIterations) {
		this.startingPopulation = startingPopulation;
		this.function = function;
		this.crossing = crossing;
		this.mutation = mutation;
		this.selection = selection;
		this.minError = minError;
		this.maxIterations = maxIterations;
		this.elite = elite;
	}

	public void run() {
		T[] population = startingPopulation;
		int iterationNumber = 0;
		double bestValue = Double.MAX_VALUE;
		double[] values;
		T bestSolution;
		while (iterationNumber < maxIterations && minError < bestValue) {
			values = generateValues(population);
			int eliteIndex = findElite(values);
			T[] newPopulation = population.clone();

			if (elite) {
                newPopulation[0] = population[eliteIndex];
            }

            if (values[eliteIndex]<bestValue){
			    bestValue = values[eliteIndex];
			    bestSolution = population[eliteIndex];
                System.out.println("ITERACIJA: " + iterationNumber);
                System.out.println("RJEŠENJE: ");
                print(bestSolution);
                System.out.println("ODSTUPANJE: " + bestValue);
                System.out.println();
            }

			for (int i = 2; i < population.length; i += 2) {
				T[] parents = selection.select(population, values);
				T[] kids = crossing.cross(parents[0], parents[1]);
				T[] mutants = mutation.mutate(kids);

				if ((i + 1) == population.length) {
					newPopulation[i] = mutants[0];
				} else {
					newPopulation[i] = mutants[0];
					newPopulation[i + 1] = mutants[1];
				}
			}
			population = newPopulation;
			iterationNumber++;

		}

	}

	private void print(T child) {
		if (child.getClass() == double[].class) {
			double[] solution = (double[]) child;
			for (int i = 0; i < solution.length; i++) {
                System.out.print(solution[i] + " ");
			}
			System.out.println();
		}
	}

	private int findElite(double[] values) {
		int indexElite1 = 0;

		for (int i = 1; i < values.length; i++) {
			if (values[i] < values[indexElite1]) {
				indexElite1 = i;
			}
		}

		return indexElite1;

	}

	private double[] generateValues(T[] population) {
		double[] newValues = new double[population.length];
		for (int i = 0; i < newValues.length; i++) {
			newValues[i] = function.valueAt(population[i]);
		}
		return newValues;
	}

}
