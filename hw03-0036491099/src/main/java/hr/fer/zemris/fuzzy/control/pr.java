package hr.fer.zemris.fuzzy.control;

public class pr {

    public static void main(String[] args) {
        Defuzzifier def = new COADefuzzifier();
        FuzzySystem fsKormilo = new KormiloFuzzySystemMin(def);
        System.out.println(fsKormilo.zakljuci(new int[]{84, 54, 1300, 39, 50, 1}));
    }
}
