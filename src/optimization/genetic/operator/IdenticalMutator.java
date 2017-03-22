/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator;

import java.util.List;

import optimization.function.fitness.Function;
import optimization.function.space.Space;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class IdenticalMutator<D, C> implements GeneticMutator<D, C> {

	/*
	 * (non-Javadoc)
	 * @see optimization.genetic.operator.GeneticMutator#mutate(java.util.List,
	 * optimization.function.fitness.Function,
	 * optimization.function.space.Space)
	 */
	@Override
	public List<Solution<D, C>> mutate(List<Solution<D, C>> offspring, Function<D, C> fitnessFunction, Space<D> space) {

		return offspring;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.operator.GeneticMutator#mutate(optimization.util.
	 * type.Solution, optimization.function.fitness.Function,
	 * optimization.function.space.Space)
	 */
	@Override
	public D mutate(Solution<D, C> child, Function<D, C> fitnessFunction, Space<D> space) {

		return child.getSolution();
	}

}
