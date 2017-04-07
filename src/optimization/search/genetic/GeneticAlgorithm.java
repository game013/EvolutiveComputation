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
import optimization.search.termination.ForLoopTerminationCondition;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class GeneticAlgorithm<D, C> extends AbstractGeneticAlgorithm<D, C> implements Search<D, C> {

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

		super(populationSize, new ForLoopTerminationCondition<>(numberOfIterations));
		this.selector = selector;
		this.geneticOperator = geneticOperator;
		this.replacement = replacement;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.search.genetic.AbstractGeneticAlgorithm#loadFirstPopulation(
	 * optimization.problem.OptimizationProblem)
	 */
	protected Population<D, C> loadFirstPopulation(OptimizationProblem<D, C> problem) {

		List<D> pop = problem.getSpace().pick(this.populationSize);
		List<Solution<D, C>> initialSolutionList = new ArrayList<>(this.populationSize);
		for (D individual : pop) {
			initialSolutionList.add(new Solution<>(individual, problem.getFitnessFunction()));
		}
		Population<D, C> population = new Population<>(initialSolutionList);
		return population;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.search.genetic.AbstractGeneticAlgorithm#nextPopulation(
	 * optimization.util.type.Population,
	 * optimization.function.fitness.Function,
	 * optimization.function.space.Space, java.util.Comparator)
	 */
	protected Population<D, C> nextPopulation(Population<D, C> population, Function<D, C> fitnessFunction,
			Space<D> space, Comparator<C> goal) {

		List<Solution<D, C>> parents = selector.selectParent(population, fitnessFunction, goal);
		List<Solution<D, C>> offspring = this.geneticOperator.apply(parents, fitnessFunction, space);
		return this.replacement.apply(population, offspring, fitnessFunction, goal);
	}

}
