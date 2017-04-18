/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.function.space;

import java.util.ArrayList;
import java.util.List;

import optimization.util.type.Solution;

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
	default List<D> pick(int n) {

		List<D> elements = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			elements.add(this.pick());
		}
		return elements;
	}

	/**
	 * @param data
	 * @return
	 */
	D repair(D data);

	/**
	 * Repairs given solution, returning it to the space.
	 * 
	 * @param solution
	 * @return
	 */
	default D repair(Solution<D, ?> solution) {

		D data = solution.getSolution();
		return repair(data);
	}

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

	/**
	 * Gets the dimension size of the space.
	 * 
	 * @return
	 */
	int getDimension();

}
