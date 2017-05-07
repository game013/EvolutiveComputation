/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.function.fitness;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import funico.interpreter.Evaluator;
import funico.interpreter.GoalException;
import funico.interpreter.ProgramException;
import funico.language.LexicalException;
import funico.language.SyntacticalException;
import optimization.util.type.tree.FunicoNode;

/**
 * @author Oscar Garavito
 *
 */
public class FunicoFunction implements Function<List<FunicoNode>, Integer> {

	private final Map<String, String> examples;

	/**
	 * @param examples
	 */
	public FunicoFunction(Map<String, String> examples) {

		this.examples = examples;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.function.fitness.Function#calculate(java.lang.Object)
	 */
	@Override
	public Integer calculate(List<FunicoNode> params) {

		int result = 0;
		String equation = params.stream().map(FunicoNode::getEquation).collect(Collectors.joining(";"));
		for (Entry<String, String> entry : this.examples.entrySet()) {
			try {
				String evaluation = Evaluator.evalue(equation, entry.getKey(), 11);
				if (!evaluation.equals(entry.getValue())) {
					result++;
				}
			} catch (LexicalException | SyntacticalException | ProgramException | GoalException e) {
				result += this.examples.size();
			}
		}
		return result;
	}

}
