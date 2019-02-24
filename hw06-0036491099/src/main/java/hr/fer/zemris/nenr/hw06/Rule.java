package hr.fer.zemris.nenr.hw06;

import java.util.Random;

public class Rule {

    private double a;
    private double b;
    private double c;
    private double d;
    private double p;
    private double q;
    private double r;


    private double newA;
    private double newB;
    private double newC;
    private double newD;

    private double newP;
    private double newQ;
    private double newR;


    private Random rand = new Random();

    public Rule() {

        a = -1 + 2 * rand.nextDouble();
        b = -1 + 2 * rand.nextDouble();
        c = -1 + 2 * rand.nextDouble();
        d = -1 + 2 * rand.nextDouble();

        p = -1 + 2 * rand.nextDouble();
        q = -1 + 2 * rand.nextDouble();
        r = -1 + 2 * rand.nextDouble();

        newA = a;
        newB = b;
        newC = c;
        newD = d;

        newP = p;
        newQ = q;
        newR = r;

    }

    public double getF(double x, double y) {
        return p * x + q * y + r;
    }

    public double getW(double x, double y) {
        return sigmoid(a, b, x) * sigmoid(c, d, y);
    }

    public double abSigmoid(double x) {
        return sigmoid(a, b, x);
    }

    public double cdSigmoid(double y) {
        return sigmoid(c, d, y);
    }

    private double sigmoid(double a, double b, double x) {
        return 1.0 / (1 + Math.exp(b * (x - a)));
    }

    public void updateDerivatives(ANFIS.Sample s, double o, double sumW, double sumWZ, double learningRate) {

        double w = getW(s.x, s.y);
        double alpha = sigmoid(a, b, s.x);
        double beta = sigmoid(c, d, s.y);

        newP += learningRate * (s.z - o) * w / sumW * s.x;
        newQ += learningRate * (s.z - o) * w / sumW * s.y;
        newR += learningRate * (s.z - o) * w / sumW;

        newA += learningRate * (s.z - o) * sumWZ / (sumW * sumW) * beta * b * alpha * (1 - alpha);
        newB += learningRate * (s.z - o) * sumWZ / (sumW * sumW) * beta * (a - s.x) * alpha * (1 - alpha);
        newC += learningRate * (s.z - o) * sumWZ / (sumW * sumW) * alpha * d * beta * (1 - beta);
        newD += learningRate * (s.z - o) * sumWZ / (sumW * sumW) * alpha * (c - s.y) * beta * (1 - beta);

    }

    public void swap() {

        p = newP;
        q = newQ;
        r = newR;

        a = newA;
        b = newB;
        c = newC;
        d = newD;

    }

}