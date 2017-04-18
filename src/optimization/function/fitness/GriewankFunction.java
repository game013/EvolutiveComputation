/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.function.fitness;

/**
 * Griewank function calculation of fitness, to be used in optimization tests.
 * 
 * @author Oscar Garavito
 *
 */
public class GriewankFunction implements Function<double[], Double> {

	/**
	 * Used as divisor in calculation of fitness function.
	 */
	public static final double DIVISOR = 4000;

	/**
	 * Addend used in calculation of fitness function.
	 */
	public static final double ADDEND = 1;

	/*
	 * (non-Javadoc)
	 * @see optimization.function.fitness.Function#calculate(java.lang.Object)
	 */
	@Override
	public Double calculate(double[] params) {

		double result = 0, sustraend = 1;
		for (int i = 0; i < params.length; i++) {
			result += (Math.pow(params[i], 2) / DIVISOR);
			sustraend *= Math.cos(params[i] / Math.sqrt(i + 1));
		}
		return result - sustraend + ADDEND;
	}

}
