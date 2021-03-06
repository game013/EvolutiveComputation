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
public interface GeneticOperator<D, C> {

	/**
	 * Executes genetic operator over given parent list and returns produced
	 * offspring.
	 * 
	 * @param parents
	 *            Parents to be genetically operated.
	 * @param fitnessFunction
	 *            Fitness function to apply selective pressure over its
	 *            descendant.
	 * @param space
	 *            Space to repair created child.
	 * @return List of offspring.
	 */
	List<Solution<D, C>> apply(List<Solution<D, C>> parents, Function<D, C> fitnessFunction, Space<D> space);

}
