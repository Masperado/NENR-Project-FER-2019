package hr.fer.zemris.fuzzy.control;

import hr.fer.zemris.fuzzy.sets.IFuzzySet;

public class KormiloFuzzySystemMin extends FuzzySystem {

    public KormiloFuzzySystemMin(Defuzzifier def) {
        super(def);

        addRule(new IFuzzySet[]{null, null, BoatSets.REALLY_NEAR_SHORE, null, null, null}, BoatSets.SHARP_TURN_RIGHT);

        addRule(new IFuzzySet[]{null, null, null, BoatSets.REALLY_NEAR_SHORE, null, null}, BoatSets.SHARP_TURN_LEFT);

        addRule(new IFuzzySet[]{null, null, BoatSets.NEAR_SHORE, null, null, null}, BoatSets.EASY_TURN_RIGHT);

        addRule(new IFuzzySet[]{null, null, null, BoatSets.NEAR_SHORE, null, null}, BoatSets.EASY_TURN_LEFT);

//        addRule(new IFuzzySet[]{BoatSets.NEAR_SHORE, null, null, null, null, null}, BoatSets.EASY_TURN_RIGHT);
//
//        addRule(new IFuzzySet[]{null, BoatSets.NEAR_SHORE, null, null, null, null}, BoatSets.EASY_TURN_LEFT);

        addRule(new IFuzzySet[]{null, null, null, null, null, BoatSets.WRONG_WAY}, BoatSets.SHARP_TURN_LEFT);

    }


}
