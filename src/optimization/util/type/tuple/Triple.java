/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util.type.tuple;

/**
 * Triple class to be used as utility in project.
 * 
 * @author Oscar Garavito
 *
 */
public class Triple<T> {

	/**
	 * 
	 */
	private final T left;

	/**
	 * 
	 */
	private final T center;

	/**
	 * 
	 */
	private final T right;

	/**
	 * @param left
	 * @param center
	 * @param right
	 */
	public Triple(T left, T center, T right) {

		this.left = left;
		this.center = center;
		this.right = right;
	}

	/**
	 * @return the left
	 */
	public T getLeft() {
		return left;
	}

	/**
	 * @return the center
	 */
	public T getCenter() {
		return center;
	}

	/**
	 * @return the right
	 */
	public T getRight() {
		return right;
	}

}
