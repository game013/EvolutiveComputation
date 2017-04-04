/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.select;

import java.util.Comparator;

import optimization.function.fitness.Function;
import optimization.util.type.Population;

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

	/**
	 * Function that calculates probabilities for each individual in population.
	 * 
	 * @param population
	 * @param function
	 * @param goal
	 * @return
	 */
	@Override
	protected abstract double[] getProbabities(Population<D, Double> population, Function<D, Double> function,
			Comparator<Double> goal);

}
