package hr.fer.zemris.fuzzy.sets;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Relations {

    public static boolean isUtimesURelation(IFuzzySet relation) {
        return relation.getDomain().getNumberOfComponents() == 2;
    }

    public static boolean isSymmetric(IFuzzySet relation) {

        if (!isUtimesURelation(relation)) {
            return false;
        }

        for (var element : relation.getDomain()) {
            DomainElement simetric = DomainElement.of(element.getComponentValue(1), element.getComponentValue(0));
            if (relation.getValueAt(element) != relation.getValueAt(simetric)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isReflexive(IFuzzySet relation) {

        if (!isUtimesURelation(relation)) {
            return false;
        }

        for (var element : relation.getDomain()) {
            if (element.getComponentValue(0) == element.getComponentValue(1) && relation.getValueAt(element) != 1) {
                return false;
            }
        }

        return true;

    }

    public static boolean isMaxMinTransitive(IFuzzySet relation) {

        if (!isUtimesURelation(relation)) {
            return false;
        }

        for (var element1 : relation.getDomain()) {
            for (var element2 : relation.getDomain()) {
                if (element1.getComponentValue(1) != element2.getComponentValue(0)) {
                    continue;
                }
                if (relation.getValueAt(DomainElement.of(element1.getComponentValue(0),
                        element2.getComponentValue(1))) < Math.min(relation.getValueAt(element1),
                        relation.getValueAt(element2))) {
                    return false;
                }
            }
        }

        return true;

    }


    public static IFuzzySet compositionOfBinaryRelations(IFuzzySet r1, IFuzzySet r2) {

        IDomain x = r1.getDomain().getComponent(0);
        IDomain y = r1.getDomain().getComponent(1);
        IDomain z = r2.getDomain().getComponent(1);

        if (!y.equals(r2.getDomain().getComponent(0))) {
            throw new IllegalArgumentException("Sets must be of same domain!");
        }

        MutableFuzzySet composition = new MutableFuzzySet(new CompositeDomain((SimpleDomain) x, (SimpleDomain) z));

        for (var element1 : x) {
            for (var element3 : z) {
                List<Double> values = new ArrayList<>();
                for (var element2 : y) {
                    values.add(Math.min(
                            r1.getValueAt(DomainElement.of(element1.getComponentValue(0), element2.getComponentValue(0))),
                            r2.getValueAt(DomainElement.of(element2.getComponentValue(0),
                                    element3.getComponentValue(0)))));
                }
                values.sort(Comparator.reverseOrder());
                composition.set(DomainElement.of(element1.getComponentValue(0), element3.getComponentValue(0)),
                        values.get(0));
            }
        }

        return composition;
    }

    public static boolean isFuzzyEquivalence(IFuzzySet r) {
        return Relations.isReflexive(r) && Relations.isMaxMinTransitive(r) && Relations.isSymmetric(r);
    }
}
