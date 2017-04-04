/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.search.genetic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import optimization.function.fitness.Function;
import optimization.function.space.Space;
import optimization.genetic.operator.GeneticOperator;
import optimization.genetic.replace.GeneticReplacement;
import optimization.genetic.select.GeneticSelector;
import optimization.problem.OptimizationProblem;
import optimization.search.Search;
import optimization.util.goal.Goal;
import optimization.util.metric.Tracer;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class GeneticAlgorithm<D, C extends Comparable<C>> implements Search<D, C> {

	private final int populationSize;

	private final int numberOfIterations;

	private final GeneticSelector<D, C> selector;

	private final GeneticOperator<D, C> geneticOperator;

	private final GeneticReplacement<D, C> replacement;

	/**
	 * @param populationSize
	 * @param numberOfIterations
	 * @param selector
	 * @param geneticOperator
	 * @param replacement
	 */
	public GeneticAlgorithm(int populationSize, int numberOfIterations, GeneticSelector<D, C> selector,
			GeneticOperator<D, C> geneticOperator, GeneticReplacement<D, C> replacement) {

		this.populationSize = populationSize;
		this.numberOfIterations = numberOfIterations;
		this.selector = selector;
		this.geneticOperator = geneticOperator;
		this.replacement = replacement;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.search.Search#solve(optimization.problem.
	 * OptimizationProblem)
	 */
	@Override
	public Solution<D, C> solve(OptimizationProblem<D, C> problem) {

		Population<D, C> population = this.loadFirstPopulation(problem);
		Tracer.trace(population.getClass(), population);
		for (int i = 0; i < this.numberOfIterations; i++) {
			population = this.nextPopulation(population, problem.getFitnessFunction(), problem.getSpace(),
					problem.getGoal());
			Tracer.trace(population.getClass(), population);
			if (Tracer.isActive(Solution.class)) {
				Tracer.trace(Solution.class, Goal.getBestFromPopulation(population, problem.getGoal()));
			}
		}
		return Goal.getBestFromPopulation(population, problem.getGoal());
	}

	private Population<D, C> loadFirstPopulation(OptimizationProblem<D, C> problem) {

		List<D> pop = problem.getSpace().pick(this.populationSize);
		List<Solution<D, C>> initialSolutionList = new ArrayList<>(this.populationSize);
		for (D individual : pop) {
			initialSolutionList.add(new Solution<>(individual, problem.getFitnessFunction()));
		}
		Population<D, C> population = new Population<>(initialSolutionList);
		return population;
	}

	private Population<D, C> nextPopulation(Population<D, C> population, Function<D, C> fitnessFunction, Space<D> space,
			Comparator<C> goal) {

		List<Solution<D, C>> parents = selector.selectParent(population, fitnessFunction, goal);
		List<Solution<D, C>> offspring = this.geneticOperator.apply(parents, fitnessFunction, space);
		return this.replacement.apply(population, offspring, fitnessFunction, goal);
	}

}
