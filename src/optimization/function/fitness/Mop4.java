/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.function.fitness;

/**
 * @author Oscar Garavito
 *
 */
public class Mop4 implements Function<double[], double[]> {

	public static final double A = -10.0;

	public static final double B = -0.2;

	private final int dimension;

	public Mop4(int dimension) {

		this.dimension = dimension;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.function.fitness.Function#calculate(java.lang.Object)
	 */
	@Override
	public double[] calculate(double[] params) {

		if (dimension != params.length) {
			throw new IllegalArgumentException(
					"Parameters given to this function must be equals to defined dimension size.");
		}
		return new double[] { calculateF1(params), calculateF2(params) };
	}

	/**
	 * @param params
	 * @return
	 */
	private double calculateF1(double[] params) {

		double result = 0.0;
		for (int i = 0; i < params.length - 1; i++) {
			result += calculateF1(params[i], params[i + 1]);
		}
		return result;
	}

	/**
	 * @param param
	 * @return
	 */
	private double calculateF1(double param, double nextParam) {

		return A * Math.exp(B * Math.sqrt(Math.pow(param, 2) + Math.pow(nextParam, 2)));
	}

	/**
	 * @param params
	 * @return
	 */
	private double calculateF2(double[] params) {

		double result = 0.0;
		for (int i = 0; i < params.length; i++) {
			result += calculateF2(params[i]);
		}
		return result;
	}

	/**
	 * @param param
	 * @return
	 */
	private double calculateF2(double param) {

		return Math.pow(Math.abs(param), 0.8) + (5 * Math.sin(Math.pow(param, 3)));
	}

}
