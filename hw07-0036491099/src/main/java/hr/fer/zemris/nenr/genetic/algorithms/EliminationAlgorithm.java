package hr.fer.zemris.nenr.genetic.algorithms;


import hr.fer.zemris.nenr.genetic.api.ICrossing;
import hr.fer.zemris.nenr.genetic.api.IFunction;
import hr.fer.zemris.nenr.genetic.api.IMutation;
import hr.fer.zemris.nenr.genetic.api.ISelection;

public class EliminationAlgorithm<T> {

	private final T[] startingPopulation;
	private final IFunction<T> function;
	private final ICrossing<T> crossing;
	private final IMutation<T> mutation;
	private final ISelection<T> selection;
	private final double minError;
	private final int maxIterations;

	public EliminationAlgorithm(T[] startingPopulation, IFunction<T> function, ICrossing<T> crossing,
			IMutation<T> mutation, ISelection<T> selection, double minError, int maxIterations) {
		this.startingPopulation = startingPopulation;
		this.function = function;
		this.crossing = crossing;
		this.mutation = mutation;
		this.selection = selection;
		this.minError = minError;
		this.maxIterations = maxIterations;
	}

	public T run() {
		T[] population = startingPopulation;

        int iterationNumber = 0;
        double bestValue = Double.MAX_VALUE;
        double[] values;
        T bestSolution = null;



		while (iterationNumber < maxIterations && minError < bestValue) {
			values = generateValues(population);

            int eliteIndex = findElite(values);

            if (values[eliteIndex]<bestValue){
                bestValue = values[eliteIndex];
                bestSolution = population[eliteIndex];
                System.out.println("ITERACIJA: " + iterationNumber);
                System.out.println("RJEŠENJE: ");
                print(bestSolution);
                System.out.println("ODSTUPANJE: " + bestValue);
                System.out.println();
            }

			T[] parents = selection.select(population, values);

			T[] kids = crossing.cross(parents[0], parents[1]);

			T mutant = bestMutant(mutation.mutate(kids));

            exchange(population, mutant, parents[2]);

			iterationNumber++;
		}

		return bestSolution;

	}


	private T bestMutant(T[] mutants) {
		if (function.valueAt(mutants[0]) > function.valueAt(mutants[1])) {
			return mutants[1];
		} else {
			return mutants[0];
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

	private void exchange(T[] population, T mutant, T candidate) {
		for (int i = 0; i < population.length; i++) {
			if (population[i] == candidate) {
				population[i] = mutant;
				break;
			}
		}
	}

    private void print(T child) {
        if (child.getClass() == double[].class) {
            double[] solution = (double[]) child;
            for (double aSolution : solution) {
                System.out.print(aSolution + " ");
            }
            System.out.println();
        }
    }

	private double[] generateValues(T[] population) {
		double[] newValues = new double[population.length];
		for (int i = 0; i < newValues.length; i++) {
			newValues[i] = function.valueAt(population[i]);
		}
		return newValues;
	}
}
