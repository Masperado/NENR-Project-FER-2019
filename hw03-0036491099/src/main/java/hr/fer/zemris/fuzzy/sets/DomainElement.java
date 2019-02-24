package hr.fer.zemris.fuzzy.sets;

import java.util.Arrays;
import java.util.List;

public class DomainElement {

    private int[] values;

    public DomainElement(int... values) {
        this.values = values;
    }

    public DomainElement(List<Integer> currentComponentValues) {
        this.values = currentComponentValues.stream().mapToInt(i -> i).toArray();
    }


    public int getNumberOfComponents() {
        return values.length;
    }

    public int getComponentValue(int index) {
        if (index < 0 || index >= values.length) {
            throw new IndexOutOfBoundsException();
        }

        return values[index];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainElement that = (DomainElement) o;
        return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (var i : values) {
            sb.append(i + ",");
        }
        sb.setLength(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }

    public static DomainElement of(int... values) {
        return new DomainElement(values);
    }
}
