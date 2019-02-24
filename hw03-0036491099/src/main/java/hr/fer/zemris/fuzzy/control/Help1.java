package hr.fer.zemris.fuzzy.control;

import hr.fer.zemris.fuzzy.sets.Debug;
import hr.fer.zemris.fuzzy.sets.IFuzzySet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Help1 {

    public static void main(String[] args) throws IOException {

        Defuzzifier def = new COADefuzzifier();
        FuzzySystem fs = new AkcelFuzzySystemMin(def);


        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        String line;

        System.out.println("A ZA AKCELERACIJU, K ZA KORMILO:");

        if ((line = input.readLine()) != null) {
            Scanner s = new Scanner(line);
            String str = s.next();
            if (str.equals("A")) {
                fs = new AkcelFuzzySystemMin(def);
            } else if (str.equals("K")) {
                fs = new KormiloFuzzySystemMin(def);
            } else {
                throw new IllegalArgumentException("NEPOZNATA BAZA!");
            }
        }

        int index = 0;

        System.out.println("UNESITE BROJ PRAVILA:");
        if ((line = input.readLine()) != null) {
            Scanner s = new Scanner(line);
            index = s.nextInt();
        }

        Rule rule = fs.getRule(index);

        System.out.println("UNESITE L, D, LK, DK, V, S: \n");
        int L = 0, D = 0, LK = 0, DK = 0, V = 0, S = 0;
        if ((line = input.readLine()) != null) {
            Scanner s = new Scanner(line);
            L = s.nextInt();
            D = s.nextInt();
            LK = s.nextInt();
            DK = s.nextInt();
            V = s.nextInt();
            S = s.nextInt();
        }

        IFuzzySet set = rule.apply(L, D, LK, DK, V, S);
        int value = def.defuzzify(set);

        System.out.println("SET:");
        Debug.print(set, "PRAVILO");
        System.out.println("VRIJEDNOST: " + value);
    }
}
