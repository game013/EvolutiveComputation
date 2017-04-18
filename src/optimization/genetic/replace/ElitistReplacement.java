/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.replace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import optimization.function.fitness.Function;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class ElitistReplacement<D, C> implements GeneticReplacement<D, C> {

	private final int populationSize;

	private final boolean useCurrentPopulation;

	/**
	 * Percent value [0, 1] of elite.
	 */
	private final double eliteRate;

	/**
	 * Percent value [0, 1] of cull rate.
	 */
	private final double cullRate;

	/**
	 * Default value for elite rate.
	 */
	private static final double DEFAULT_ELITE_RATE = 0.1;

	/**
	 * Default value for cull rate.
	 */
	private static final double DEFAULT_CULL_RATE = 0.3;

	private static final Random RANDOM = new Random();

	/**
	 * @param populationSize
	 * @param useCurrentPopulation
	 * @param eliteRate
	 * @param cullRate
	 */
	public ElitistReplacement(int populationSize, boolean useCurrentPopulation, double eliteRate, double cullRate) {

		this.populationSize = populationSize;
		this.useCurrentPopulation = useCurrentPopulation;
		this.eliteRate = eliteRate;
		this.cullRate = cullRate;
	}

	/**
	 * @param populationSize
	 * @param useCurrentPopulation
	 */
	public ElitistReplacement(int populationSize, boolean useCurrentPopulation) {

		this(populationSize, useCurrentPopulation, DEFAULT_ELITE_RATE, DEFAULT_CULL_RATE);
	}

	/**
	 * @param populationSize
	 * @param useCurrentPopulation
	 * @param eliteRate
	 */
	public ElitistReplacement(int populationSize, boolean useCurrentPopulation, double eliteRate) {

		this(populationSize, useCurrentPopulation, eliteRate, DEFAULT_CULL_RATE);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.replace.GeneticReplacement#apply(optimization.util.
	 * type.Population, java.util.List, optimization.function.fitness.Function,
	 * java.util.Comparator)
	 */
	@Override
	public Population<D, C> apply(Population<D, C> population, List<Solution<D, C>> offspring,
			Function<D, C> fitnessFunction, Comparator<C> goal) {

		List<Solution<D, C>> poolSelection = new ArrayList<>(offspring);
		if (this.useCurrentPopulation) {
			poolSelection.addAll(population.getPopulation());
		}
		Collections.sort(poolSelection, Comparator.comparing(Solution::getFitnessValue, goal));

		List<Solution<D, C>> newOffspring = new ArrayList<>();

		int elite = (int) Math.ceil(this.eliteRate * poolSelection.size());
		int eliteAndMedianClass = (int) Math.floor((1.0 - this.cullRate) * poolSelection.size());
		for (int i = 0; i < this.populationSize; i += 2) {
			newOffspring.add(poolSelection.get(RANDOM.nextInt(elite)));
			newOffspring.add(poolSelection.get(RANDOM.nextInt(eliteAndMedianClass)));
		}
		return new Population<>(newOffspring);
	}

}
