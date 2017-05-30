/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator.recombination;

import java.util.Comparator;
import java.util.List;

import optimization.util.type.Solution;
import optimization.util.type.SolutionParameter;
import optimization.util.type.constant.ParameterName;

/**
 * @author Oscar Garavito
 *
 */
public class IntermediateRecombinator<C> implements EvolutionaryRecombinator<double[], C> {

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.operator.recombination.EvolutionaryRecombinator#
	 * recombine(java.util.List, java.util.Comparator)
	 */
	@Override
	public double[] recombine(List<Solution<double[], C>> parents, Comparator<C> goal) {

		double[] child;
		if (!parents.isEmpty()) {
			Solution<double[], C> firstParent = parents.get(0);
			child = new double[firstParent.getSolution().length];
			for (int i = 0; i < child.length; i++) {
				double value = 0;
				for (int j = 0; j < parents.size(); j++) {
					value += parents.get(j).getSolution()[i];
				}
				child[i] = value / parents.size();
			}
		} else {
			throw new IllegalArgumentException("Parents list cannot be empty.");
		}
		return child;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.operator.recombination.EvolutionaryRecombinator#
	 * recombineParameters(java.util.List, java.util.Comparator)
	 */
	@Override
	public SolutionParameter recombineParameters(List<Solution<double[], C>> parents, Comparator<C> goal) {

		SolutionParameter defaultParameter = new SolutionParameter();
		defaultParameter.set(ParameterName.SIGMA, 0.0);
		double value = 0;

		for (int i = 0; i < parents.size(); i++) {
			value += (double) parents.get(i).getParameters().orElse(defaultParameter).get(ParameterName.SIGMA);
		}
		SolutionParameter parameter = new SolutionParameter();
		parameter.set(ParameterName.SIGMA, value / parents.size());
		return parameter;
	}

}
