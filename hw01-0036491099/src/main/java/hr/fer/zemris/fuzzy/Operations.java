package hr.fer.zemris.fuzzy;

public class Operations {

    public static IFuzzySet unaryOperation(IFuzzySet set, IUnaryFunction function) {
        MutableFuzzySet resultSet = new MutableFuzzySet(set.getDomain());

        for (DomainElement e : set.getDomain()) {
            resultSet = resultSet.set(e, function.valueAt(set.getValueAt(e)));
        }

        return resultSet;
    }

    public static IFuzzySet binaryOperation(IFuzzySet set1, IFuzzySet set2, IBinaryFunction function) {
        if (!(set1.getDomain().equals(set2.getDomain()))) {
            throw new IllegalArgumentException("Sets must be defined on same domain!");
        }

        MutableFuzzySet resultSet = new MutableFuzzySet(set1.getDomain());

        for (DomainElement e : set1.getDomain()) {
            resultSet = resultSet.set(e, function.valueAt(set1.getValueAt(e), set2.getValueAt(e)));
        }

        return resultSet;
    }

    public static IUnaryFunction zadehNot() {
        return a -> 1 - a;
    }

    public static IBinaryFunction zadehAnd() {
        return Double::min;
    }

    public static IBinaryFunction zadehOr() {
        return Double::max;
    }

    public static IBinaryFunction hamacherTNorm(double parameter) {
        return (a, b) -> (a * b) / (parameter + (1 - parameter) * (a + b - a * b));
    }

    public static IBinaryFunction hamacherSNorm(double parameter) {
        return (a, b) -> (a + b - (2 - parameter) * a * b) / (1 - (1 - parameter) * a * b);
    }


}
