package hr.fer.zemris.nenr.network.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.nenr.network.api.IReadOnlyDataset;

public class Dataset implements IReadOnlyDataset {

	private Map<double[], int[]> Map;

	public Dataset(String path) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(path));
		this.Map = parseLinesIntoMap(lines);
	}

	private Map<double[], int[]> parseLinesIntoMap(List<String> lines) {
		Map<double[], int[]> Map = new LinkedHashMap<>();

		for (String line : lines) {
			String[] inputs = line.split("\\s+");

			double[] inputsDouble = new double[2];
			int[] outputsInt = new int[3];

			for (int i = 0; i < inputsDouble.length; i++) {
				inputsDouble[i] = Double.valueOf(inputs[i]);
			}

			for (int i = 0; i < outputsInt.length; i++) {
				outputsInt[i] = Integer.valueOf(inputs[i+2]);
			}

			Map.put(inputsDouble, outputsInt);

		}

		return Map;
	}

	@Override
	public int[] getOutput(double[] input) {
		return Map.get(input);
	}

	@Override
	public Set<double[]> getTestDataset() {
		return Map.keySet();
	}

	@Override
	public int numberOfSamples() {
		return Map.size();
	}

}
