package hr.fer.zemris.nenr.hw05;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException {
        Mreza kvadratna = new Mreza("kvadratna.txt",10000,3,4,1,6,6,1);
        kvadratna.learn();

        List<Double> sample = new ArrayList<>();
        sample.add(1.0);

        kvadratna.evaluate(sample);

        System.out.println(kvadratna.getValue());
    }
}
