/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package hilldescent.distribution;

/**
 * @author Oscar Garavito
 *
 */
public class BernoulliDistribution implements Distribution {

	private final double p;

	private final double q;

	private final double val1;

	private final double val2;

	public BernoulliDistribution(double p, double val1, double val2) {

		if (p < 0 || p > 1) {
			throw new IllegalArgumentException("P must be between [0, 1]");
		}
		this.p = p;
		this.q = 1 - p;
		this.val1 = val1;
		this.val2 = val2;
	}

	/*
	 * (non-Javadoc)
	 * @see hilldescent.Distribution#nextRandom()
	 */
	@Override
	public double nextRandom() {

		return Math.random() < this.p ? this.val1 : this.val2;
	}

	/**
	 * @return the p
	 */
	public double getP() {
		return p;
	}

	/**
	 * @return the q
	 */
	public double getQ() {
		return q;
	}

	/**
	 * @return the val1
	 */
	public double getVal1() {
		return val1;
	}

	/**
	 * @return the val2
	 */
	public double getVal2() {
		return val2;
	}

	/**
	 * Gets a Bernoulli distribution with 50/50 probability of each value and
	 * values are -1 and 1.
	 * 
	 * @return {@link Distribution} A Bernoulli distribution described above.
	 */
	public static Distribution getSignDistribution() {

		return new BernoulliDistribution(0.5, -1, 1);
	}

}
