/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator.mutation;

import java.util.BitSet;
import java.util.Random;

import optimization.function.fitness.Function;
import optimization.function.space.Space;

/**
 * Flips a random bit of solution.
 * 
 * @author Oscar Garavito
 *
 */
public class BitMutator<C> implements GeneticMutator<BitSet, C> {

	/**
	 * 
	 */
	private static final Random RANDOM = new Random();

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.operator.GeneticMutator#mutate(optimization.util.
	 * type.Solution, optimization.function.fitness.Function,
	 * optimization.function.space.Space)
	 */
	@Override
	public BitSet mutate(BitSet child, Function<BitSet, C> fitnessFunction, Space<BitSet> space) {

		BitSet newChild = (BitSet) child.clone();
		newChild.flip(RANDOM.nextInt(space.getDimension()));
		return newChild;
	}

}
