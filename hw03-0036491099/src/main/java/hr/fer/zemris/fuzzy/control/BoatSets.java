package hr.fer.zemris.fuzzy.control;

import hr.fer.zemris.fuzzy.sets.CalculatedFuzzySet;
import hr.fer.zemris.fuzzy.sets.IFuzzySet;
import hr.fer.zemris.fuzzy.sets.StandardFuzzySets;

public class BoatSets {

    public static final IFuzzySet EASY_TURN_LEFT = new CalculatedFuzzySet(BoatDomains.ANGLE,
            StandardFuzzySets.gammaFunction(150, 160));
    public static final IFuzzySet EASY_TURN_RIGHT = new CalculatedFuzzySet(BoatDomains.ANGLE,
            StandardFuzzySets.lFunction(20, 30));

    public static final IFuzzySet SHARP_TURN_LEFT = new CalculatedFuzzySet(BoatDomains.ANGLE,
            StandardFuzzySets.gammaFunction(160, 180));
    public static final IFuzzySet SHARP_TURN_RIGHT = new CalculatedFuzzySet(BoatDomains.ANGLE,
            StandardFuzzySets.lFunction(0, 20));

    public static final IFuzzySet NEAR_SHORE = new CalculatedFuzzySet(BoatDomains.DISTANCE,
            StandardFuzzySets.lFunction(50, 60));

    public static final IFuzzySet REALLY_NEAR_SHORE = new CalculatedFuzzySet(BoatDomains.DISTANCE,
            StandardFuzzySets.lFunction(40, 50));


    public static final IFuzzySet WRONG_WAY = new CalculatedFuzzySet(BoatDomains.DISTANCE, StandardFuzzySets.lFunction(0, 1));

    public static final IFuzzySet TOO_FAST = new CalculatedFuzzySet(BoatDomains.VELOCITY,
            StandardFuzzySets.gammaFunction(50, 60));
    public static final IFuzzySet TOO_SLOW = new CalculatedFuzzySet(BoatDomains.VELOCITY,
            StandardFuzzySets.lFunction(40, 50));

    public static final IFuzzySet SPEED_UP = new CalculatedFuzzySet(BoatDomains.ACCELERATION,
            StandardFuzzySets.gammaFunction(55, 70));
    public static final IFuzzySet SLOW_DOWN = new CalculatedFuzzySet(BoatDomains.ACCELERATION,
            StandardFuzzySets.lFunction(30, 45));
}