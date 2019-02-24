package hr.fer.zemris.fuzzy.control;

import hr.fer.zemris.fuzzy.sets.IFuzzySet;

public interface Defuzzifier {
    int defuzzify(IFuzzySet set);
}
