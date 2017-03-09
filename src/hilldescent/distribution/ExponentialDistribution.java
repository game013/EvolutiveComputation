/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package hilldescent.distribution;

/**
 * @author Oscar Garavito
 *
 */
public class ExponentialDistribution extends AbstractMonotonicDistribution implements Distribution {

	public final double lambda;

	/**
	 * @param lambda
	 */
	public ExponentialDistribution(double lambda) {

		this.lambda = lambda;
	}

	/*
	 * (non-Javadoc)
	 * @see hilldescent.AbstractMonotonicDistribution#calculateInverse(double)
	 */
	@Override
	protected double calculateInverse(double uniformRandom) {

		return -1 * Math.log(uniformRandom) / this.lambda;
	}

	/**
	 * @return the lambda
	 */
	public double getLambda() {
		return lambda;
	}

}
