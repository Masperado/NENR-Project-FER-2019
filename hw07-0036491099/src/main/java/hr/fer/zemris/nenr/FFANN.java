package hr.fer.zemris.nenr;

import hr.fer.zemris.nenr.network.api.IReadOnlyDataset;
import hr.fer.zemris.nenr.network.api.ITransferFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;


public class FFANN {

    private int[] layers;
    private ITransferFunction[] functions;
    private IReadOnlyDataset dataset;

    public FFANN(int[] layers, ITransferFunction[] functions, IReadOnlyDataset dataset) {
        this.layers = layers;
        this.functions = functions;
        this.dataset = dataset;
    }

    public int getWeightsCount() {
        int weightsCount = 0;

        for (int i = 0; i < layers.length; i++) {

            weightsCount += layers[i];

            if (i == 0) {
                weightsCount += layers[i] * layers[i + 1] * 2;
            } else if (i != layers.length - 1) {
                weightsCount += layers[i] * layers[i + 1];
            }
        }

        weightsCount -= layers[0] + layers[1];

        return weightsCount;
    }

    public double[] calcOutputs(double[] inputs, double[] weights) {
        if (weights.length != this.getWeightsCount()) {
            throw new RuntimeException("Weights don't match neural network");
        }

        List<Double> nets = new ArrayList<>();
        List<Double> functionValues = DoubleStream.of(inputs).boxed().collect(Collectors.toCollection(ArrayList::new));
        int weightsCounter = 0;

        for (int i = 0; i < layers.length - 1; i++) {
            if (i == 10) {
                for (int j = 0; j < layers[i + 1]; j++) {
                    double netValue = 0;
                    for (Double k : functionValues) {
                        netValue += Math.abs((k - weights[weightsCounter]) / weights[weightsCounter + 1]);
                        weightsCounter += 2;
                    }
                    netValue = 1 / (1 + netValue);
                    nets.add(netValue);
                }
            } else {
                for (int j = 0; j < layers[i + 1]; j++) {
                    double netValue = 0;
                    for (Double k : functionValues) {
                        netValue += k * weights[weightsCounter];
                        weightsCounter++;
                    }
                    netValue += weights[weightsCounter];
                    weightsCounter++;
                    nets.add(netValue);
                }
            }
            functionValues.clear();
            for (Double net : nets) {
                functionValues.add(functions[i+1].valueAt(net));
            }
            nets.clear();
        }

        double[] outputs = functionValues.stream().mapToDouble(Double::doubleValue).toArray();
        return outputs;
    }

    public double errorFunction(double[] weights) {
        double error = 0;
        double[] outputs = new double[layers[layers.length - 1]];

        for (double[] inputs : dataset.getTestDataset()) {
            outputs = calcOutputs(inputs, weights);
            int[] wantedIntOutputs = dataset.getOutput(inputs);
            for (int i = 0; i < outputs.length; i++) {
                error += Math.pow(outputs[i] - wantedIntOutputs[i], 2);
            }

        }
        return error / dataset.numberOfSamples();
    }

    public void statistics(double[] weights) {
        int good = 0;
        int bad = 0;

        double[] outputs = new double[layers[layers.length - 1]];
        int[] intOutputs = new int[outputs.length];

        for (double[] inputs : dataset.getTestDataset()) {
            outputs = calcOutputs(inputs, weights);

            int[] wantedIntOutputs = dataset.getOutput(inputs);
            for (int i = 0; i < outputs.length; i++) {
                intOutputs[i] = outputs[i] > 0.5 ? 1 : 0;
            }

            boolean goodOutput = true;
            for (int i = 0; i < outputs.length; i++) {
                if (intOutputs[i] != wantedIntOutputs[i]) {
                    bad++;
                    goodOutput = false;
                    break;
                }
            }
            if (goodOutput) {
                good++;
            }

            System.out.printf("UZORAK: ");
            for (int i = 0; i < inputs.length; i++) {
                System.out.printf(inputs[i] + " ");
            }
            System.out.printf("DOBIVENI IZLAZ: ");
            for (int i = 0; i < outputs.length; i++) {
                System.out.printf(intOutputs[i] + " ");
            }
            System.out.printf("TRAŽENI IZLAZ: ");
            for (int i = 0; i < outputs.length; i++) {
                System.out.printf(wantedIntOutputs[i] + " ");
            }
            System.out.println();
        }
        System.out.println("DOBRIH: " + good);
        System.out.println("LOŠIH: " + bad);
    }

}
