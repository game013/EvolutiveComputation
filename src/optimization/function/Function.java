/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package optimization.function;

/**
 * @author Oscar Garavito
 *
 * @param <D>
 *            Domain of the function
 * @param <C>
 *            Codomain of the function
 */
@FunctionalInterface
public interface Function<D, C> {

	/**
	 * Calculates function value for given parameters.
	 * 
	 * @param params
	 * @return
	 */
	C calculate(D params);

}
