package hr.fer.zemris.nenr.network.impl;

import hr.fer.zemris.nenr.network.api.ITransferFunction;

public class IdentityTransferFunction implements ITransferFunction {

    @Override
    public double valueAt(double x) {
        return x;
    }
}
