package hr.fer.zemris.fuzzy;

public class MutableFuzzySet implements IFuzzySet {

    private double[] memberships;
    private IDomain domain;

    public MutableFuzzySet(IDomain domain) {
        this.domain = domain;
        this.memberships = new double[domain.getCardinality()];
    }

    @Override
    public IDomain getDomain() {
        return domain;
    }

    @Override
    public double getValueAt(DomainElement element) {
        int index = domain.indexOfElement(element);

        if (index < 0 || index > memberships.length) {
            throw new IllegalArgumentException("No such element in domain!");
        }

        return memberships[index];
    }

    public MutableFuzzySet set(DomainElement element, double value) {
        int index = domain.indexOfElement(element);

        if (index < 0 || index > memberships.length) {
            throw new IllegalArgumentException("No such element in domain!");
        }

        memberships[index] = value;

        return this;
    }
}
