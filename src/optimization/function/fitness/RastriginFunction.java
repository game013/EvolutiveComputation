/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package optimization.function.fitness;

/**
 *
 * @author Estudiante
 */
public class RastriginFunction implements Function<double[], Double> {

	public static final double A = 10.0;

	private final int dimension;

	public RastriginFunction(int dimension) {

		this.dimension = dimension;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.function.Function#calculate(java.lang.Object)
	 */
	@Override
	public Double calculate(double[] params) {

		if (dimension != params.length) {
			throw new IllegalArgumentException(
					"Parameters given to this function must be equals to defined dimension size.");
		}
		double result = 0.0;
		for (double param : params) {
			result += calculate(param);
		}
		return result + (A * params.length);
	}

	/**
	 * @param param
	 * @return
	 */
	private double calculate(double param) {

		return Math.pow(param, 2.0) - A * Math.cos(2 * Math.PI * param);
	}

}
