package hr.fer.zemris.fuzzy.control;

import java.io.*;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {

        Defuzzifier def = new COADefuzzifier();
        FuzzySystem fsAkcel = new AkcelFuzzySystemMin(def);
        FuzzySystem fsKormilo = new KormiloFuzzySystemMin(def);


        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        int L = 0, D = 0, LK = 0, DK = 0, V = 0, S = 0, akcel, kormilo;
        String line;
        while (true) {
            if ((line = input.readLine()) != null) {
                if (line.charAt(0) == 'K') break;
                Scanner s = new Scanner(line);
                L = s.nextInt();
                D = s.nextInt();
                LK = s.nextInt();
                DK = s.nextInt();
                V = s.nextInt();
                S = s.nextInt();
            }



            akcel = fsAkcel.zakljuci(L, D, LK, DK, V, S);
            kormilo = fsKormilo.zakljuci(L, D, LK, DK, V, S);
            System.out.println(akcel + " " + kormilo);
            System.out.flush();

            String output = String.format("%d %d %d %d %d %d %d %d",L,D,LK,DK,V,S,akcel,kormilo);

            System.err.println(output);

        }
    }

}

