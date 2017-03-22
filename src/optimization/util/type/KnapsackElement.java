/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util.type;

/**
 * @author Oscar Garavito
 *
 */
public class KnapsackElement {

	/**
	 * Weight of the element.
	 */
	private final double weight;

	/**
	 * Value of the element.
	 */
	private final double value;

	/**
	 * @param weight
	 * @param value
	 */
	public KnapsackElement(double weight, double value) {

		this.weight = weight;
		this.value = value;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

}
