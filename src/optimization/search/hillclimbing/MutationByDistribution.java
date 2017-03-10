/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.search.hillclimbing;

import optimization.distribution.Distribution;

/**
 * @author Oscar Garavito
 *
 */
public class MutationByDistribution implements Mutation<double[]> {

	/**
	 * Distribution to be used in variation.
	 */
	private final Distribution distribution;

	/**
	 * @param distribution
	 */
	public MutationByDistribution(Distribution distribution) {

		this.distribution = distribution;
	}

	@Override
	public double[] mutate(double[] origin) {

		double[] result = new double[origin.length];
		for (int i = 0; i < origin.length; i++) {
			double delta = distribution.nextRandom();
			result[i] = delta + origin[i];
		}
		return result;
	}

}
