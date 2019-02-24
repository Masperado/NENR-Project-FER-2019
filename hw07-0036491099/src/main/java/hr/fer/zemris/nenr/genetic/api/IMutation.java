package hr.fer.zemris.nenr.genetic.api;

public interface IMutation<T> {

	T[] mutate(T[] kids);
}
