/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator.mutation;

/**
 * Adapter to genetic mutator interface.
 * 
 * @author Oscar Garavito
 *
 */
@FunctionalInterface
public interface MutatorAdapter<D, C> {

	/**
	 * Adapts given mutation. Transforms given mutation using last parameter to
	 * decide if adapts or not.
	 * 
	 * @param mutator
	 *            Mutator to be adapted.
	 * @param wasLastBetter
	 *            Indicates if last generation was better or not.
	 * @return New adapted mutator.
	 */
	GeneticMutator<D, C> adaptMutator(GeneticMutator<D, C> mutator, boolean wasLastBetter);

}
