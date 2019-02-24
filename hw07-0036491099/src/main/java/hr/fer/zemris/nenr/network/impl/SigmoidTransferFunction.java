package hr.fer.zemris.nenr.network.impl;


import hr.fer.zemris.nenr.network.api.ITransferFunction;

public class SigmoidTransferFunction implements ITransferFunction {

	@Override
	public double valueAt(double x) {
		return 1.0 / (1.0 + Math.pow(Math.E, -x));
	}

}
