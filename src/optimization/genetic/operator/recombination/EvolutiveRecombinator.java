/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator.recombination;

import java.util.List;

import optimization.function.fitness.Function;
import optimization.util.type.Solution;
import optimization.util.type.SolutionParameter;

/**
 * @author Oscar Garavito
 *
 */
public interface EvolutiveRecombinator<D, C> {

	/**
	 * @param parents
	 * @param fitnessFunction
	 * @return
	 */
	default Solution<D, C> recombine(List<Solution<D, C>> parents, Function<D, C> fitnessFunction) {

		SolutionParameter parameters = this.recombineParameters(parents);
		D solution = this.recombine(parents);
		return new Solution<>(solution, fitnessFunction, parameters);
	}

	/**
	 * Recombines from parents to form a child.
	 * 
	 * @param parents
	 *            List of parents to be recombined.
	 * @return Produced child after recombination.
	 */
	D recombine(List<Solution<D, C>> parents);

	/**
	 * Recombines parameters from parents.
	 * 
	 * @param parents
	 *            List of parents to recombine parameters.
	 * @return Produced recombined solution parameter.
	 */
	SolutionParameter recombineParameters(List<Solution<D, C>> parents);

}
