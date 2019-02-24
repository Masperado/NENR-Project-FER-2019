package hr.fer.zemris.optjava.dz4.impl;

import hr.fer.zemris.optjava.dz4.api.IFunction;

public class AFFunction implements IFunction<double[]> {

    private double[][] inputs;
    private double[] consts;


    public AFFunction(double[][] inputs, double[] consts) {
        this.inputs = inputs;
        this.consts = consts;
    }

    @Override
    public double valueAt(double[] betaArray) {

        double error = 0;
        for (int i = 0; i < consts.length; i++) {
            double beta0 = betaArray[0];
            double beta1 = betaArray[1];
            double beta2 = betaArray[2];
            double beta3 = betaArray[3];
            double beta4 = betaArray[4];
            double x = inputs[i][0];
            double y = inputs[i][1];
            double K = consts[i];

            double result = Math.sin(beta0 + beta1 * x) + beta2 * Math.cos(x * (beta3 + y)) / (1 + Math.exp(Math.pow(x - beta4, 2))) - K;
            result *= result;
            error += result;
        }

        return error / consts.length;
    }

}
