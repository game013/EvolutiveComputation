/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.replace;

import java.util.Comparator;
import java.util.List;

import optimization.function.fitness.Function;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
@FunctionalInterface
public interface GeneticReplacement<D, C> {

	/**
	 * Executes genetic replacement over original population.
	 * 
	 * @param population
	 *            Original population.
	 * @param offspring
	 *            Obtained offspring.
	 * @param fitnessFunction
	 *            Fitness function to apply selective pressure.
	 * @param goal
	 *            Comparator to be used to compare solutions.
	 * @return New replaced population.
	 */
	Population<D, C> apply(Population<D, C> population, List<Solution<D, C>> offspring, Function<D, C> fitnessFunction,
			Comparator<C> goal);

}
