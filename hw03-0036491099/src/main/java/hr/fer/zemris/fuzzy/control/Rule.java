package hr.fer.zemris.fuzzy.control;

import hr.fer.zemris.fuzzy.sets.DomainElement;
import hr.fer.zemris.fuzzy.sets.IFuzzySet;

import java.util.List;

public class Rule {

    private List<IFuzzySet> antecedent;

    private IFuzzySet konsekvent;

    public Rule(List<IFuzzySet> antecedent, IFuzzySet konsekvent) {
        this.antecedent = antecedent;
        this.konsekvent = konsekvent;
    }

    public IFuzzySet apply(int... values) {
        double mi = 1;
        for (int i = 0; i < antecedent.size(); i++) {
            if (antecedent.get(i) == null) {
                continue;
            }

//             OVO JE RAZLIKA IZMEĐU STROJA ZA MINIMUM I STROJA ZA PRODUKT
//            mi *= antecedent.get(i).getValueAt(DomainElement.of(values[i]));
            mi = Double.min(mi, antecedent.get(i).getValueAt(DomainElement.of(values[i])));
        }
        return konsekvent.cutoff(mi);
    }


}
