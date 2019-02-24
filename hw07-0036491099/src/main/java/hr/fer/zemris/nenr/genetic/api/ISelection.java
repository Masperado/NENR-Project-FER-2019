package hr.fer.zemris.nenr.genetic.api;

public interface ISelection<T> {

	T[] select(T[] population, double[] values);

}
