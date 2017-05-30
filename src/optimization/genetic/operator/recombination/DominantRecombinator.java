/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator.recombination;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

import optimization.util.type.Solution;
import optimization.util.type.SolutionParameter;

/**
 * @author Oscar Garavito
 *
 */
public class DominantRecombinator<C> implements EvolutionaryRecombinator<double[], C> {

	/**
	 * 
	 */
	private static final Random RANDOM = new Random();

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.operator.recombination.EvolutionaryRecombinator#
	 * recombine(java.util.List, java.util.Comparator)
	 */
	@Override
	public double[] recombine(List<Solution<double[], C>> parents, Comparator<C> goal) {

		double[] child;
		if (!parents.isEmpty()) {
			Solution<double[], C> firstParent = parents.get(0);
			child = new double[firstParent.getSolution().length];
			for (int i = 0; i < child.length; i++) {
				int chosenParent = RANDOM.nextInt(parents.size());
				child[i] = parents.get(chosenParent).getSolution()[i];
			}
		} else {
			throw new IllegalArgumentException("Parents list cannot be empty.");
		}
		return child;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.operator.recombination.EvolutionaryRecombinator#
	 * recombineParameters(java.util.List, java.util.Comparator)
	 */
	@Override
	public SolutionParameter recombineParameters(List<Solution<double[], C>> parents, Comparator<C> goal) {

		int chosenParent = RANDOM.nextInt(parents.size());
		return parents.get(chosenParent).getParameters().orElse(null);
	}

}
