/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator;

import java.util.List;

import optimization.function.fitness.Function;
import optimization.function.space.Space;
import optimization.genetic.operator.crossover.GeneticCrossover;
import optimization.genetic.operator.mutation.GeneticMutator;
import optimization.genetic.operator.mutation.IdenticalMutator;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class CustomGeneticOperator<D, C> implements GeneticOperator<D, C> {

	/**
	 * Crossover operator.
	 */
	private final GeneticCrossover<D, C> crossover;

	/**
	 * Mutation operator.
	 */
	private final GeneticMutator<D, C> mutator;

	/**
	 * Creates a genetic operator without mutations.
	 * 
	 * @param crossover
	 *            Crossover operator.
	 */
	public CustomGeneticOperator(GeneticCrossover<D, C> crossover) {

		this(crossover, new IdenticalMutator<>());
	}

	/**
	 * Creates a genetic operator with given crossover and mutator.
	 * 
	 * @param crossover
	 *            Crossover operator.
	 * @param mutator
	 *            Mutator operator.
	 */
	public CustomGeneticOperator(GeneticCrossover<D, C> crossover, GeneticMutator<D, C> mutator) {

		this.crossover = crossover;
		this.mutator = mutator;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.genetic.operator.GeneticOperator#apply(java.util.List,
	 * optimization.function.fitness.Function,
	 * optimization.function.space.Space)
	 */
	@Override
	public List<Solution<D, C>> apply(List<Solution<D, C>> parents, Function<D, C> fitnessFunction, Space<D> space) {

		return crossover.xover(parents, fitnessFunction, mutator, space);
	}

}
