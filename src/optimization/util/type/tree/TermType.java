/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util.type.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oscar Garavito
 *
 */
public enum TermType {

	TERMINAL(false),

	VARIABLE(false),

	FUNCTOR(true);

	private final boolean requireChildren;

	private TermType(boolean requireChildren) {

		this.requireChildren = requireChildren;
	}

	/**
	 * @return the requireChildren
	 */
	public boolean isRequireChildren() {
		return requireChildren;
	}

	/**
	 * Returns values filtered by requires children or not.
	 * 
	 * @param requireChildren
	 * @return
	 */
	public static TermType[] values(boolean requireChildren) {

		List<TermType> foundTypes = new ArrayList<>();
		for (TermType type : TermType.values()) {
			if (type.requireChildren == requireChildren) {
				foundTypes.add(type);
			}
		}
		return foundTypes.toArray(new TermType[0]);
	}

}
