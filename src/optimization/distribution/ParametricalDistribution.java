/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.distribution;

import java.util.Optional;

import optimization.util.type.SolutionParameter;

/**
 * @author Oscar Garavito
 *
 */
public interface ParametricalDistribution extends Distribution {

	/**
	 * Sets parameters taken from solution parameter.
	 * 
	 * @param parameters
	 */
	void setParameters(SolutionParameter parameters);

	/**
	 * Set parameters given optional of parameters. If present, another set
	 * parameters method is called.
	 * 
	 * @param parameters
	 *            Optional with parameters.
	 */
	default void setParameters(Optional<SolutionParameter> parameters) {

		if (parameters.isPresent()) {
			setParameters(parameters.get());
		}
	}

	/**
	 * Sets generic parameter.
	 * 
	 * @param parameter
	 */
	<T> void setParameter(T parameter);

}
