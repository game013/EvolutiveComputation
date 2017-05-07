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
public class Node<T> implements Cloneable {

	private T data;

	private int qtyOfChildNodes;

	private List<Node<T>> children;

	public Node(T data) {

		this.data = data;
		this.children = new ArrayList<>();
		this.qtyOfChildNodes = 1;
	}

	/**
	 * @param node
	 */
	protected Node(Node<T> node) {

		this.data = node.data;
		this.children = new ArrayList<>();
		for (Node<T> child : node.children) {
			this.children.add(child.clone());
		}
		this.qtyOfChildNodes = node.qtyOfChildNodes;
	}

	/**
	 * @return the data
	 */
	public T getData() {

		return this.data;
	}

	/**
	 * @return the children
	 */
	public List<Node<T>> getChildren() {

		return this.children;
	}

	public Node<T> getChild(int index) {

		if (this.getQtyOfChildNodes() < index) {
			throw new IllegalArgumentException(
					"Given position to replace node exceeds the number of nodes contained in this tree");
		}
		if (index < 0) {
			throw new IllegalArgumentException("Position to replace cannot less than zero");
		}
		Node<T> node = null;
		if (index == 0) {
			node = this;
		} else {
			index--;
			for (Node<T> child : this.children) {
				int qty = child.getQtyOfChildNodes();
				if (index < qty) {
					node = child.getChild(index);
					break;
				}
				index -= qty;
			}
			this.qtyOfChildNodes = -1;
		}
		if (node == null) {
			throw new IllegalStateException(String.format("Node %d not found", index));
		}
		return node;
	}

	public void copyFrom(Node<T> node) {

		this.data = node.data;
		this.children = node.children;
		this.qtyOfChildNodes = node.qtyOfChildNodes;
	}

	public void addChild(Node<T> node) {

		this.qtyOfChildNodes = -1;
		this.children.add(node);
	}

	public boolean isLeaf() {

		return this.children.isEmpty();
	}

	public int getQtyOfChildNodes() {

		int result = 1;
		if (this.qtyOfChildNodes > 0) {
			result = this.qtyOfChildNodes;
		} else {
			if (!isLeaf()) {
				result = 1 + this.children.stream().mapToInt(Node::getQtyOfChildNodes).sum();
			}
		}
		return result;
	}

	/**
	 * Replaces content of child in given position by content of node.
	 * 
	 * @param pos
	 *            Position to find node in current node. If position is equals
	 *            to zero this node is replaced.
	 * @param node
	 *            Node with information to be replaced.
	 */
	public void replaceChild(int pos, Node<T> node) {

		this.getChild(pos).copyFrom(node);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Node<T> clone() {

		return new Node<T>(this);
	}

}
