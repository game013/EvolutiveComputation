/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.operator.mutation;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import optimization.function.fitness.Function;
import optimization.function.space.Space;
import optimization.util.type.tree.FunicoNode;
import optimization.util.type.tree.Node;
import optimization.util.type.tree.Operator;

/**
 * @author Oscar Garavito
 *
 */
public class FunicoMutator implements GeneticMutator<List<FunicoNode>, Integer> {

	/**
	 * Random utility.
	 */
	private static final Random RANDOM = new Random();

	/**
	 * 
	 */
	private final double mutationProbability;

	/**
	 * @param mutationProbability
	 */
	public FunicoMutator(double mutationProbability) {

		this.mutationProbability = mutationProbability;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.operator.mutation.GeneticMutator#mutate(java.lang.
	 * Object, optimization.function.fitness.Function,
	 * optimization.function.space.Space)
	 */
	@Override
	public List<FunicoNode> mutate(List<FunicoNode> child, Function<List<FunicoNode>, Integer> fitnessFunction,
			Space<List<FunicoNode>> space) {

		if (RANDOM.nextDouble() < this.mutationProbability) {
			switch (RANDOM.nextInt(3)) {
				case 0 :
					swapEquations(child);
					break;
				case 1 :
					addEquation(child, space);
					break;
				default :
					replaceNode(child, space);
					break;
			}
		}
		return space.repair(child);
	}

	private void swapEquations(List<FunicoNode> child) {

		int from = RANDOM.nextInt(child.size());
		int to = RANDOM.nextInt(child.size());
		Collections.swap(child, from, to);
	}

	private void addEquation(List<FunicoNode> child, Space<List<FunicoNode>> space) {

		Node<Operator> newNode = space.pick().get(0);
		child.add((FunicoNode) newNode);
	}

	private void replaceNode(List<FunicoNode> child, Space<List<FunicoNode>> space) {

		int equationIndex = RANDOM.nextInt(child.size());
		FunicoNode node = child.get(equationIndex);
		int childNodeIndex = RANDOM.nextInt(node.getQtyOfChildNodes() - 1) + 1;
		FunicoNode replacementRoot = space.pick().get(0);

		int replacementNodeIndex = RANDOM.nextInt(replacementRoot.getQtyOfChildNodes() - 1) + 1;

		Node<Operator> newNode = replacementRoot.getChild(replacementNodeIndex);
		node.replaceChild(childNodeIndex, newNode);
	}

}
