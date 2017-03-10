/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.select;

import java.util.ArrayList;
import java.util.List;

import optimization.function.Function;
import roulette.RouletteAcumulated;

/**
 * @author Oscar Garavito
 *
 */
public abstract class AbstractRouletteGeneticSelector<D> extends AbstractGeneticSelector<D>
		implements
			GeneticSelector<D> {

	/**
	 * Constructor with given parent's sample size parameter.
	 * 
	 * @param parentsSampleSize
	 *            Parent's sample size.
	 */
	protected AbstractRouletteGeneticSelector(int parentsSampleSize) {

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

		RouletteAcumulated roulette = new RouletteAcumulated(getProbabities(population, function));
		List<D> result = new ArrayList<>(this.parentsSampleSize);
		for (int i = 0; i < this.parentsSampleSize; i++) {
			result.add(population.get(roulette.nextRandom()));
		}
		return result;
	}

	/**
	 * Function that calculates probabilities for each individual in population.
	 * 
	 * @param population
	 * @param function
	 * @return
	 */
	protected abstract double[] getProbabities(List<D> population, Function<D, Double> function);

}
