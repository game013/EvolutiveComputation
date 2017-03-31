/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package optimization.distribution;

import java.util.Random;

/**
 *
 * @author Estudiante
 */
public class NormalDistribution implements Distribution {

	/**
	 * Standard deviation to calculate next random.
	 */
	private double sigma;

	/**
	 * Random constant to calculate next Gaussian number.
	 */
	private static final Random RANDOM = new Random();

	/**
	 * @param sigma
	 */
	public NormalDistribution(double sigma) {

		this.sigma = sigma;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.distribution.Distribution#nextRandom()
	 */
	@Override
	public double nextRandom() {

		return RANDOM.nextGaussian() * this.sigma;
	}

	/**
	 * @return the sigma
	 */
	public double getSigma() {
		return sigma;
	}

	/**
	 * @param sigma
	 *            the sigma to set
	 */
	public void setSigma(double sigma) {
		this.sigma = sigma;
	}

}
