/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.select;

import java.util.ArrayList;
import java.util.List;

import optimization.function.Function;
import optimization.util.type.Population;
import optimization.util.type.Solution;
import roulette.RouletteAcumulated;

/**
 * @author Oscar Garavito
 *
 */
public abstract class AbstractDoubleRouletteGeneticSelector<D> extends AbstractRouletteGeneticSelector<D, Double> {

	/**
	 * Constructor with given parent's sample size parameter.
	 * 
	 * @param parentsSampleSize
	 *            Parent's sample size.
	 */
	protected AbstractDoubleRouletteGeneticSelector(int parentsSampleSize) {

		super(parentsSampleSize);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.select.GeneticSelector#selectParent(java.util.List,
	 * optimization.function.Function)
	 */
	@Override
	public List<Solution<D, Double>> selectParent(Population<D, Double> population, Function<D, Double> function) {

		RouletteAcumulated roulette = new RouletteAcumulated(getProbabities(population, function));
		List<Solution<D, Double>> result = new ArrayList<>(this.parentsSampleSize);
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
	@Override
	protected abstract double[] getProbabities(Population<D, Double> population, Function<D, Double> function);

}
