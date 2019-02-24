package hr.fer.zemris.fuzzy.sets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CompositeDomain extends Domain {

    private List<SimpleDomain> domains = new ArrayList<>();

    public CompositeDomain(SimpleDomain... domains) {
        this.domains.addAll(Arrays.asList(domains));
    }

    @Override
    public int getCardinality() {
        int cardinality = domains.get(0).getCardinality();

        for (int i = 1; i < domains.size(); i++) {
            cardinality *= domains.get(i).getCardinality();
        }
        return cardinality;
    }

    @Override
    public IDomain getComponent(int index) {
        return domains.get(index);
    }

    @Override
    public int getNumberOfComponents() {
        return domains.size();
    }

    @Override
    public Iterator<DomainElement> iterator() {
        return new CompositeDomainIterator();
    }


    private class CompositeDomainIterator implements Iterator<DomainElement> {

        private List<Iterator<DomainElement>> iterators = new ArrayList<>();
        private List<Integer> currentComponentValues = new ArrayList<>();
        private int maxSize = getCardinality();
        private int currentIndex = 0;

        public CompositeDomainIterator() {
            for (var domain : domains) {
                iterators.add(domain.iterator());
            }
            for (var iterator : iterators) {
                currentComponentValues.add(iterator.next().getComponentValue(0));
            }
        }

        @Override
        public boolean hasNext() {
            return currentIndex < maxSize;
        }

        @Override
        public DomainElement next() {
            if (currentIndex == 0) {
                currentIndex++;
                return new DomainElement(currentComponentValues);
            }
            for (int i = iterators.size() - 1; i >= 0; i--) {
                if (iterators.get(i).hasNext()) {
                    currentComponentValues.set(i, iterators.get(i).next().getComponentValue(0));
                    break;
                } else {
                    iterators.set(i, domains.get(i).iterator());
                    currentComponentValues.set(i, iterators.get(i).next().getComponentValue(0));
                }
            }
            currentIndex++;
            return new DomainElement(currentComponentValues);
        }
    }
}
