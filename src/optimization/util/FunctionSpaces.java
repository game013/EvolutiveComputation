/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util;

import java.util.Arrays;

import optimization.function.space.HyperCube;
import optimization.function.space.Space;

/**
 * Class to create spaces for known functions.
 * 
 * @author Oscar Garavito
 *
 */
public final class FunctionSpaces {

	/**
	 * Private constructor to avoid instances.
	 */
	private FunctionSpaces() {

		// Nothing to do.
	}

	/**
	 * Gets an hyper cube for Rastrigin function with given dimension.
	 * 
	 * @param dimension
	 *            Dimension of the function.
	 * @return Hyper cube of Rastrigin function.
	 */
	public static Space<double[]> getRastriginSpace(int dimension) {

		double[] lowerBound = new double[dimension];
		Arrays.fill(lowerBound, -5.12);
		double[] upperBound = new double[dimension];
		Arrays.fill(upperBound, 5.12);
		return new HyperCube(lowerBound, upperBound);
	}

	/**
	 * Gets an hyper cube for Griewank function with given dimension.
	 * 
	 * @param dimension
	 *            Dimension of the function.
	 * @return Hyper cube of Griewank function.
	 */
	public static Space<double[]> getGriewankSpace(int dimension) {

		double[] lowerBound = new double[dimension];
		Arrays.fill(lowerBound, -600);
		double[] upperBound = new double[dimension];
		Arrays.fill(upperBound, 600);
		return new HyperCube(lowerBound, upperBound);
	}

}
