package hr.fer.zemris.fuzzy;

public class CalculatedFuzzySet implements IFuzzySet {

    private IIntUnaryFUnction function;
    private IDomain domain;

    public CalculatedFuzzySet(IDomain domain, IIntUnaryFUnction function) {
        this.function = function;
        this.domain = domain;
    }

    @Override
    public IDomain getDomain() {
        return domain;
    }

    @Override
    public double getValueAt(DomainElement element) {
        return function.valueAt(domain.indexOfElement(element));
    }
}
