/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util;

import java.util.BitSet;

/**
 * @author Oscar Garavito
 *
 */
public final class BitUtils {

	/**
	 * 
	 */
	private BitUtils() {

		// Nothing to do.
	}

	public static long extractLong(BitSet bitSet, int from, int to) {

		long[] longArray = bitSet.get(from, to).toLongArray();
		long result = 0;
		if (longArray.length > 0) {
			result = longArray[0];
		}
		return result;
	}

}
