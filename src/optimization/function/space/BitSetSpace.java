/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.function.space;

import java.util.BitSet;
import java.util.Random;

/**
 * @author Oscar Garavito
 *
 */
public class BitSetSpace implements Space<BitSet> {

	/**
	 * Size of the bit set.
	 */
	private final int size;

	/**
	 * Random object.
	 */
	private static final Random RANDOM = new Random();

	/**
	 * Constructor with given dimension of the bit set.
	 * 
	 * @param size
	 *            Dimension of the bit set.
	 */
	public BitSetSpace(int size) {

		this.size = size;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.function.space.Space#pick()
	 */
	@Override
	public BitSet pick() {

		// BitSet element = new BitSet(this.size);
		BitSet element = new BitSet();
		for (int i = 0; i < this.size; i++) {
			element.set(i, RANDOM.nextBoolean());
		}
		return element;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.function.space.Space#repair(java.lang.Object)
	 */
	@Override
	public BitSet repair(BitSet data) {

		BitSet repairedData = data;
		if (data.length() > this.size) {
			repairedData = data.get(0, this.size);
		}
		return repairedData;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.function.space.Space#getLowerBound()
	 */
	@Override
	public BitSet getLowerBound() {

		throw new IllegalStateException("Lower bound not exists.");
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.function.space.Space#getUpperBound()
	 */
	@Override
	public BitSet getUpperBound() {

		throw new IllegalStateException("Lower bound not exists.");
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.function.space.Space#getDimension()
	 */
	@Override
	public int getDimension() {

		return this.size;
	}

}
