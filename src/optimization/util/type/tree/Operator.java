/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util.type.tree;

import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * @author Oscar Garavito
 *
 */
public class Operator {

	private static final Random RANDOM = new Random();

	private final TermType type;

	private final String value;

	private final int arity;

	public Operator(TermType type, String value, int arity) {

		this.type = type;
		this.value = value;
		this.arity = arity;
	}

	public Operator(boolean leaf, Map<String, Integer> functorsTable, Set<String> variables, Set<String> terminals) {

		this(TermType.values(!leaf), functorsTable, variables, terminals);
	}

	public Operator(Map<String, Integer> functorsTable, Set<String> variables, Set<String> terminals) {

		this(TermType.values(), functorsTable, variables, terminals);
	}

	private Operator(TermType[] types, Map<String, Integer> functorsTable, Set<String> variables,
			Set<String> terminals) {

		int arity = 0;
		String value = "";
		TermType type = types[RANDOM.nextInt(types.length)];

		switch (type) {
			case VARIABLE :
				value = variables.toArray(new String[0])[RANDOM.nextInt(variables.size())];
				break;
			case TERMINAL :
				value = terminals.toArray(new String[0])[RANDOM.nextInt(terminals.size())];
				break;
			case FUNCTOR :
				String[] functors = functorsTable.keySet().toArray(new String[0]);
				int rnd = RANDOM.nextInt(functors.length);
				value = functors[rnd];
				arity = functorsTable.get(value);
				break;
		}

		this.type = type;
		this.arity = arity;
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {

		return this.value;
	}

	/**
	 * @return the type
	 */
	public TermType getType() {

		return this.type;
	}

	/**
	 * @return the arity.
	 */
	public int getArity() {

		return this.arity;
	}

}
