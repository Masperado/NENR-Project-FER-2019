package hr.fer.zemris.nenr.hw05;

import java.util.ArrayList;
import java.util.List;


public class Neuron {

    private boolean isInputLayer;

    private double y;

    private double w0;

    private double delta;

    private int neuronNumber;

    private List<Double> weights;

    private List<Double> newWeights;

    public Neuron(boolean isInputLayer, int nextLayerSize, int neuronNumber) {
        this.isInputLayer = isInputLayer;
        this.weights = new ArrayList<>();
        this.neuronNumber = neuronNumber;
        delta = 0;
        initializeWeights(nextLayerSize);
    }

    private void initializeWeights(int size) {
        w0 = -0.2 + Math.random() * 0.4;
        for (int i = 0; i < size; i++) {
            weights.add(-0.2 + Math.random() * 0.4);
        }
        newWeights = new ArrayList<>(weights);
    }

    public void setW0(double w0) {
        this.w0 = w0;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(Sloj next) {
        delta = 0;
        for (int i = 0; i < next.size(); i++) {
            delta += getY() * (1 - getY()) * weights.get(i) * next.getNeuron(i).getDelta();
        }
    }

    public void updateWeights(Sloj next, double learningRate) {
        for (int i = 0; i < next.size(); i++) {
            newWeights.set(i, newWeights.get(i) + learningRate * next.getNeuron(i).getDelta() * getY());
        }
    }

    public double getY() {
        if (!isInputLayer) {
            return y;
        }
        return w0;
    }

    public void calcY(Sloj prev) {

        double net = 0;

        for (Neuron n : prev.getNeurons()) {
            net += n.getY() * n.weights.get(neuronNumber);
        }

        y = 1 / (1 + Math.exp(-net));
    }


    public void swapWeights() {
        weights = new ArrayList<>(newWeights);
    }

}