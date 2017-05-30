/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator.crossover;

import optimization.function.fitness.Function;
import optimization.function.space.Space;
import optimization.util.type.Solution;
import optimization.util.type.tuple.Pair;

/**
 * @author Oscar Garavito
 *
 */
public class MultidimensionalSpaceCrossover<C> extends AbstractGeneticCrossover<double[], C>
		implements
			GeneticCrossover<double[], C> {

	public MultidimensionalSpaceCrossover(double xoverProbability) {

		super(xoverProbability);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.operator.crossover.AbstractGeneticCrossover#xover(
	 * optimization.util.type.tuple.Pair,
	 * optimization.function.fitness.Function,
	 * optimization.function.space.Space)
	 */
	@Override
	protected Pair<double[], double[]> xover(Pair<Solution<double[], C>, Solution<double[], C>> parents,
			Function<double[], C> fitnessFunction, Space<double[]> space) {

		double[] child0 = new double[parents.getLeft().getSolution().length];
		double[] child1 = new double[parents.getLeft().getSolution().length];
		for (int j = 0; j < child0.length; j++) {
			double q = RANDOM.nextDouble();
			child0[j] = parents.getLeft().getSolution()[j] * q + parents.getRight().getSolution()[j] * (1 - q);
			child1[j] = parents.getLeft().getSolution()[j] * (1 - q) + parents.getRight().getSolution()[j] * q;
		}
		return new Pair<double[], double[]>(child0, child1);
	}

}
