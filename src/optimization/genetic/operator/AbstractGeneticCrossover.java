/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator;

import java.util.ArrayList;
import java.util.List;

import optimization.function.fitness.Function;
import optimization.function.space.Space;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public abstract class AbstractGeneticCrossover<D, C> implements GeneticCrossover<D, C> {

	/**
	 * Pair of solutions to be used in genetic crossover.
	 * 
	 * @author Oscar Garavito
	 *
	 */
	protected class Pair<L, R> {

		/**
		 * 
		 */
		private final L left;

		/**
		 * 
		 */
		private final R right;

		/**
		 * @param left
		 * @param right
		 */
		public Pair(L left, R right) {

			this.left = left;
			this.right = right;
		}

		/**
		 * @return the left
		 */
		public L getLeft() {
			return left;
		}

		/**
		 * @return the right
		 */
		public R getRight() {
			return right;
		}

	}

	/**
	 * 
	 */
	private final double xoverProbability;

	/**
	 * @param xoverProbability
	 */
	public AbstractGeneticCrossover(double xoverProbability) {

		if (xoverProbability < 0 || xoverProbability > 1) {
			throw new IllegalArgumentException("Crossover probability must be between 0 and 1.");
		}
		this.xoverProbability = xoverProbability;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.genetic.operator.GeneticCrossover#xover(java.util.List,
	 * optimization.function.fitness.Function,
	 * optimization.function.space.Space)
	 */
	@Override
	public List<Solution<D, C>> xover(List<Solution<D, C>> parents, Function<D, C> fitnessFunction, Space<D> space) {

		List<Solution<D, C>> offspring = new ArrayList<>(parents.size());
		for (int i = 0; i < parents.size(); i += 2) {
			Pair<D, D> children;
			if (Math.random() < this.xoverProbability) {
				children = xover(new Pair<>(parents.get(i), parents.get(i + 1)), fitnessFunction, space);
			} else {
				children = new Pair<>(parents.get(i).getSolution(), parents.get(i + 1).getSolution());
			}
			offspring.add(new Solution<>(space.repair(children.getLeft()), fitnessFunction));
			offspring.add(new Solution<>(space.repair(children.getRight()), fitnessFunction));
		}
		return offspring;
	}

	/**
	 * @param parents
	 * @param fitnessFunction
	 * @param space
	 * @return
	 */
	protected abstract Pair<D, D> xover(Pair<Solution<D, C>, Solution<D, C>> parents, Function<D, C> fitnessFunction,
			Space<D> space);

}
