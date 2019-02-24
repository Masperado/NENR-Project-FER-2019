package hr.fer.zemris.fuzzy.sets;

public interface IFuzzySet {

    IDomain getDomain();

    double getValueAt(DomainElement element);

    IFuzzySet cutoff(double mi);
}
