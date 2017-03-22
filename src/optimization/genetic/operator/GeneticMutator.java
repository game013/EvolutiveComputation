/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator;

import java.util.ArrayList;
import java.util.List;

import optimization.function.fitness.Function;
import optimization.function.space.Space;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public interface GeneticMutator<D, C> {

	/**
	 * Performs mutation over each individual of offspring.
	 * 
	 * @param offspring
	 *            Offspring to be mutated.
	 * @param fitnessFunction
	 *            Function to calculate fitness.
	 * @param space
	 *            Space to repair data.
	 * @return Offspring, product from mutation.
	 */
	default List<Solution<D, C>> mutate(List<Solution<D, C>> offspring, Function<D, C> fitnessFunction,
			Space<D> space) {

		List<Solution<D, C>> mutatedOffspring = new ArrayList<>();
		for (Solution<D, C> child : offspring) {
			mutatedOffspring.add(new Solution<>(space.repair(mutate(child, fitnessFunction, space)), fitnessFunction));
		}
		return mutatedOffspring;
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
	D mutate(Solution<D, C> child, Function<D, C> fitnessFunction, Space<D> space);

}
