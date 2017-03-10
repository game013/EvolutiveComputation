/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.select;

import java.util.ArrayList;
import java.util.List;

import optimization.function.Function;

/**
 * @author Oscar Garavito
 *
 */
public class UniformGeneticSelector<D> extends AbstractGeneticSelector<D> implements GeneticSelector<D> {

	/**
	 * Constructor with given parent's sample size parameter.
	 * 
	 * @param parentsSampleSize
	 *            Parent's sample size.
	 */
	protected UniformGeneticSelector(int parentsSampleSize) {

		super(parentsSampleSize);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.select.GeneticSelector#selectParent(java.util.List,
	 * optimization.function.Function)
	 */
	@Override
	public List<D> selectParent(List<D> population, Function<D, Double> function) {

		List<D> selectedParents = new ArrayList<>();
		for (int i = 0; i < super.parentsSampleSize; i++) {
			selectedParents.add(population.get(random.nextInt(population.size())));
		}
		return selectedParents;
	}

}
