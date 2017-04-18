/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.search.genetic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import optimization.function.fitness.Function;
import optimization.function.space.Space;
import optimization.problem.OptimizationProblem;
import optimization.search.Search;
import optimization.search.termination.TerminationCondition;
import optimization.util.type.Population;
import optimization.util.type.Solution;
import optimization.util.type.tuple.Triple;

/**
 * @author Oscar Garavito
 *
 */
public class DifferentialEvolution extends AbstractGeneticAlgorithm<double[], Double>
		implements
			Search<double[], Double> {

	private static final Random RANDOM = new Random();

	private final double cross_probability;

	private final double force;

	public DifferentialEvolution(int populationSize,
			TerminationCondition<Population<double[], Double>> terminationCondition, double cross_probability,
			double force) {

		super(populationSize, terminationCondition);
		this.cross_probability = cross_probability;
		this.force = force;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.search.genetic.AbstractGeneticAlgorithm#loadFirstPopulation(
	 * optimization.problem.OptimizationProblem)
	 */
	@Override
	protected Population<double[], Double> loadFirstPopulation(OptimizationProblem<double[], Double> problem) {

		List<double[]> pop = problem.getSpace().pick(this.populationSize);
		List<Solution<double[], Double>> initialSolutionList = new ArrayList<>(this.populationSize);
		for (double[] individual : pop) {
			initialSolutionList.add(new Solution<>(individual, problem.getFitnessFunction()));
		}
		Population<double[], Double> population = new Population<>(initialSolutionList);
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
	protected Population<double[], Double> nextPopulation(Population<double[], Double> population,
			Function<double[], Double> fitnessFunction, Space<double[]> space, Comparator<Double> goal) {

		List<Solution<double[], Double>> newPop = new ArrayList<>(this.populationSize);
		int index = 0;
		for (Solution<double[], Double> individual : population.getPopulation()) {
			Solution<double[], Double> child = this.nextIndividual(population, fitnessFunction, space, individual,
					index++);
			if (goal.compare(child.getFitnessValue(), individual.getFitnessValue()) < 0) {
				newPop.add(child);
			} else {
				newPop.add(individual);
			}
		}
		return new Population<>(newPop);
	}

	/**
	 * @param population
	 * @param fitnessFunction
	 * @param space
	 * @param individual
	 * @param index
	 * @return
	 */
	private Solution<double[], Double> nextIndividual(Population<double[], Double> population,
			Function<double[], Double> fitnessFunction, Space<double[]> space, Solution<double[], Double> individual,
			int index) {

		Triple<Solution<double[], Double>> parents = pickParents(population, index);
		int r = RANDOM.nextInt(space.getDimension()) + 1;
		double[] newSolution = new double[space.getDimension()];
		for (int i = 0; i < space.getDimension(); i++) {
			double cross = RANDOM.nextDouble();
			if (cross < this.cross_probability || i == r) {
				newSolution[i] = parents.getLeft().getSolution()[i]
						+ (this.force * (parents.getCenter().getSolution()[i] - parents.getRight().getSolution()[i]));
			} else {
				newSolution[i] = individual.getSolution()[i];
			}
		}
		return new Solution<>(space.repair(newSolution), fitnessFunction);
	}

	/**
	 * @param population
	 * @param index
	 * @return
	 */
	private Triple<Solution<double[], Double>> pickParents(Population<double[], Double> population, int index) {

		Set<Integer> random = new HashSet<>();
		while (random.size() < 3) {
			int rnd = RANDOM.nextInt(populationSize);
			if (!random.contains(rnd) && rnd != index) {
				random.add(rnd);
			}
		}
		List<Integer> parents = new ArrayList<>(random);
		return new Triple<>(population.get(parents.get(0)), population.get(parents.get(1)),
				population.get(parents.get(2)));
	}

}
