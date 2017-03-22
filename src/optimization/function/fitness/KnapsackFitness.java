/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.function.fitness;

import java.util.BitSet;
import java.util.List;

import optimization.util.type.KnapsackElement;

/**
 * @author Oscar Garavito
 *
 */
public class KnapsackFitness implements Function<BitSet, Double> {

	/**
	 * Capacity of the knapsack.
	 */
	private final double knapsackCapacity;

	/**
	 * List of elements to be included in the knapsack.
	 */
	private final List<KnapsackElement> elements;

	/**
	 * @param knapsackCapacity
	 *            Capacity of the knapsack.
	 * @param elements
	 *            List of elements to be included in the knapsack.
	 */
	public KnapsackFitness(double knapsackCapacity, List<KnapsackElement> elements) {

		this.knapsackCapacity = knapsackCapacity;
		this.elements = elements;
	}

	@Override
	public Double calculate(BitSet t) {

		double value = 0, weight = 0;
		int maxIndex = Math.min(t.length(), this.elements.size());
		for (int i = 0; i < maxIndex; i++) {
			if(t.get(i)) {
				value += this.elements.get(i).getValue();
				weight += this.elements.get(i).getWeight();
			}
		}
		if (weight > this.knapsackCapacity) {
			value = 1 / (weight - this.knapsackCapacity);
		}
		return value;
	}

	/**
	 * @return the knapsackCapacity
	 */
	public double getKnapsackCapacity() {
		return knapsackCapacity;
	}

	/**
	 * @return the elements
	 */
	public List<KnapsackElement> getElements() {
		return elements;
	}

}
