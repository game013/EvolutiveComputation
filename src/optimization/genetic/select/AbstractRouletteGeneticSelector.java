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
import roulette.RouletteAcumulated;

/**
 * @author Oscar Garavito
 *
 */
public abstract class AbstractRouletteGeneticSelector<D, C> extends AbstractGeneticSelector<D, C>
		implements
			GeneticSelector<D, C> {

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
	 * optimization.genetic.select.GeneticSelector#selectParent(optimization.
	 * util.type.Population, optimization.function.fitness.Function,
	 * java.util.Comparator)
	 */
	@Override
	public List<Solution<D, C>> selectParent(Population<D, C> population, Function<D, C> function, Comparator<C> goal) {

		RouletteAcumulated roulette = new RouletteAcumulated(getProbabities(population, function, goal));
		List<Solution<D, C>> result = new ArrayList<>(this.parentsSampleSize);
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
	 * @param goal
	 * @return
	 */
	protected abstract double[] getProbabities(Population<D, C> population, Function<D, C> function,
			Comparator<C> goal);

}
