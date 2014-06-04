package es.wobbl.algoclass.tree;

import com.google.common.base.Preconditions;

public class Node<N extends Node<N, T>, T extends Comparable<T>> {
	private N left, right, parent;
	private T value;

	@SuppressWarnings("unchecked")
	public Node(T value, N parent, N left, N right) {
		this.value = value;
		if (parent != null) {
			setParent(parent);
		}
		if (left != null) {
			setLeft(left);
			left.setParent((N) this);
		}
		if (right != null) {
			setRight(right);
			right.setParent((N) this);
		}
	}

	public int childCount() {
		return (getLeft() == null ? 0 : 1) + (getRight() == null ? 0 : 1);
	}

	public Node(T value) {
		this.value = value;
	}

	public N getLeft() {
		return left;
	}

	public void setLeft(N left) {
		if (left != null)
			Preconditions.checkArgument(left.getValue().compareTo(getValue()) < 0, "new left value %s must be less than %s",
					left.getValue(), getValue());
		this.left = left;
	}

	public N getRight() {
		return right;
	}

	public void setRight(N right) {
		if (right != null)
			Preconditions.checkArgument(right.getValue().compareTo(getValue()) > 0, "new right value %s must be greater than %s",
					right.getValue(), getValue());
		this.right = right;
	}

	public T getValue() {
		return value;
	}

	void setValue(T value) {
		this.value = value;
	}

	public N getParent() {
		return parent;
	}

	void setParent(N parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "Node [value=" + value + " left=" + left + ", right=" + right + "]";
	}
}
