/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util.goal;

/**
 * @author Oscar Garavito
 *
 */
public interface Distance<T> {

	/**
	 * Calculates the distance between two objects.
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	double calcule(T o1, T o2);

}
