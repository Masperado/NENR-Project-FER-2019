package hr.fer.zemris.fuzzy.control;

import hr.fer.zemris.fuzzy.sets.Debug;
import hr.fer.zemris.fuzzy.sets.IFuzzySet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Help2 {

    public static void main(String[] args) throws IOException {

        Defuzzifier def = new COADefuzzifier();
        FuzzySystem fsAkcel = new AkcelFuzzySystemMin(def);
        FuzzySystem fsKormilo = new KormiloFuzzySystemMin(def);


        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("UNESITE L, D, LK, DK, V, S: \n");
        int L = 0, D = 0, LK = 0, DK = 0, V = 0, S = 0, akcel, kormilo;
        String line;
        if ((line = input.readLine()) != null) {
            Scanner s = new Scanner(line);
            L = s.nextInt();
            D = s.nextInt();
            LK = s.nextInt();
            DK = s.nextInt();
            V = s.nextInt();
            S = s.nextInt();
        }

        IFuzzySet akcelSet = fsAkcel.zakljuciBezDefuz(L, D, LK, DK, V, S);
        akcel = fsAkcel.zakljuci(L, D, LK, DK, V, S);

        IFuzzySet kormiloSet = fsKormilo.zakljuciBezDefuz(L, D, LK, DK, V, S);
        kormilo = fsKormilo.zakljuci(L, D, LK, DK, V, S);
        System.out.println("AKCELERACIJA SET:");
        Debug.print(akcelSet, "AKCELERACIJA");
        System.out.println("AKCELERACIJA: " + akcel);
        Debug.print(kormiloSet, "KORMILO");
        System.out.println("KORMILO: " + kormilo);
    }
}
