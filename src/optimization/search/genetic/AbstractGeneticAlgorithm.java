/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.search.genetic;

import java.util.Comparator;

import optimization.function.fitness.Function;
import optimization.function.space.Space;
import optimization.problem.OptimizationProblem;
import optimization.search.Search;
import optimization.search.termination.TerminationCondition;
import optimization.util.goal.Goal;
import optimization.util.metric.Tracer;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public abstract class AbstractGeneticAlgorithm<D, C> implements Search<D, C> {

	/**
	 * 
	 */
	protected final int populationSize;

	/**
	 * 
	 */
	private final TerminationCondition<Population<D, C>> terminationCondition;

	/**
	 * @param populationSize
	 * @param terminationCondition
	 */
	public AbstractGeneticAlgorithm(int populationSize, TerminationCondition<Population<D, C>> terminationCondition) {

		this.populationSize = populationSize;
		this.terminationCondition = terminationCondition;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.search.Search#solve(optimization.problem.
	 * OptimizationProblem)
	 */
	@Override
	public Solution<D, C> solve(OptimizationProblem<D, C> problem) {

		Population<D, C> population = this.loadFirstPopulation(problem);
		this.terminationCondition.init();
		Tracer.trace(population.getClass(), population);
		while (this.terminationCondition.test(population)) {
			population = this.nextPopulation(population, problem.getFitnessFunction(), problem.getSpace(),
					problem.getGoal());
			Tracer.trace(population.getClass(), population);
			if (Tracer.isActive(Solution.class)) {
				Tracer.trace(Solution.class, Goal.getBestFromPopulation(population, problem.getGoal()));
			}
		}
		return Goal.getBestFromPopulation(population, problem.getGoal());
	}

	/**
	 * Loads the first population.
	 * 
	 * @param problem
	 *            Problem to be optimized.
	 * @return First population for the problem.
	 */
	protected abstract Population<D, C> loadFirstPopulation(OptimizationProblem<D, C> problem);

	/**
	 * @param population
	 * @param fitnessFunction
	 * @param space
	 * @param goal
	 * @return
	 */
	protected abstract Population<D, C> nextPopulation(Population<D, C> population, Function<D, C> fitnessFunction,
			Space<D> space, Comparator<C> goal);

}
