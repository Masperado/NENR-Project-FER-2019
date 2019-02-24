package hr.fer.zemris.fuzzy;

import java.util.Objects;

public abstract class Domain implements IDomain {

    public static IDomain intRange(int first, int last) {
        return new SimpleDomain(first, last);
    }

    public static IDomain combine(IDomain d1, IDomain d2) {
        if (!(d1 instanceof SimpleDomain && d2 instanceof SimpleDomain)) {
            throw new IllegalArgumentException("Domains must be simple!");
        }

        return new CompositeDomain((SimpleDomain) d1, (SimpleDomain) d2);
    }

    @Override
    public int indexOfElement(DomainElement element) {
        Objects.requireNonNull(element, "Element can't be null!");

        int index = 0;
        for (var el : this) {
            if (el.equals(element)) {
                break;
            }
            index++;
        }
        if (index > this.getCardinality()) {
            return -1;
        }
        return index;
    }

    @Override
    public DomainElement elementForIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index can't be smallet than 0");
        }
        for (var el : this) {
            index--;
            if (index == -1) {
                return el;
            }
        }
        throw new ArrayIndexOutOfBoundsException("Index too high!");
    }
}
