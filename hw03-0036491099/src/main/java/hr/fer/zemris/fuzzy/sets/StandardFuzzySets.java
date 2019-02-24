package hr.fer.zemris.fuzzy.sets;

public class StandardFuzzySets {

    public static IIntUnaryFUnction lFunction(int alfa, int beta) {
        return element -> {
            if (alfa > beta) {
                throw new IllegalArgumentException("Alfa can't be higher than beta!");
            }

            if (element < alfa) {
                return 1;
            } else if (element >= beta) {
                return 0;
            } else {
                return (beta - element) / 1.0 / (beta - alfa);
            }
        };
    }

    public static IIntUnaryFUnction gammaFunction(int alfa, int beta) {
        if (alfa > beta) {
            throw new IllegalArgumentException("Alfa can't be higher than beta!");
        }

        return element -> {
            if (element < alfa) {
                return 0;
            } else if (element >= beta) {
                return 1;
            } else {
                return (element / 1.0 - alfa) / (beta - alfa);
            }
        };
    }

    public static IIntUnaryFUnction lambdaFunction(int alfa, int beta, int gamma) {
        if (alfa > beta || alfa > gamma || beta > gamma) {
            throw new IllegalArgumentException("Alfa can't be higher than beta and beta can't be higher than gamma!");
        }

        return element -> {
            if (element < alfa) {
                return 0;
            } else if (element >= gamma) {
                return 0;
            } else if (element >= alfa && element < beta) {
                return (element - alfa) / 1.0 / (beta - alfa);
            } else {
                return (gamma - element) / 1.0 / (gamma - beta);
            }
        };
    }
}
