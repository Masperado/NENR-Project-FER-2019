package hr.fer.zemris.fuzzy.control;

import hr.fer.zemris.fuzzy.sets.IFuzzySet;


public class AkcelFuzzySystemMin extends FuzzySystem {
    public AkcelFuzzySystemMin(Defuzzifier def) {
        super(def);

        addRule(new IFuzzySet[]{null, null, null, null, BoatSets.TOO_SLOW, null}, BoatSets.SPEED_UP);
        addRule(new IFuzzySet[]{null, null, null, null, BoatSets.TOO_FAST, null}, BoatSets.SLOW_DOWN);

    }

}
