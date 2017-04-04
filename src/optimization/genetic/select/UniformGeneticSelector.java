/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.select;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import optimization.function.fitness.Function;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class UniformGeneticSelector<D, C> extends AbstractGeneticSelector<D, C> implements GeneticSelector<D, C> {

	/**
	 * Constructor with given parent's sample size parameter.
	 * 
	 * @param parentsSampleSize
	 *            Parent's sample size.
	 */
	public UniformGeneticSelector(int parentsSampleSize) {

		super(parentsSampleSize);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.select.GeneticSelector#selectParent(optimization.
	 * util.type.Population, optimization.function.fitness.Function,
	 * java.util.Comparator)
	 */
	@Override
	public List<Solution<D, C>> selectParent(Population<D, C> population, Function<D, C> function, Comparator<C> goal) {

		List<Solution<D, C>> selectedParents = new ArrayList<>();
		for (int i = 0; i < super.parentsSampleSize; i++) {
			selectedParents.add(population.get(random.nextInt(population.size())));
		}
		return selectedParents;
	}

}
