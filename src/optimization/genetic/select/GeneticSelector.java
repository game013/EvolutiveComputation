/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.select;

import java.util.List;

import optimization.function.Function;

/**
 * @author Oscar Garavito
 *
 */
@FunctionalInterface
public interface GeneticSelector<D> {

	/**
	 * Given a population this method selects parents to be used in next genetic
	 * process.
	 * 
	 * @param population
	 *            Population where parents will be chosen from.
	 * @param function
	 *            Function to calculate the fitness for each individual from
	 *            population.
	 * @return Parents population.
	 */
	List<D> selectParent(List<D> population, Function<D, Double> function);

}
