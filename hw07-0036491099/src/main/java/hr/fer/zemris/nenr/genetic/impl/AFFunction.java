package hr.fer.zemris.nenr.genetic.impl;


import hr.fer.zemris.nenr.FFANN;
import hr.fer.zemris.nenr.genetic.api.IFunction;

public class AFFunction implements IFunction<double[]> {


    private FFANN ffann;

    public AFFunction(FFANN ffann) {
        this.ffann = ffann;
    }

    @Override
    public double valueAt(double[] weights) {

        return ffann.errorFunction(weights);
    }

}
