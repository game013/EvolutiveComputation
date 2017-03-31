/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator;

import optimization.distribution.Distribution;
import optimization.distribution.NormalDistribution;

/**
 * Implementation of mutator adapter for one-fifth rule.
 * 
 * @author Oscar Garavito
 *
 */
public class OneFifthRule<D, C> implements MutatorAdapter<D, C> {

	/**
	 * One fifth constant.
	 */
	private static final double ONE_FIFTH = 0.2;

	/**
	 * Iteration period.
	 */
	public final int iterationPeriod;

	/**
	 * Alpha to adjust sigma value in normal distribution.
	 */
	public final double alpha;

	/**
	 * Current iteration.
	 */
	public int currentIteration;

	/**
	 * Quantity of improvements made between iteration periods.
	 */
	public int improvements;

	/**
	 * @param iterationPeriod
	 *            Indicates how many iterations must past to evaluate rule.
	 * @param alpha
	 *            Parameter to adjust sigma value in normal distribution.
	 */
	public OneFifthRule(int iterationPeriod, double alpha) {

		this.iterationPeriod = iterationPeriod;
		this.alpha = alpha;
		this.currentIteration = 0;
		this.improvements = 0;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.operator.MutatorAdapter#adaptMutator(optimization.
	 * genetic.operator.GeneticMutator, boolean)
	 */
	@Override
	public GeneticMutator<D, C> adaptMutator(GeneticMutator<D, C> mutator, boolean wasLastBetter) {

		this.currentIteration++;
		if (wasLastBetter) {
			this.improvements++;
		}
		if (this.currentIteration == this.iterationPeriod) {
			if (MutationByDistribution.class.isAssignableFrom(mutator.getClass())) {
				MutationByDistribution castedMutator = MutationByDistribution.class.cast(mutator);
				Distribution distribution = castedMutator.getDistribution();
				if (NormalDistribution.class.isAssignableFrom(distribution.getClass())) {
					NormalDistribution normalDistribution = NormalDistribution.class.cast(distribution);
					normalDistribution.setSigma(this.apply(normalDistribution.getSigma()));
				}
			}
			this.currentIteration = 0;
			this.improvements = 0;
		}
		return mutator;
	}

	/**
	 * Applies change to sigma if it's required.
	 * 
	 * @param sigma
	 *            Current sigma parameter.
	 * @return New sigma parameter calculated by one-fifth rule.
	 */
	private double apply(double sigma) {

		double newSigma = sigma;
		double performance = (double) this.improvements / (double) this.iterationPeriod;
		if (performance > ONE_FIFTH) {
			newSigma = sigma / this.alpha;
		} else if (performance < ONE_FIFTH) {
			newSigma = sigma * this.alpha;
		}
		return newSigma;
	}

}
