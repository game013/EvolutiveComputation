/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator.mutation;

import optimization.function.fitness.Function;
import optimization.function.space.Space;

/**
 * @author Oscar Garavito
 *
 */
public class IdenticalMutator<D, C> implements GeneticMutator<D, C> {

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.operator.GeneticMutator#mutate(optimization.util.
	 * type.Solution, optimization.function.fitness.Function,
	 * optimization.function.space.Space)
	 */
	@Override
	public D mutate(D child, Function<D, C> fitnessFunction, Space<D> space) {

		return child;
	}

}
