/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator.mutation;

import optimization.distribution.Distribution;
import optimization.function.fitness.Function;
import optimization.function.space.Space;

/**
 * @author Oscar Garavito
 *
 */
public class MutationByDistribution implements GeneticMutator<double[], Double> {

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

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.operator.GeneticMutator#mutate(java.lang.Object,
	 * optimization.function.fitness.Function,
	 * optimization.function.space.Space)
	 */
	@Override
	public double[] mutate(double[] origin, Function<double[], Double> fitnessFunction, Space<double[]> space) {

		double[] result = new double[origin.length];
		for (int i = 0; i < origin.length; i++) {
			double delta = distribution.nextRandom();
			result[i] = delta + origin[i];
		}
		return result;
	}

	/**
	 * @return the distribution
	 */
	public Distribution getDistribution() {
		return distribution;
	}

}
