/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import optimization.function.fitness.Function;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class ElitistSelector<D, C> extends AbstractGeneticSelector<D, C> implements GeneticSelector<D, C> {

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

	/**
	 * Elite and cull rates must be a value between [0, 1]. The sum of the both
	 * values cannot be greater than 1.
	 * 
	 * @param eliteRate
	 * @param cullRate
	 */
	public ElitistSelector(int parentsSampleSize, double eliteRate, double cullRate) {

		super(parentsSampleSize);
		validateRate(eliteRate, "elite");
		validateRate(cullRate, "cull");
		if (eliteRate + cullRate > 1) {
			throw new IllegalArgumentException("Sum of elite and cull rates cannot be greater than 1.");
		}
		this.eliteRate = eliteRate;
		this.cullRate = cullRate;
	}

	/**
	 * Creates a elitist replacement with default values, 10% for elite and 30%
	 * for cull.
	 */
	public ElitistSelector(int parentsSampleSize) {

		this(parentsSampleSize, DEFAULT_ELITE_RATE, DEFAULT_CULL_RATE);
	}

	/**
	 * Creates a elitist replacement with default value of 30% for cull.
	 * 
	 * @param eliteRate
	 */
	public ElitistSelector(int parentsSampleSize, double eliteRate) {

		this(parentsSampleSize, eliteRate, DEFAULT_CULL_RATE);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.select.GeneticSelector#selectParent(optimization.
	 * util.type.Population, optimization.function.fitness.Function,
	 * java.util.Comparator)
	 */
	@Override
	public List<Solution<D, C>> selectParent(Population<D, C> population, Function<D, C> fitnessFunction,
			Comparator<C> goal) {

		List<Solution<D, C>> selectedParents = new ArrayList<>();
		List<Solution<D, C>> orderedPopulation = new ArrayList<>(population.getPopulation());
		Collections.sort(orderedPopulation, Comparator.comparing(Solution::getFitnessValue, goal));
		int elite = (int) Math.ceil(this.eliteRate * population.getSize());
		int eliteAndMedianClass = (int) Math.floor((1.0 - this.cullRate) * population.getSize());
		for (int i = 0; i < this.parentsSampleSize; i += 2) {
			selectedParents.add(orderedPopulation.get(random.nextInt(elite)));
			selectedParents.add(orderedPopulation.get(random.nextInt(eliteAndMedianClass)));
		}
		return selectedParents;
	}

	/**
	 * @return the eliteRate
	 */
	public double getEliteRate() {
		return eliteRate;
	}

	/**
	 * @return the cullRate
	 */
	public double getCullRate() {
		return cullRate;
	}

	/**
	 * @param rate
	 * @param type
	 */
	private void validateRate(double rate, String type) {

		if (rate < 0 || rate > 1) {
			throw new IllegalArgumentException(String.format("Rate %s must be a value in range [0, 1]", type));
		}
	}

}
