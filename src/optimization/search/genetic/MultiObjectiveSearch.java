/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.search.genetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import optimization.function.fitness.Function;
import optimization.function.space.Space;
import optimization.genetic.operator.GeneticOperator;
import optimization.problem.OptimizationProblem;
import optimization.search.Search;
import optimization.search.termination.TerminationCondition;
import optimization.util.goal.Distance;
import optimization.util.goal.Goal;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class MultiObjectiveSearch<D, C> extends AbstractGeneticAlgorithm<D, C> implements Search<D, C> {

	private final GeneticOperator<D, C> operator;

	private final Distance<C> distance;

	public MultiObjectiveSearch(int populationSize, TerminationCondition<Population<D, C>> terminationCondition,
			GeneticOperator<D, C> operator, Distance<C> distance) {

		super(populationSize, terminationCondition);
		this.operator = operator;
		this.distance = distance;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.search.genetic.AbstractGeneticAlgorithm#loadFirstPopulation(
	 * optimization.problem.OptimizationProblem)
	 */
	@Override
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
	@Override
	protected Population<D, C> nextPopulation(Population<D, C> population, Function<D, C> fitnessFunction,
			Space<D> space, Comparator<C> goal) {

		List<Solution<D, C>> nextGen = new ArrayList<>();
		List<Solution<D, C>> parents = getNonDominatedParents(population, goal);
		for (int i = 0; i < this.populationSize; i += 2) {
			Solution<D, C> firstParent = parents.get(i % parents.size());
			Solution<D, C> secondParent = parents.get((i + 1) % parents.size());
			List<Solution<D, C>> parentsPair = Arrays.asList(firstParent, secondParent);

			List<Solution<D, C>> children = this.operator.apply(parentsPair, fitnessFunction, space);
			Solution<D, C> firstChild = children.get(0);
			Solution<D, C> secondChild = children.get(1);

			if (getDistance(firstParent, firstChild, secondParent, secondChild) <= getDistance(firstParent, secondChild,
					secondParent, firstChild)) {
				nextGen.add(Goal.getBestFromPopulation(Arrays.asList(firstParent, firstChild), goal));
				nextGen.add(Goal.getBestFromPopulation(Arrays.asList(secondParent, secondChild), goal));
			} else {
				nextGen.add(Goal.getBestFromPopulation(Arrays.asList(firstParent, secondChild), goal));
				nextGen.add(Goal.getBestFromPopulation(Arrays.asList(secondParent, firstChild), goal));
			}
		}
		return new Population<>(nextGen);
	}

	private List<Solution<D, C>> getNonDominatedParents(Population<D, C> population, Comparator<C> goal) {

		List<Solution<D, C>> parents = new ArrayList<>();
		for (Solution<D, C> individual : population.getPopulation()) {
			List<Solution<D, C>> freshParents = new ArrayList<>();
			boolean include = true;
			for (Solution<D, C> nd : parents) {
				int comparison = goal.compare(individual.getFitnessValue(), nd.getFitnessValue());
				if (comparison >= 0) {
					freshParents.add(nd);
				}
				if (comparison > 0) {
					include = false;
				}
			}
			if (include) {
				freshParents.add(individual);
			}
			parents = freshParents;
		}
		return parents;
	}

	private double getDistance(Solution<D, C> sol1, Solution<D, C> sol2, Solution<D, C> sol3, Solution<D, C> sol4) {

		return this.distance.calcule(sol1.getFitnessValue(), sol2.getFitnessValue())
				+ this.distance.calcule(sol3.getFitnessValue(), sol4.getFitnessValue());
	}

}
