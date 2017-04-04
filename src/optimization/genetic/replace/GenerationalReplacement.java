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
public class GenerationalReplacement<D, C> implements GeneticReplacement<D, C> {

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.replace.GeneticReplacement#apply(optimization.util.
	 * type.Population, java.util.List, optimization.function.Function)
	 */
	@Override
	public Population<D, C> apply(Population<D, C> population, List<Solution<D, C>> offspring,
			Function<D, C> fitnessFunction, Comparator<C> goal) {

		return new Population<>(offspring);
	}

}
