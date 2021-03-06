/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.select;

import java.util.Comparator;
import java.util.stream.IntStream;

import optimization.function.fitness.Function;
import optimization.util.type.Population;

/**
 * @author Oscar Garavito
 *
 */
public class RankingGeneticSelector<D, C> extends AbstractRouletteGeneticSelector<D, C>
		implements
			GeneticSelector<D, C> {

	/**
	 * @param parentsSampleSize
	 */
	public RankingGeneticSelector(int parentsSampleSize) {

		super(parentsSampleSize);
	}

	@Override
	protected double[] getProbabities(Population<D, C> population, Function<D, C> function, Comparator<C> goal) {

		int[] sortedIndexes = IntStream.range(0, population.size()).boxed().sorted(
				(i, j) -> goal.compare(population.get(i).getFitnessValue(), population.get(j).getFitnessValue()))
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
