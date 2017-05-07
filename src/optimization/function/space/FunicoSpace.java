/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.function.space;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import optimization.util.type.tree.FunicoNode;
import optimization.util.type.tree.Operator;
import optimization.util.type.tree.TermType;

/**
 * @author Oscar Garavito
 *
 */
public class FunicoSpace implements Space<List<FunicoNode>> {

	/**
	 * 
	 */
	private static final Random RANDOM = new Random();

	/**
	 * 
	 */
	private final int maxQtyOfNodes;

	private final int maxQtyOfEquations;

	private final Map<String, Integer> functorsTable;
	private final Set<String> variables;
	private final Set<String> terminals;

	/**
	 * @param maxQtyOfNodes
	 */
	public FunicoSpace(int maxQtyOfNodes, int maxQtyOfEquations, Map<String, Integer> functorsTable,
			Set<String> variables, Set<String> terminals) {

		this.maxQtyOfNodes = maxQtyOfNodes;
		this.maxQtyOfEquations = maxQtyOfEquations;
		this.functorsTable = functorsTable;
		this.variables = variables;
		this.terminals = terminals;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.function.space.Space#pick()
	 */
	@Override
	public List<FunicoNode> pick() {

		int qtyOfEquations = RANDOM.nextInt(this.maxQtyOfEquations) + 1;
		List<FunicoNode> equations = new ArrayList<>(qtyOfEquations);
		for (int i = 0; i < qtyOfEquations; i++) {
			equations.add(buildNode());
		}
		return equations;
	}

	private FunicoNode buildNode() {

		int maxNodes = this.maxQtyOfNodes;
		Queue<FunicoNode> queue = new LinkedList<>();

		Operator rootOperator = new Operator(TermType.FUNCTOR, "=", 2);
		FunicoNode root = new FunicoNode(rootOperator);
		queue.add(root);

		while (!queue.isEmpty()) {
			FunicoNode node = queue.poll();
			maxNodes -= node.getData().getArity();

			for (int i = 0; i < node.getData().getArity(); i++) {

				Operator operator;
				if (maxNodes <= 1) {
					operator = new Operator(true, this.functorsTable, this.variables, this.terminals);
				} else {
					operator = new Operator(this.functorsTable, this.variables, this.terminals);
				}

				FunicoNode child = new FunicoNode(operator);
				node.addChild(child);
				queue.add(child);
			}
		}
		return root;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.function.space.Space#repair(java.lang.Object)
	 */
	@Override
	public List<FunicoNode> repair(List<FunicoNode> data) {

		List<FunicoNode> repairedData = new ArrayList<>(data);
		if (data.size() > this.maxQtyOfEquations) {
			repairedData = repairedData.subList(0, this.maxQtyOfEquations);
		}
		for (int i = 0;i < repairedData.size();i++) {
			FunicoNode root = repairedData.get(i);
			if (root.getQtyOfChildNodes() > this.maxQtyOfNodes) {
				repairedData.set(i, pick().get(0));
			}
		}
		return repairedData;
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.function.space.Space#getLowerBound()
	 */
	@Override
	public List<FunicoNode> getLowerBound() {

		throw new IllegalStateException("Lower bound not exists.");
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.function.space.Space#getUpperBound()
	 */
	@Override
	public List<FunicoNode> getUpperBound() {

		throw new IllegalStateException("Upper bound not exists.");
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.function.space.Space#getDimension()
	 */
	@Override
	public int getDimension() {

		return this.maxQtyOfNodes;
	}

}
