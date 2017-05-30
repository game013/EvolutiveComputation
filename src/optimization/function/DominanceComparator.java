/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.function;

import java.util.Comparator;

/**
 * @author Oscar Garavito
 *
 */
public class DominanceComparator implements Comparator<double[]> {

	/**
	 * 
	 */
	private final boolean[] minimize;

	/**
	 * @param minimize
	 */
	public DominanceComparator(boolean[] minimize) {

		this.minimize = minimize;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(double[] o1, double[] o2) {

		if (this.minimize.length != o1.length || this.minimize.length != o2.length) {
			throw new IllegalArgumentException(
					"Lenght of solutions doesn't match with length of objects to be compared");
		}

		boolean domFirst = false, domSecond = false;
		for (int i = 0; i < this.minimize.length; i++) {
			Comparator<Double> cmp = this.minimize[i] ? Comparator.naturalOrder() : Comparator.reverseOrder();
			int comparison = cmp.compare(o1[i], o2[i]);
			if (comparison < 0) {
				domFirst = true;
			} else if (comparison > 0) {
				domSecond = true;
			}
		}
		return domFirst ^ domSecond ? (domFirst ? -1 : 1) : 0;
	}

}
