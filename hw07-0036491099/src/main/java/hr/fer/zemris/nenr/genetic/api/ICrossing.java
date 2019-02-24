package hr.fer.zemris.nenr.genetic.api;

public interface ICrossing<T> {

	T[] cross(T parent1, T parent2);

}
