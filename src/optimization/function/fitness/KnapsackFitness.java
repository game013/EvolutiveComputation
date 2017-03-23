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

	/*
	 * (non-Javadoc)
	 * @see optimization.function.fitness.Function#calculate(java.lang.Object)
	 */
	//@Override
	public Double calculateOld(BitSet t) {

		double value = 0, weight = 0;
		for (int i = 0; i < this.elements.size(); i++) {
			if(t.get(i)) {
				value += this.elements.get(i).getValue();
				weight += this.elements.get(i).getWeight();
				if (weight > this.knapsackCapacity) {
					break;
				}
			}
		}
		if (weight > this.knapsackCapacity) {
			value = 1 / (weight - this.knapsackCapacity);
		}
		return value;
	}
	public Double calculate(BitSet t) {

		double value = 0, weight = 0;
		for (int i = t.nextSetBit(0); i >= 0; i = t.nextSetBit(i + 1)) {
			if (i == Integer.MAX_VALUE) {
				break; // or (i+1) would overflow
			}
			value += this.elements.get(i).getValue();
			weight += this.elements.get(i).getWeight();
			if (weight > this.knapsackCapacity) {
				break;
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
