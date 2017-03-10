/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.search.hillclimbing;

import optimization.function.Function;
import optimization.function.Space;

/**
 * @author Oscar Garavito
 *
 * @param <D>
 *            Domain of search.
 * @param <C>
 *            Codomain of search.
 */
public interface Search<D, C> {

	/**
	 * Solve searching.
	 * 
	 * @param space
	 *            Space where looking for.
	 * @param function
	 *            Function to evaluate fitness of solution.
	 * @return Found solution.
	 */
	C solve(Space<D> space, Function<D, C> function);

}
