/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util.goal;

import java.util.Comparator;
import java.util.List;

import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public final class Goal {

	/**
	 * @param population
	 * @param goal
	 * @return
	 */
	public static <D, C> Solution<D, C> getBestFromPopulation(Population<D, C> population, Comparator<C> goal) {

		return getBestFromPopulation(population.getPopulation(), goal);
	}

	/**
	 * @param population
	 * @param goal
	 * @return
	 */
	public static <D, C> Solution<D, C> getBestFromPopulation(List<Solution<D, C>> population, Comparator<C> goal) {

		return population.stream().sorted(Comparator.comparing(Solution::getFitnessValue, goal)).findFirst()
				.orElse(null);
	}

	/**
	 * @param population
	 * @param goal
	 * @return
	 */
	public static <D, C extends Comparable<C>> Solution<D, C> getWorstFromPopulation(Population<D, C> population,
			Comparator<C> goal) {

		return getWorstFromPopulation(population.getPopulation(), goal);
	}

	/**
	 * @param population
	 * @param goal
	 * @return
	 */
	public static <D, C extends Comparable<C>> Solution<D, C> getWorstFromPopulation(List<Solution<D, C>> population,
			Comparator<C> goal) {

		return population.stream().sorted(Comparator.comparing(Solution::getFitnessValue, goal.reversed())).findFirst()
				.orElse(null);
	}

}
