/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.search.termination;

/**
 * @author Oscar Garavito
 *
 */
public class ForLoopTerminationCondition<T> implements TerminationCondition<T> {

	/**
	 * Current count of iterations.
	 */
	private int counter;

	/**
	 * Maximum number of iterations.
	 */
	private final int maxIterations;

	/**
	 * @param maxIterations
	 */
	public ForLoopTerminationCondition(int maxIterations) {

		this.maxIterations = maxIterations;
		this.counter = 0;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.search.termination.TerminationCondition#init()
	 */
	@Override
	public void init() {

		this.counter = 0;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.search.termination.TerminationCondition#test(java.lang.
	 * Object)
	 */
	@Override
	public boolean test(T data) {

		if((this.counter + 1) % 1000 == 0) {
			System.out.println(String.format("Iteration: %d", this.counter + 1));
		}
		return this.counter++ < this.maxIterations;
	}

}
