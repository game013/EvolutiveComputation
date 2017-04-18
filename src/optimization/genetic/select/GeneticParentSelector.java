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
public class GeneticParentSelector<D, C> implements GeneticSelector<D, C> {

	private static final int PARENT_SAMPLE_SIZE = 1;

	@Override
	public List<Solution<D, C>> selectParent(Population<D, C> population, Function<D, C> fitnessFunction,
			Comparator<C> goal) {

		return population.getPopulation();
	}

	@Override
	public int getParentsSampleSize() {

		return PARENT_SAMPLE_SIZE;
	}

}
