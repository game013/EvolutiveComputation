/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.distribution;

/**
 * @author Oscar Garavito
 *
 */
public class ParetoDistribution extends AbstractMonotonicDistribution implements Distribution {

	private final double a;

	private final double b;

	/**
	 * @param a
	 * @param b
	 */
	public ParetoDistribution(double a, double b) {

		this.a = a;
		this.b = b;
	}

	/**
	 * @param uniformRandom
	 * @return
	 */
	protected double calculateInverse(double uniformRandom) {

		return this.b / Math.pow(1 - uniformRandom, 1 / this.a);
	}

	/**
	 * @return the a
	 */
	public double getA() {
		return a;
	}

	/**
	 * @return the b
	 */
	public double getB() {
		return b;
	}

}
