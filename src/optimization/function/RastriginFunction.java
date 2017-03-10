/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package optimization.function;

import java.util.Arrays;

/**
 *
 * @author Estudiante
 */
public class RastriginFunction implements Function<double[], Double> {

	public static final double A = 10.0;

	public final Space<double[]> space;

	public RastriginFunction(int dimension) {

		double[] lowerBound = new double[dimension];
		Arrays.fill(lowerBound, -5.12);
		double[] upperBound = new double[dimension];
		Arrays.fill(upperBound, 5.12);
		this.space = new HyperCube(lowerBound, upperBound);
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.function.Function#calculate(java.lang.Object)
	 */
	@Override
	public Double calculate(double[] params) {

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

	/*
	 * (non-Javadoc)
	 * @see optimization.function.Function#getSpace()
	 */
	@Override
	public Space<double[]> getSpace() {

		return this.space;
	}

}
