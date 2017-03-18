/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.search.genetic;

import java.util.ArrayList;
import java.util.List;

import optimization.function.Function;
import optimization.genetic.operator.GeneticOperator;
import optimization.genetic.replace.GeneticReplacement;
import optimization.genetic.select.GeneticSelector;
import optimization.problem.OptimizationProblem;
import optimization.search.Search;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class GeneticAlgorithm<D, C> implements Search<D, C> {

	private final int populationSize;

	private final GeneticSelector<D, C> selector;

	private final GeneticOperator<D, C> geneticOperator;

	private final GeneticReplacement<D, C> replacement;

	public GeneticAlgorithm(int populationSize, GeneticSelector<D, C> selector, GeneticOperator<D, C> geneticOperator,
			GeneticReplacement<D, C> replacement) {

		this.populationSize = populationSize;
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
	public C solve(OptimizationProblem<D, C> problem) {

		// TODO : Adjust number of iterations. Return best value.
		Population<D, C> population = this.loadFirstPopulation(problem);
		for (int i = 0; i < 10; i++) {
			population = this.nextPopulation(population, problem.getFitnessFunction());
		}
		return null;
	}

	private Population<D, C> loadFirstPopulation(OptimizationProblem<D, C> problem) {

		List<D> pop = problem.getSpace().pick(this.populationSize);
		List<Solution<D, C>> initialSolutionList = new ArrayList<>(this.populationSize);
		for (D individual : pop) {
			initialSolutionList.add(new Solution<>(individual, problem.getFitnessFunction().calculate(individual)));
		}
		Population<D, C> population = new Population<>(initialSolutionList);
		return population;
	}

	private Population<D, C> nextPopulation(Population<D, C> population, Function<D, C> fitnessFunction) {

		List<Solution<D, C>> parents = selector.selectParent(population, fitnessFunction);
		List<Solution<D, C>> offspring = this.geneticOperator.apply(parents, fitnessFunction);
		return this.replacement.apply(population, offspring, fitnessFunction);
	}

}
