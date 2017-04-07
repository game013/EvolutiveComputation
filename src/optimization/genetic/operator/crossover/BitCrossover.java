/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator.crossover;

import java.util.BitSet;

import optimization.function.fitness.Function;
import optimization.function.space.Space;
import optimization.util.type.Solution;
import optimization.util.type.tuple.Pair;

/**
 * @author Oscar Garavito
 *
 */
public class BitCrossover<C> extends AbstractGeneticCrossover<BitSet, C> implements GeneticCrossover<BitSet, C> {

	/**
	 * @param xoverProbability
	 */
	public BitCrossover(double xoverProbability) {

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

		BitSet child1 = (BitSet) parents.getRight().getSolution().clone();
		child1.andNot(parents.getLeft().getSolution());

		BitSet child2 = (BitSet) parents.getLeft().getSolution().clone();
		child2.xor(parents.getRight().getSolution());
		return new Pair<>(child1, child2);
	}

}
