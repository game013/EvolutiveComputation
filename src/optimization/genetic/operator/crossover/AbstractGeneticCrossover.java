/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator.crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import optimization.function.fitness.Function;
import optimization.function.space.Space;
import optimization.genetic.operator.mutation.GeneticMutator;
import optimization.util.type.Solution;
import optimization.util.type.tuple.Pair;

/**
 * @author Oscar Garavito
 *
 */
public abstract class AbstractGeneticCrossover<D, C> implements GeneticCrossover<D, C> {

	/**
	 * Random object.
	 */
	protected static final Random RANDOM = new Random();

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
	 * optimization.genetic.operator.GeneticMutator,
	 * optimization.function.space.Space)
	 */
	@Override
	public List<Solution<D, C>> xover(List<Solution<D, C>> parents, Function<D, C> fitnessFunction,
			GeneticMutator<D, C> mutator, Space<D> space) {

		List<Solution<D, C>> offspring = new ArrayList<>(parents.size());
		for (int i = 0; i < parents.size(); i += 2) {
			if (Math.random() < this.xoverProbability) {
				Pair<D, D> children;
				children = xover(new Pair<>(parents.get(i), parents.get(i + 1)), fitnessFunction, space);

				D mutatedChild1 = space.repair(mutator.mutate(children.getLeft(), fitnessFunction, space));
				D mutatedChild2 = space.repair(mutator.mutate(children.getRight(), fitnessFunction, space));
				offspring.add(new Solution<>(mutatedChild1, fitnessFunction));
				offspring.add(new Solution<>(mutatedChild2, fitnessFunction));
			} else {
				offspring.add(parents.get(i));
				offspring.add(parents.get(i + 1));
			}
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
