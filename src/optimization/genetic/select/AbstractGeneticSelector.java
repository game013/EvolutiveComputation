/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.select;

import java.util.Random;

/**
 * @author Oscar Garavito
 *
 */
public abstract class AbstractGeneticSelector<D> implements GeneticSelector<D> {

	/**
	 * Size of parent's sample.
	 */
	protected final int parentsSampleSize;

	/**
	 * Random object initialized in the constructor.
	 */
	protected final Random random;

	/**
	 * Constructor of abstract selector indicating the parent's sample size.
	 * 
	 * @param parentsSampleSize
	 *            Indicates the parent's sample size.
	 */
	protected AbstractGeneticSelector(int parentsSampleSize) {

		this.parentsSampleSize = parentsSampleSize;
		this.random = new Random();
	}

}
