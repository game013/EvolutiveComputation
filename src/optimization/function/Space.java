/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.function;

import java.util.List;

/**
 * @author Oscar Garavito
 * @param <D>
 *            Domain of space, same as a function.
 */
public interface Space<D> {
	
	/**
	 * @return
	 */
	D pick();
	
	/**
	 * @param n
	 * @return
	 */
	List<D> pick(int n);

	/**
	 * @param data
	 * @return
	 */
	D repair(D data);

	/**
	 * Get lower bound of space.
	 * 
	 * @return
	 */
	D getLowerBound();

	/**
	 * Get upper bound of space.
	 * 
	 * @return
	 */
	D getUpperBound();

}
