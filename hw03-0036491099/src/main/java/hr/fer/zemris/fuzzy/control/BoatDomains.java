package hr.fer.zemris.fuzzy.control;

import hr.fer.zemris.fuzzy.sets.IDomain;
import hr.fer.zemris.fuzzy.sets.SimpleDomain;

public class BoatDomains {

    public static final IDomain ANGLE = new SimpleDomain(-90, 91);

    public static final IDomain DISTANCE = new SimpleDomain(0, 1301);

    public static final IDomain VELOCITY = new SimpleDomain(0, 101);

    public static final IDomain ACCELERATION = new SimpleDomain(-50, 51);

}