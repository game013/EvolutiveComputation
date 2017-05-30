/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util.goal;

/**
 * @author Oscar Garavito
 *
 */
public class EuclideanDistance implements Distance<double[]> {

	/*
	 * (non-Javadoc)
	 * @see optimization.util.goal.Distance#calcule(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public double calcule(double[] o1, double[] o2) {

		double sumSqs = 0;
		for (int i = 0; i < o1.length; i++) {
			sumSqs += Math.pow(o1[i] - o2[i], 2);
		}
		return Math.sqrt(sumSqs);
	}

}
