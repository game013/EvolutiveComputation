/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.function;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oscar Garavito
 *
 */
public class HyperCube implements Space<double[]> {

	/**
	 * Lower bound of hyper cube. Each value for each dimension.
	 */
	public final double[] lowerBound;

	/**
	 * Upper bound of hyper cube. Each value for each dimension.
	 */
	public final double[] upperBound;

	/**
	 * Dimension of hyper cube.
	 */
	public final int dimension;

	/**
	 * Lower bound and upper bound arrays must be of same size.
	 * 
	 * @param lowerBound
	 * @param upperBound
	 */
	public HyperCube(double[] lowerBound, double[] upperBound) {

		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		if (this.lowerBound.length != this.upperBound.length) {
			throw new IllegalArgumentException("Lower bound and upper bound arrays must be of the same size.");
		}
		this.dimension = this.lowerBound.length;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.function.Space#pick()
	 */
	@Override
	public double[] pick() {

		double[] result = new double[this.dimension];
		for (int i = 0; i < this.dimension; i++) {
			result[i] = (Math.random() * (this.upperBound[i] - this.lowerBound[i])) + this.lowerBound[i];
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.function.Space#repair(java.lang.Object)
	 */
	@Override
	public double[] repair(double[] data) {

		double[] result = data.clone();
		for (int i = 0; i < data.length; i++) {
			result[i] = Math.max(this.lowerBound[i], Math.min(this.upperBound[i], data[i]));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.function.Space#getLowerBound()
	 */
	@Override
	public double[] getLowerBound() {

		return this.lowerBound;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.function.Space#getUpperBound()
	 */
	@Override
	public double[] getUpperBound() {

		return this.upperBound;
	}

	@Override
	public List<double[]> pick(int n) {

		List<double[]> elements = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			elements.set(i, this.pick());
		}
		return elements;
	}

}
