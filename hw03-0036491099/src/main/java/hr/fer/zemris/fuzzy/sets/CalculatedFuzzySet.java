package hr.fer.zemris.fuzzy.sets;

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

    @Override
    public IFuzzySet cutoff(double mi) {
        MutableFuzzySet cutted = new MutableFuzzySet(this.domain);
        for (var e : domain) {
            cutted = cutted.set(e, getValueAt(e) * mi);
        }
        return cutted;
    }
}
