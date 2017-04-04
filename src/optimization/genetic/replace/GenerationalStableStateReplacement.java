/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.replace;

import java.util.Comparator;
import java.util.List;

import optimization.function.fitness.Function;
import optimization.util.goal.Goal;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class GenerationalStableStateReplacement<D, C extends Comparable<C>> implements GeneticReplacement<D, C> {

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.replace.GeneticReplacement#apply(optimization.util.
	 * type.Population, java.util.List, optimization.function.fitness.Function)
	 */
	@Override
	public Population<D, C> apply(Population<D, C> population, List<Solution<D, C>> offspring,
			Function<D, C> fitnessFunction, Comparator<C> goal) {

		Solution<D, C> bestFromOriginalPopulation = Goal.getBestFromPopulation(population, goal);
		Solution<D, C> bestFromOffspring = Goal.getBestFromPopulation(offspring, goal);
		Population<D, C> newPopulation = new Population<>(offspring);
		if (bestFromOriginalPopulation.getFitnessValue().compareTo(bestFromOffspring.getFitnessValue()) > 0) {
			Solution<D, C> worstFromOffspring = Goal.getWorstFromPopulation(offspring, goal);
			for (int i = 0; i < newPopulation.getSize(); i++) {
				if (newPopulation.get(i).getFitnessValue().equals(worstFromOffspring.getFitnessValue())) {
					newPopulation.set(i, bestFromOriginalPopulation);
					break;
				}
			}
		}
		return newPopulation;
	}

}
