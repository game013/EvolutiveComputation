/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.search.genetic;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.List;

import optimization.function.fitness.Function;
import optimization.function.space.Space;
import optimization.genetic.operator.GeneticOperator;
import optimization.genetic.replace.GeneticReplacement;
import optimization.genetic.select.GeneticSelector;
import optimization.problem.ClassificationProblem;
import optimization.problem.OptimizationProblem;
import optimization.search.Search;
import optimization.search.termination.TerminationCondition;
import optimization.util.BitUtils;
import optimization.util.goal.Goal;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class CoevolutionaryGeneticAlgorithm implements Search<BitSet, Long> {

	private final TerminationCondition<Population<BitSet, Long>> terminationCondition;

	private final GeneticSelector<BitSet, Long> selector;

	private final GeneticOperator<BitSet, Long> geneticOperator;

	private final GeneticReplacement<BitSet, Long> replacement;

	private final int populationSize;

	private final int dataSize;

	public CoevolutionaryGeneticAlgorithm(TerminationCondition<Population<BitSet, Long>> terminationCondition,
			GeneticSelector<BitSet, Long> selector, GeneticOperator<BitSet, Long> geneticOperator,
			GeneticReplacement<BitSet, Long> replacement, int populationSize, int dataSize) {

		this.terminationCondition = terminationCondition;
		this.selector = selector;
		this.geneticOperator = geneticOperator;
		this.replacement = replacement;
		this.populationSize = populationSize;
		this.dataSize = dataSize;
	}

	@Override
	public Solution<BitSet, Long> solve(OptimizationProblem<BitSet, Long> problem) {

		throw new IllegalStateException("Method not valid for this genetic algorithm.");
	}

	public List<Solution<BitSet, Long>> solve(ClassificationProblem<BitSet> problem) {

		List<Population<BitSet, Long>> populations = this.loadFirstPopulations(problem);
		while (terminationCondition.test(populations.get(0))) {
			List<Population<BitSet, Long>> offspring = new ArrayList<>();
			for (int i = 0; i < populations.size(); i++) {
				offspring.add(this.nextPopulation(populations.get(i), problem.getClassificationFunctions().get(i),
						problem.getSpace(), Comparator.reverseOrder()));
			}
			offspring = recalculatePopulationsFitness(populations);
			for (int i = 0; i < populations.size(); i++) {
				populations.set(i, replacePopulation(populations.get(i), offspring.get(i),
						problem.getClassificationFunctions().get(i), problem.getSpace(), Comparator.reverseOrder()));
			}
			for (int i = 0; i < populations.size(); i++) {
				List<Solution<BitSet, Long>> unAppliedFixed = new ArrayList<>();
				for (Solution<BitSet, Long> solution : populations.get(i).getPopulation()) {
					unAppliedFixed.add(new Solution<>(solution.getSolution(), solution.getPartialFitnessValue()));
				}
				populations.set(i, new Population<>(unAppliedFixed));
			}
		}
		List<Solution<BitSet, Long>> bestResults = new ArrayList<>();
		for (Population<BitSet, Long> population : populations) {
			bestResults.add(Goal.getBestFromPopulation(population, Comparator.reverseOrder()));
		}
		return bestResults;
	}

	protected List<Population<BitSet, Long>> recalculatePopulationsFitness(List<Population<BitSet, Long>> populations) {

		List<Population<BitSet, Long>> fixedPopulations = new ArrayList<>();
		int popIdx = 0;
		for (Population<BitSet, Long> population : populations) {
			List<Solution<BitSet, Long>> fixedPopulation = new ArrayList<>();
			for (Solution<BitSet, Long> solution : population.getPopulation()) {
				long value = solution.getFitnessValue();
				for (int i = 0; i < populations.size(); i++) {
					if (i == popIdx) {
						continue;
					}
					long index = BitUtils.extractLong(solution.getSolution(), this.dataSize + (i * this.populationSize),
							this.dataSize + ((i + 1) * this.populationSize));
					value += populations.get(i).getPopulation().get((int) index).getFitnessValue();
				}
				Solution<BitSet, Long> fixedSolution = new Solution<>(solution.getSolution(), value,
						solution.getFitnessValue());
				fixedPopulation.add(fixedSolution);
			}
			fixedPopulations.add(new Population<>(fixedPopulation));
			popIdx++;
		}
		return fixedPopulations;
	}

	protected Population<BitSet, Long> nextPopulation(Population<BitSet, Long> population,
			Function<BitSet, Long> fitnessFunction, Space<BitSet> space, Comparator<Long> goal) {

		List<Solution<BitSet, Long>> parents = selector.selectParent(population, fitnessFunction, goal);
		return new Population<>(this.geneticOperator.apply(parents, fitnessFunction, space));
	}

	protected Population<BitSet, Long> replacePopulation(Population<BitSet, Long> population,
			Population<BitSet, Long> offspring, Function<BitSet, Long> fitnessFunction, Space<BitSet> space,
			Comparator<Long> goal) {

		return this.replacement.apply(population, offspring.getPopulation(), fitnessFunction, goal);
	}

	protected List<Population<BitSet, Long>> loadFirstPopulations(ClassificationProblem<BitSet> problem) {

		List<Population<BitSet, Long>> populations = new ArrayList<>();
		for (int i = 0; i < problem.getNumberOfClasses(); i++) {
			List<BitSet> pop = problem.getSpace().pick(this.populationSize);
			List<Solution<BitSet, Long>> initialSolutionList = new ArrayList<>(this.populationSize);
			for (BitSet individual : pop) {
				initialSolutionList.add(new Solution<>(individual, problem.getClassificationFunctions().get(i)));
			}
			populations.add(new Population<>(initialSolutionList));
		}
		return populations;
	}

}
