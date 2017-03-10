/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.select;

import java.util.DoubleSummaryStatistics;
import java.util.List;

import optimization.function.Function;

/**
 * @author Oscar Garavito
 *
 */
public class RouletteGeneticSelector<D> extends AbstractRouletteGeneticSelector<D> implements GeneticSelector<D> {

	/**
	 * Constructor with given parent's sample size parameter.
	 * 
	 * @param parentsSampleSize
	 *            Parent's sample size.
	 */
	protected RouletteGeneticSelector(int parentsSampleSize) {

		super(parentsSampleSize);
	}

	/**
	 * Function that calculates probabilities for each individual in population.
	 * 
	 * @param population
	 * @param function
	 * @return
	 */
	@Override
	protected double[] getProbabities(List<D> population, Function<D, Double> function) {

		DoubleSummaryStatistics statistics = population.stream().map(function::calculate).mapToDouble(Double::valueOf)
				.summaryStatistics();
		double quantum = 0;
		double reference = statistics.getMax() + quantum;
		double scale = reference * statistics.getCount() - statistics.getSum();
		double[] probabilities = new double[population.size()];
		for (int i = 0; i < population.size(); i++) {
			probabilities[i] = (reference - function.calculate(population.get(i))) / (quantum * scale);
		}
		return probabilities;
	}

}
