/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator.mutation;

import java.util.Optional;

import optimization.distribution.Distribution;
import optimization.distribution.ParametricalDistribution;
import optimization.function.fitness.Function;
import optimization.function.space.Space;
import optimization.util.type.Solution;
import optimization.util.type.SolutionParameter;
import optimization.util.type.constant.ParameterName;

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
	 * optimization.genetic.operator.mutation.GeneticMutator#mutate(optimization
	 * .util.type.Solution, optimization.function.fitness.Function,
	 * optimization.function.space.Space)
	 */
	@Override
	public Solution<double[], Double> mutate(Solution<double[], Double> child,
			Function<double[], Double> fitnessFunction, Space<double[]> space) {

		// Mutation of endogenous parameter
		Optional<SolutionParameter> parameters = child.getParameters();
		if (parameters.isPresent() && Math.random() < 0.05) {
			double oldSigma = (double) parameters.get().get(ParameterName.SIGMA);
			double alpha = 1.0 + 1.0 / Math.sqrt(20);
			double newSigma = Math.random() >= 0.5 ? oldSigma * alpha : oldSigma / alpha;
			parameters.get().set(ParameterName.SIGMA, newSigma);
		}

		if (ParametricalDistribution.class.isAssignableFrom(this.distribution.getClass())) {
			((ParametricalDistribution) this.distribution).setParameters(parameters);
		}

		Solution<double[], Double> solution = GeneticMutator.super.mutate(child, fitnessFunction, space);
		return solution;
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
