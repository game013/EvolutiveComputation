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
public interface GeneticMutator<D, C> {

	/**
	 * Performs mutation over one individual.
	 * 
	 * @param child
	 *            Individual to mutate.
	 * @param fitnessFunction
	 *            Function to calculate fitness.
	 * @param space
	 *            Space to repair data.
	 * @return Mutated child.
	 */
	D mutate(D child, Function<D, C> fitnessFunction, Space<D> space);

}
