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
public class RouletteGeneticSelector<D> extends AbstractDoubleRouletteGeneticSelector<D> {

	/**
	 * Constructor with given parent's sample size parameter.
	 * 
	 * @param parentsSampleSize
	 *            Parent's sample size.
	 */
	public RouletteGeneticSelector(int parentsSampleSize) {

		super(parentsSampleSize);
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.genetic.select.AbstractDoubleRouletteGeneticSelector#
	 * getProbabities(optimization.util.type.Population,
	 * optimization.function.fitness.Function, java.util.Comparator)
	 */
	@Override
	protected double[] getProbabities(Population<D, Double> population, Function<D, Double> function,
			Comparator<Double> goal) {

		double max = Double.MIN_VALUE, sum = 0.0;
		for (int i = 1; i < population.size(); i++) {
			max = Math.max(max, population.get(i).getFitnessValue());
			sum += population.get(i).getFitnessValue();
		}
		double[] probabilities = new double[population.size()];
		for (int i = 0; i < population.size(); i++) {
			probabilities[i] = (population.get(i).getFitnessValue()) / sum;
		}
		return probabilities;
	}

}
