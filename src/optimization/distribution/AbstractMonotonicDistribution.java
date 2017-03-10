/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.distribution;

/**
 * @author Oscar Garavito
 *
 */
public abstract class AbstractMonotonicDistribution implements Distribution {

	/**
	 * Distribution of sign.
	 */
	private static final Distribution SIGN_DIST = BernoulliDistribution.getSignDistribution();

	/*
	 * (non-Javadoc)
	 * @see hilldescent.Distribution#nextRandom()
	 */
	@Override
	public double nextRandom() {

		return SIGN_DIST.nextRandom() * calculateInverse(Math.random());
	}

	protected abstract double calculateInverse(double uniformRandom);

}
