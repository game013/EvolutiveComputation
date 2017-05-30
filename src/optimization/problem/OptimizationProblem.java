/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.problem;

import java.util.Comparator;

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
	 * Goal of function.
	 */
	private final Comparator<C> goal;

	/**
	 * @param space
	 * @param fitnessFunction
	 * @param goal
	 */
	public OptimizationProblem(Space<D> space, Function<D, C> fitnessFunction, Comparator<C> goal) {

		this.space = space;
		this.fitnessFunction = fitnessFunction;
		this.goal = goal;
	}

	/**
	 * @param space
	 * @param fitnessFunction
	 * @param goal
	 */
	public OptimizationProblem(Space<D> space, Function<D, C> fitnessFunction) {

		this(space, fitnessFunction, null);
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

	/**
	 * @return the goal
	 */
	public Comparator<C> getGoal() {
		return goal;
	}

}
