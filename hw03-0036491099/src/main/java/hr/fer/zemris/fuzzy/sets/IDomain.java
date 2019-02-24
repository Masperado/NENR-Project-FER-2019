package hr.fer.zemris.fuzzy.sets;

public interface IDomain extends Iterable<DomainElement> {

    int getCardinality();

    IDomain getComponent(int index);

    int getNumberOfComponents();

    int indexOfElement(DomainElement element);

    DomainElement elementForIndex(int index);


}
