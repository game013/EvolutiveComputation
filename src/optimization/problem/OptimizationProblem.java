/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.problem;

import optimization.function.fitness.Function;
import optimization.function.space.Space;

/**
 * @param <D>
 *            Domain.
 * @param <C>
 *            Codomain.
 * @author Oscar Garavito
 *
 */
public class OptimizationProblem<D, C> {

	/**
	 * Search space.
	 */
	private final Space<D> space;

	/**
	 * Fitness function to be optimized.
	 */
	private final Function<D, C> fitnessFunction;

	/**
	 * @param space
	 * @param fitnessFunction
	 */
	public OptimizationProblem(Space<D> space, Function<D, C> fitnessFunction) {

		this.space = space;
		this.fitnessFunction = fitnessFunction;
	}

	/**
	 * @return
	 */
	public Space<D> getSpace() {

		return this.space;
	}

	/**
	 * @return
	 */
	public Function<D, C> getFitnessFunction() {

		return this.fitnessFunction;
	}

}
