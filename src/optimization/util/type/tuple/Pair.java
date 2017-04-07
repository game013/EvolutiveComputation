/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util.type.tuple;

/**
 * Pair of generic types to be used.
 * 
 * @author Oscar Garavito
 *
 */
public class Pair<L, R> {

	/**
	 * 
	 */
	private final L left;

	/**
	 * 
	 */
	private final R right;

	/**
	 * @param left
	 * @param right
	 */
	public Pair(L left, R right) {

		this.left = left;
		this.right = right;
	}

	/**
	 * @return the left
	 */
	public L getLeft() {
		return left;
	}

	/**
	 * @return the right
	 */
	public R getRight() {
		return right;
	}

}