/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.search.hillclimbing;

/**
 * @author Oscar Garavito
 *
 */
@FunctionalInterface
public interface Mutation<D> {

	D mutate(D origin);

}
