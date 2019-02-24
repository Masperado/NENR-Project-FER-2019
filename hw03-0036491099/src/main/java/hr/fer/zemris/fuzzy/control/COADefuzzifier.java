package hr.fer.zemris.fuzzy.control;

import hr.fer.zemris.fuzzy.sets.IFuzzySet;

public class COADefuzzifier implements Defuzzifier {

    @Override
    public int defuzzify(IFuzzySet set) {
        double broj = 0;
        double naz = 0;
        for (var e : set.getDomain()) {
            broj += set.getValueAt(e) * e.getComponentValue(0);
            naz += set.getValueAt(e);
        }
        return (int) (broj / naz);
    }
}
