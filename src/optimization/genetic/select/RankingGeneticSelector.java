/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.select;

import java.util.List;
import java.util.stream.IntStream;

import optimization.function.Function;

/**
 * @author Oscar Garavito
 *
 */
public class RankingGeneticSelector<D> extends AbstractRouletteGeneticSelector<D> implements GeneticSelector<D> {

	/**
	 * @param parentsSampleSize
	 */
	protected RankingGeneticSelector(int parentsSampleSize) {

		super(parentsSampleSize);
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.genetic.select.AbstractRouletteGeneticSelector#
	 * getProbabities(java.util.List, optimization.function.Function)
	 */
	@Override
	protected double[] getProbabities(List<D> population, Function<D, Double> function) {

		int[] sortedIndexes = IntStream.range(0, population.size()).boxed().sorted(
				(i, j) -> function.calculate(population.get(i)).compareTo(function.calculate(population.get(j))))
				.mapToInt(Integer::valueOf).toArray();
		int F = population.size() * (population.size() + 1) / 2;
		double[] probabilities = new double[population.size()];
		for (int i = 0; i < sortedIndexes.length; i++) {
			int index = sortedIndexes[i];
			probabilities[index] = i / F;
		}
		return probabilities;
	}

}
