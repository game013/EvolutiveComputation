/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util.type.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Oscar Garavito
 *
 */
public class FunicoNode extends Node<Operator> {

	/**
	 * @param data
	 */
	public FunicoNode(Operator data) {

		super(data);
	}

	public FunicoNode(FunicoNode funicoNode) {

		super(funicoNode);
	}

	public String getEquation() {

		List<String> equation = new ArrayList<>();
		if (this.getData().getValue().equals("=")) {
			FunicoNode leftNode = (FunicoNode) this.getChildren().get(0);
			FunicoNode rightNode = (FunicoNode) this.getChildren().get(1);

			equation.add(leftNode.getEquation());
			equation.add(" ");
			equation.add(this.getData().getValue());
			equation.add(" ");
			equation.add(rightNode.getEquation());
		} else {
			equation.add(this.getData().getValue());
			if (this.getData().getType().isRequireChildren()) {
				equation.add("(");
				List<String> parameters = new ArrayList<>();
				for (Node<Operator> child : this.getChildren()) {
					FunicoNode node = (FunicoNode) child;
					parameters.add(node.getEquation());
				}
				equation.add(parameters.stream().collect(Collectors.joining(", ")));
				equation.add(")");
			}
		}
		return equation.stream().collect(Collectors.joining(""));
	}

	/*
	 * (non-Javadoc)
	 * @see optimization.util.type.tree.Node#clone()
	 */
	@Override
	public FunicoNode clone() {

		return new FunicoNode(this);
	}

	@Override
	public String toString() {

		return getEquation();
	}

}
