/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.select;

import java.util.Comparator;
import java.util.List;

import optimization.function.fitness.Function;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public interface GeneticSelector<D, C> {

	/**
	 * Given a population this method selects parents to be used in next genetic
	 * process.
	 * 
	 * @param population
	 *            Population where parents will be chosen from.
	 * @param fitnessFunction
	 *            Function to calculate the fitness for each individual from
	 *            population.
	 * @param goal
	 *            Comparator used to determine the order of solutions.
	 * @return Parents population.
	 */
	List<Solution<D, C>> selectParent(Population<D, C> population, Function<D, C> fitnessFunction, Comparator<C> goal);

	/**
	 * Gets the number of parents to be chosen.
	 * 
	 * @return
	 */
	int getParentsSampleSize();

}
