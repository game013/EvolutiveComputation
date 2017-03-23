/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator;

import java.util.List;

import optimization.function.fitness.Function;
import optimization.function.space.Space;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
@FunctionalInterface
public interface GeneticCrossover<D, C> {

	/**
	 * Performs crossover operations between parents.
	 * 
	 * @param parents
	 *            Parents to be crossed.
	 * @param fitnessFunction
	 *            Function to calculate fitness.
	 * @param mutator
	 *            Mutator to be applied.
	 * @param space
	 *            Space to repair solution.
	 * @return List of offspring.
	 */
	List<Solution<D, C>> xover(List<Solution<D, C>> parents, Function<D, C> fitnessFunction,
			GeneticMutator<D, C> mutator, Space<D> space);

}
