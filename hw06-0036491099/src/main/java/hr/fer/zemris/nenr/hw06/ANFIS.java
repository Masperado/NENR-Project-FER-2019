package hr.fer.zemris.nenr.hw06;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ANFIS {

    private double learningRate;
    private int maxIter;
    private double sumW;
    private double sumWZ;

    List<Rule> rules;
    List<Sample> samples;

    public ANFIS(int noRules, int maxIter, double learningRate) {

        this.learningRate = learningRate;
        this.maxIter = maxIter;

        rules = new ArrayList<>();
        for (int i = 0; i < noRules; i++) {
            rules.add(new Rule());
        }

        samples = new ArrayList<>();
        for (int x = -4; x <= 4; x++) {
            for (int y = -4; y <= 4; y++) {
                samples.add(new Sample(x, y));
            }
        }

        Collections.shuffle(samples);

    }

    private void plotSamples() {
        StringBuilder sb = new StringBuilder();

        sb.append("x,y,z1,z2,dif\n");

        for (Sample sample : samples) {
            double eval = evaluate(sample.x, sample.y);

            sb.append(sample.x).append(",").append(sample.y).append(",").append(sample.z).append(",").append(eval).append(",").append(sample.z - eval).append("\n");
        }

        try {
            Files.write(Paths.get("sample.csv"), sb.toString().getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private double evaluate(double x, double y) {
        sumW = 0;
        sumWZ = 0;
        for (Rule r : rules) {
            double w = r.getW(x, y);
            sumW += w;
            sumWZ += w * r.getF(x, y);
        }
        return sumWZ / sumW;
    }


    private double error() {
        double ret = 0;
        for (Sample s : samples) {
            ret += Math.pow(evaluate(s.x, s.y) - s.z, 2);
        }
        return ret / samples.size();
    }

    public void batch() {

        StringBuilder sb = new StringBuilder();
        sb.append("error\n");

        for (int i = 0; i < maxIter; i++) {

            for (Sample s : samples) {
                double o = evaluate(s.x, s.y);
                for (Rule r : rules) {
                    double z = r.getF(s.x, s.y);
                    r.updateDerivatives(s, o, sumW, sumW * z - sumWZ, learningRate);
                }
            }

            for (Rule r : rules) {
                r.swap();
            }

            if (i % 100 == 0) {
                double error = error();
                sb.append(String.format("%6f\n", error));
                System.out.println(i + " | Error: " + error);
            }

        }

        try {
            Files.write(Paths.get("error.csv"), sb.toString().getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        plotSamples();

        plotRules();


    }

    private void plotRules() {

        StringBuilder sb = new StringBuilder();
        sb.append("x");
        for (int i = 0; i < rules.size(); i++) {
            sb.append(",").append("r").append(i);
        }
        sb.append("\n");

        for (int i = -4; i <= 4; i++) {
            sb.append(i);
            for (int j = 0; j < rules.size(); j++) {
                sb.append(",").append(rules.get(j).abSigmoid(i));
            }
            sb.append("\n");
        }

        try {
            Files.write(Paths.get("absigmoid.csv"), sb.toString().getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        sb.setLength(0);

        sb.append("y");
        for (int i = 0; i < rules.size(); i++) {
            sb.append(",").append("r").append(i);
        }
        sb.append("\n");

        for (int i = -4; i <= 4; i++) {
            sb.append(i);
            for (int j = 0; j < rules.size(); j++) {
                sb.append(",").append(rules.get(j).cdSigmoid(i));
            }
            sb.append("\n");
        }

        try {
            Files.write(Paths.get("cdsigmoid.csv"), sb.toString().getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void stohastic() {

        StringBuilder sb = new StringBuilder();
        sb.append("error\n");


        for (int i = 0; i < maxIter; i++) {

            Sample currSample = samples.get(i % samples.size());
            double o = evaluate(currSample.x, currSample.y);

            for (Rule r : rules) {
                double z = r.getF(currSample.x, currSample.y);
                r.updateDerivatives(currSample, o, sumW, sumW * z - sumWZ, learningRate);
                r.swap();
            }

            if (i % 100 == 0) {
                double error = error();
                sb.append(String.format("%6f\n", error));
                System.out.println(i + " | Error: " + error);
            }

        }

        try {
            Files.write(Paths.get("error.csv"), sb.toString().getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        plotSamples();

        plotRules();

    }

    class Sample {

        double x, y, z;

        Sample(double x, double y) {
            this.x = x;
            this.y = y;
            this.z = (Math.pow(x - 1, 2) + Math.pow(y + 2, 2) - 5 * x * y + 3) * Math.pow(Math.cos(x / 5), 2);
        }

    }


    public static void main(String[] args) {
        ANFIS neuroFuzzy = new ANFIS(20, 100000, 0.001);
        neuroFuzzy.batch();
    }


}