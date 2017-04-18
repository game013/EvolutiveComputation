/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator.mutation;

import optimization.function.fitness.Function;
import optimization.function.space.Space;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public interface GeneticMutator<D, C> {

	/**
	 * Mutates an entire solution, calling the another mutation method in this
	 * interface.
	 * 
	 * @param child
	 *            Child to be mutated.
	 * @param fitnessFunction
	 *            Fitness function.
	 * @param space
	 *            Space of the function.
	 * @return Built mutated solution.
	 */
	default Solution<D, C> mutate(Solution<D, C> child, Function<D, C> fitnessFunction, Space<D> space) {

		return new Solution<>(mutate(child.getSolution(), fitnessFunction, space), fitnessFunction,
				child.getParameters());
	}

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
