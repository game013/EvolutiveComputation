/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package hilldescent.function;

import hilldescent.distribution.Distribution;

/**
 * @author Oscar Garavito
 *
 */
public final class FunctionHelper {

	/**
	 * @param numberOfVars
	 * @return
	 */
	public static double[] getStartRandomPoint(int numberOfVars, Function function) {

		double[] result = new double[numberOfVars];
		for (int i = 0; i < numberOfVars; i++) {
			result[i] = (Math.random() * (function.getUpperBound() - function.getLowerBound()))
					+ function.getLowerBound();
		}
		return result;
	}

	/**
	 * @param currentPoint
	 * @param distribution
	 * @param lowerBound
	 * @param upperBound
	 * @return
	 */
	public static double[] addDelta(double[] currentPoint, Distribution distribution, Function function) {

		double[] result = new double[currentPoint.length];
		for (int i = 0; i < currentPoint.length; i++) {
			result[i] = Math.max(function.getLowerBound(),
					Math.min(function.getUpperBound(), distribution.nextRandom() + currentPoint[i]));
		}
		return result;
	}

}
