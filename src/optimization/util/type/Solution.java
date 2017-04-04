/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util.type;

import java.util.Optional;

import optimization.function.fitness.Function;

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
	 * Parameters associated to current solution.
	 */
	private final Optional<SolutionParameter> parameters;

	/**
	 * @param solution
	 * @param fitnessFunction
	 */
	public Solution(D solution, Function<D, C> fitnessFunction) {

		this(solution, fitnessFunction.calculate(solution));
	}

	/**
	 * @param solution
	 * @param fitnessValue
	 */
	public Solution(D solution, C fitnessValue) {

		this(solution, fitnessValue, Optional.empty());
	}

	/**
	 * @param solution
	 * @param fitnessValue
	 * @param parameters
	 */
	public Solution(D solution, C fitnessValue, SolutionParameter parameters) {

		this(solution, fitnessValue, Optional.ofNullable(parameters));
	}

	/**
	 * @param solution
	 * @param fitnessValue
	 * @param parameters
	 */
	public Solution(D solution, C fitnessValue, Optional<SolutionParameter> parameters) {

		this.solution = solution;
		this.fitnessValue = fitnessValue;
		this.parameters = parameters;
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

	/**
	 * @return the parameters
	 */
	public Optional<SolutionParameter> getParameters() {
		return parameters;
	}

	@Override
	public String toString() {

		String strRep;
		if (this.parameters.isPresent()) {
			strRep = String.format("%s -> %s (parameters: %s)", this.getSolution().toString(),
					this.getFitnessValue().toString(), this.parameters.get());
		} else {
			strRep = String.format("%s -> %s", this.getSolution().toString(), this.getFitnessValue().toString());
		}
		return strRep;
	}

}
