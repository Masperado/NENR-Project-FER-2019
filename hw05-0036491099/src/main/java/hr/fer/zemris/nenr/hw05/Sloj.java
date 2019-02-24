package hr.fer.zemris.nenr.hw05;

import java.util.ArrayList;
import java.util.List;


public class Sloj {

    private List<Neuron> neurons;

    public Sloj(int size, boolean isInputLayer, int nextLayerSize) {
        neurons = new ArrayList<>();
        fillLayer(size, isInputLayer, nextLayerSize);
    }

    public void fillLayer(int size, boolean isInputLayer, int nextLayerSize) {
        for (int i = 0; i < size; i++) {
            neurons.add(new Neuron(isInputLayer, nextLayerSize, i));
        }
    }

    public Neuron getNeuron(int i) {
        return neurons.get(i);
    }

    public void calcLayer(Sloj prev) {
        for (Neuron n : neurons) {
            n.calcY(prev);
        }
    }

    public int size() {
        return neurons.size();
    }

    public void updateDeltas(Sloj next) {
        for (Neuron n : neurons) {
            n.setDelta(next);
        }
    }

    public void updateWeights(Sloj next, double learningRate) {
        for (Neuron n : neurons) {
            n.updateWeights(next, learningRate);
        }
    }

    public void swapWeights() {
        for (Neuron n : neurons) {
            n.swapWeights();
        }
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }
}