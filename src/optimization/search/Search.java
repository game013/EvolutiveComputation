/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.search;

import optimization.problem.OptimizationProblem;
import optimization.util.type.Solution;

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
	Solution<D, C> solve(OptimizationProblem<D, C> problem);

}
