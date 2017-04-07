/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.search.termination;

/**
 * @author Oscar Garavito
 *
 */
public interface TerminationCondition<T> {

	/**
	 * Starts termination condition.
	 */
	void init();

	/**
	 * Tests if its necessary to continue with next
	 * 
	 * @return {@code true} if requires to continue process, otherwise, if it's
	 *         necessary to finish it, returns {@code false}.
	 */
	boolean test(T data);

}
