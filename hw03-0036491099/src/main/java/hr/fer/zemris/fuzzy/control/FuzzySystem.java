package hr.fer.zemris.fuzzy.control;

import hr.fer.zemris.fuzzy.sets.IFuzzySet;
import hr.fer.zemris.fuzzy.sets.Operations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class FuzzySystem {

    protected List<Rule> rules = new ArrayList<>();
    protected Defuzzifier def;

    public FuzzySystem(Defuzzifier def) {
        this.def = def;
    }

    public int zakljuci(int... values) {

        return def.defuzzify(zakljuciBezDefuz(values));
    }

    public IFuzzySet zakljuciBezDefuz(int... values) {

        List<IFuzzySet> applied = new ArrayList<>();
        for (var rule : rules) {
            applied.add(rule.apply(values));
        }

        IFuzzySet result = applied.get(0);

        for (int i = 1; i < applied.size(); i++) {
            result = Operations.binaryOperation(result, applied.get(i), Operations.zadehOr());
        }

        return result;
    }


    public Rule getRule(int index) {
        return rules.get(index);
    }

    protected void addRule(IFuzzySet[] antecedent, IFuzzySet konsekvent) {
        rules.add(new Rule(Arrays.asList(antecedent), konsekvent));
    }
}
