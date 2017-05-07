/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator.crossover;

import java.util.List;
import java.util.stream.Collectors;

import optimization.function.fitness.Function;
import optimization.function.space.Space;
import optimization.util.type.Solution;
import optimization.util.type.tree.FunicoNode;
import optimization.util.type.tuple.Pair;

/**
 * @author Oscar Garavito
 *
 */
public class FunicoCrossover extends AbstractGeneticCrossover<List<FunicoNode>, Integer>
		implements
			GeneticCrossover<List<FunicoNode>, Integer> {

	public FunicoCrossover(double xoverProbability) {

		super(xoverProbability);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.operator.crossover.AbstractGeneticCrossover#xover(
	 * optimization.util.type.tuple.Pair,
	 * optimization.function.fitness.Function,
	 * optimization.function.space.Space)
	 */
	@Override
	protected Pair<List<FunicoNode>, List<FunicoNode>> xover(
			Pair<Solution<List<FunicoNode>, Integer>, Solution<List<FunicoNode>, Integer>> parents,
			Function<List<FunicoNode>, Integer> fitnessFunction, Space<List<FunicoNode>> space) {

		int leftEquationIndex = RANDOM.nextInt(parents.getLeft().getSolution().size());
		int rightEquationIndex = RANDOM.nextInt(parents.getRight().getSolution().size());

		List<FunicoNode> leftEquation = parents.getLeft().getSolution().stream().map(FunicoNode::clone)
				.collect(Collectors.toList());
		List<FunicoNode> rightEquation = parents.getRight().getSolution().stream().map(FunicoNode::clone)
				.collect(Collectors.toList());

		FunicoNode leftRoot = leftEquation.get(leftEquationIndex);
		FunicoNode rightRoot = rightEquation.get(rightEquationIndex);

		int leftNodeIndex = RANDOM.nextInt(leftRoot.getQtyOfChildNodes() - 1) + 1;
		int rightNodeIndex = RANDOM.nextInt(rightRoot.getQtyOfChildNodes() - 1) + 1;

		FunicoNode leftChild = (FunicoNode) leftRoot.getChild(leftNodeIndex).clone();
		FunicoNode rightChild = (FunicoNode) rightRoot.getChild(rightNodeIndex).clone();

		leftRoot.replaceChild(leftNodeIndex, rightChild);
		rightRoot.replaceChild(rightNodeIndex, leftChild);

		leftEquation.set(leftEquationIndex, leftRoot);
		rightEquation.set(rightEquationIndex, rightRoot);

		return new Pair<>(leftEquation, rightEquation);
	}

}
