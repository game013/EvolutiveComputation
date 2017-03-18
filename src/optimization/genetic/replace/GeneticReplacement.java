/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.replace;

import java.util.List;

import optimization.function.Function;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
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
	 * @return New replaced population.
	 */
	Population<D, C> apply(Population<D, C> population, List<Solution<D, C>> offspring, Function<D, C> fitnessFunction);

}
