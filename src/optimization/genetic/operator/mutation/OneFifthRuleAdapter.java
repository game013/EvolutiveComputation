/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator.mutation;

import optimization.distribution.Distribution;
import optimization.distribution.NormalDistribution;
import optimization.util.OneFifthRule;

/**
 * Implementation of mutator adapter for one-fifth rule.
 * 
 * @author Oscar Garavito
 *
 */
public class OneFifthRuleAdapter<D, C> implements MutatorAdapter<D, C> {

	/**
	 * 
	 */
	private final OneFifthRule rule;

	/**
	 * @param iterationPeriod
	 *            Indicates how many iterations must past to evaluate rule.
	 * @param alpha
	 *            Parameter to adjust sigma value in normal distribution.
	 */
	public OneFifthRuleAdapter(OneFifthRule rule) {

		this.rule = rule;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.operator.MutatorAdapter#adaptMutator(optimization.
	 * genetic.operator.GeneticMutator, boolean)
	 */
	@Override
	public GeneticMutator<D, C> adaptMutator(GeneticMutator<D, C> mutator, boolean wasLastBetter) {

		if (MutationByDistribution.class.isAssignableFrom(mutator.getClass())) {
			MutationByDistribution castedMutator = MutationByDistribution.class.cast(mutator);
			Distribution distribution = castedMutator.getDistribution();
			if (NormalDistribution.class.isAssignableFrom(distribution.getClass())) {
				NormalDistribution normalDistribution = NormalDistribution.class.cast(distribution);
				normalDistribution.setSigma(this.rule.adaptMutator(normalDistribution.getSigma(), wasLastBetter));
			}
		}
		return mutator;
	}

}
