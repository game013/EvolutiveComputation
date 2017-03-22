/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator;

import java.util.BitSet;
import java.util.Random;

import optimization.function.fitness.Function;
import optimization.function.space.Space;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class BinaryCrossover<C> extends AbstractGeneticCrossover<BitSet, C> implements GeneticCrossover<BitSet, C> {

	/**
	 * Random object.
	 */
	private static final Random RANDOM = new Random();

	/**
	 * @param xoverProbability
	 */
	public BinaryCrossover(double xoverProbability) {

		super(xoverProbability);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.operator.AbstractGeneticCrossover#xover(optimization
	 * .genetic.operator.AbstractGeneticCrossover.Pair,
	 * optimization.function.fitness.Function,
	 * optimization.function.space.Space)
	 */
	@Override
	protected Pair<BitSet, BitSet> xover(Pair<Solution<BitSet, C>, Solution<BitSet, C>> parents,
			Function<BitSet, C> fitnessFunction, Space<BitSet> space) {

		int random = RANDOM.nextInt(space.getDimension());

		BitSet child1 = (BitSet) parents.getRight().getSolution().clone();
		child1.clear(0, random + 1);
		child1.or(parents.getLeft().getSolution().get(0, random + 1));

		BitSet child2 = (BitSet) parents.getLeft().getSolution().clone();
		child2.clear(0, random + 1);
		child2.or(parents.getRight().getSolution().get(0, random + 1));
		return new Pair<>(child1, child2);
	}

}
