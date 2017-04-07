/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.parameter;

import optimization.util.type.SolutionParameter;

/**
 * @author Oscar Garavito
 *
 */
@FunctionalInterface
public interface ParameterGenerator {

	/**
	 * Generates a parameter for initial solution.
	 * 
	 * @return
	 */
	SolutionParameter generateParameters();

}
