package hr.fer.zemris.nenr.hw05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Mreza {

    private int variant;

    private int maxIter;

    private double learningRate;

    private List<Sloj> slojevi = new ArrayList<>();

    private List<List<Double>> inputs = new ArrayList<>();

    private List<List<Double>> outputs = new ArrayList<>();


    public Mreza(String sampleFilePath, int maxIter, int variant, double learningRate, int... layersSizes) throws IOException {

        this.maxIter = maxIter;
        this.learningRate = learningRate;
        this.variant = variant;

        if (layersSizes.length < 3) {
            throw new IllegalArgumentException("Premalo slojeva");
        }

        List<String> lines = Files.readAllLines(Paths.get(sampleFilePath));


        for (String line : lines) {

            List<Double> input = new ArrayList<>();
            List<Double> output = new ArrayList<>();

            String[] numbers = line.split(" ");

            for (int i = 0; i < numbers.length - 5; i++) {
                input.add(Double.valueOf(numbers[i]));
            }

            for (int i = numbers.length - 5; i < numbers.length; i++) {
                output.add(Double.valueOf(numbers[i]));
            }

            inputs.add(input);
            outputs.add(output);

        }

        if (layersSizes[0] != inputs.get(0).size()) {
            throw new IllegalArgumentException("Nije 2*M!");
        }

        if (layersSizes[layersSizes.length - 1] != 5) {
            throw new IllegalArgumentException("Nije 5!");
        }

        slojevi.add(new Sloj(layersSizes[0], true, layersSizes[1]));

        for (int i = 1; i < layersSizes.length - 1; i++) {
            slojevi.add(new Sloj(layersSizes[i], false, layersSizes[i + 1]));
        }

        slojevi.add(new Sloj(layersSizes[layersSizes.length - 1], false, 0));

    }

    public void evaluate(List<Double> sample) {

        for (int i = 0; i < sample.size(); i++) {
            slojevi.get(0).getNeuron(i).setW0(sample.get(i));
        }

        for (int i = 1; i < slojevi.size(); i++) {
            slojevi.get(i).calcLayer(slojevi.get(i - 1));
        }

    }

    public List<Double> getValue() {
        Sloj last = slojevi.get(slojevi.size() - 1);

        List<Double> values = new ArrayList<>();

        for (int i = 0; i < last.size(); i++) {
            values.add(last.getNeuron(i).getY());
        }

        return values;
    }

    public String getLetter() {
        String[] letters = {"Alpha", "Beta", "Gamma", "Delta", "Epsilon"};
        int max = 0;
        Sloj last = slojevi.get(slojevi.size() - 1);
        for (int i = 1; i < 5; i++) {
            if (last.getNeuron(i).getY() > last.getNeuron(max).getY()) {
                max = i;
            }
        }
        return letters[max];
    }

    public double getError() {
        double totalError = 0;
        for (int i = 0; i < inputs.size(); i++) {
            evaluate(inputs.get(i));
            for (int j = 0; j < slojevi.get(slojevi.size() - 1).size(); j++) {
                double diff = outputs.get(i).get(j) - slojevi.get(slojevi.size() - 1).getNeuron(j).getY();
                totalError += diff * diff;
            }
        }
        return totalError;
    }


    public void updateWeights(int batchSize) {

        int outLayer = slojevi.size() - 1;

        for (int i = 0; i < inputs.size(); i++) {

            evaluate(inputs.get(i));

            for (int j = 0; j < slojevi.get(outLayer).size(); j++) {
                double y = slojevi.get(outLayer).getNeuron(j).getY();
                double outDelta = y * (1 - y) * (outputs.get(i).get(j) - y);
                slojevi.get(outLayer).getNeuron(j).setDelta(outDelta);
            }

            for (int j = outLayer - 1; j >= 0; j--) {
                slojevi.get(j).updateWeights(slojevi.get(j + 1), learningRate);
                slojevi.get(j).updateDeltas(slojevi.get(j + 1));
            }

            if ((i + 1) % batchSize == 0) {
                for (Sloj l : slojevi) {
                    l.swapWeights();
                }
            }

        }

        for (Sloj l : slojevi) {
            l.swapWeights();
        }

    }

    public void learn() {

        for (int i = 0; i < maxIter; i++) {

            double error = getError();
            System.out.println(i + " " + error);

            if (variant == 1) {
                updateWeights(inputs.size());
            } else if (variant == 2) {
                updateWeights(1);
            } else {
                Collections.shuffle(inputs, new Random(1337));
                Collections.shuffle(outputs, new Random(1337));
                updateWeights(inputs.size() / 10);
            }

        }
    }


}