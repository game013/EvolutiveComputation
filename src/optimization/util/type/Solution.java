/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util.type;

/**
 * @author Oscar Garavito
 *
 */
public class Solution<D, C> {

	/**
	 * Actual solution.
	 */
	private final D solution;

	/**
	 * Fitness value.
	 */
	private final C fitnessValue;

	/**
	 * @param solution
	 * @param fitnessValue
	 */
	public Solution(D solution, C fitnessValue) {

		this.solution = solution;
		this.fitnessValue = fitnessValue;
	}

	/**
	 * @return the solution
	 */
	public D getSolution() {
		return solution;
	}

	/**
	 * @return the fitnessValue
	 */
	public C getFitnessValue() {
		return fitnessValue;
	}

}
