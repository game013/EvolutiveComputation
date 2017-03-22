/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util.goal;

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
	 * @return
	 */
	public static <D, C extends Comparable<C>> Solution<D, C> getBestFromPopulation(Population<D, C> population) {

		return getBestFromPopulation(population.getPopulation());
	}

	/**
	 * @param population
	 * @return
	 */
	public static <D, C extends Comparable<C>> Solution<D, C> getBestFromPopulation(List<Solution<D, C>> population) {

		return population.stream().sorted((s1, s2) -> s2.getFitnessValue().compareTo(s1.getFitnessValue())).findFirst()
				.orElse(null);
	}

	/**
	 * @param population
	 * @return
	 */
	public static <D, C extends Comparable<C>> Solution<D, C> getWorstFromPopulation(Population<D, C> population) {

		return getWorstFromPopulation(population.getPopulation());
	}

	/**
	 * @param population
	 * @return
	 */
	public static <D, C extends Comparable<C>> Solution<D, C> getWorstFromPopulation(List<Solution<D, C>> population) {

		return population.stream().sorted((s1, s2) -> s1.getFitnessValue().compareTo(s2.getFitnessValue())).findFirst()
				.orElse(null);
	}

}
