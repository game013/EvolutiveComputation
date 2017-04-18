/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.search.genetic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import optimization.function.fitness.Function;
import optimization.function.space.Space;
import optimization.genetic.operator.mutation.GeneticMutator;
import optimization.genetic.operator.recombination.EvolutionaryRecombinator;
import optimization.genetic.parameter.ParameterGenerator;
import optimization.genetic.replace.GeneticReplacement;
import optimization.genetic.select.GeneticSelector;
import optimization.problem.OptimizationProblem;
import optimization.search.Search;
import optimization.search.termination.TerminationCondition;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class EvolutionaryStrategies<D, C> extends AbstractGeneticAlgorithm<D, C> implements Search<D, C> {

	private final ParameterGenerator parameterGenerator;

	private final GeneticSelector<D, C> selector;

	private final EvolutionaryRecombinator<D, C> recombinator;

	private final GeneticMutator<D, C> mutator;

	private final GeneticReplacement<D, C> replacement;

	private final int numberOfChildren;

	public EvolutionaryStrategies(int populationSize, TerminationCondition<Population<D, C>> terminationCondition,
			ParameterGenerator parameterGenerator, int numberOfChildren, GeneticSelector<D, C> selector,
			EvolutionaryRecombinator<D, C> recombinator, GeneticMutator<D, C> mutator,
			GeneticReplacement<D, C> replacement) {

		super(populationSize, terminationCondition);
		this.parameterGenerator = parameterGenerator;
		this.numberOfChildren = numberOfChildren;
		this.selector = selector;
		this.recombinator = recombinator;
		this.mutator = mutator;
		this.replacement = replacement;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.search.genetic.AbstractGeneticAlgorithm#loadFirstPopulation(
	 * optimization.problem.OptimizationProblem)
	 */
	@Override
	public Population<D, C> loadFirstPopulation(OptimizationProblem<D, C> problem) {

		List<Solution<D, C>> population = new ArrayList<>();
		for (int i = 0; i < this.populationSize; i++) {
			D solution = problem.getSpace().pick();
			population.add(new Solution<>(solution, problem.getFitnessFunction(),
					this.parameterGenerator.generateParameters()));
		}
		return new Population<>(population);
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.search.genetic.AbstractGeneticAlgorithm#nextPopulation(
	 * optimization.util.type.Population,
	 * optimization.function.fitness.Function,
	 * optimization.function.space.Space, java.util.Comparator)
	 */
	@Override
	protected Population<D, C> nextPopulation(Population<D, C> population, Function<D, C> fitnessFunction,
			Space<D> space, Comparator<C> goal) {

		List<Solution<D, C>> children = new ArrayList<>(this.numberOfChildren);
		for (int i = 0; i < this.numberOfChildren; i++) {
			List<Solution<D, C>> parents = this.selector.selectParent(population, fitnessFunction, goal);
			Solution<D, C> child = this.recombinator.recombine(parents, fitnessFunction, goal);
			Solution<D, C> mutatedChild = this.mutator.mutate(child, fitnessFunction, space);
			children.add(new Solution<D, C>(space.repair(mutatedChild), fitnessFunction, child.getParameters()));
		}
		return this.replacement.apply(population, children, fitnessFunction, goal);
	}

}
