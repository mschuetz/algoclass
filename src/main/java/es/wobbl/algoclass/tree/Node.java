package es.wobbl.algoclass.tree;

import com.google.common.base.Preconditions;

public class Node<N extends Node<N, T>, T extends Comparable<T>> {
	private N left, right;
	private final T value;

	public Node(T value, N left, N right) {
		this.value = value;
		if (left != null) {
			setLeft(left);
		}
		if (right != null) {
			setRight(right);
		}
	}

	public Node(T value) {
		this.value = value;
	}

	public N getLeft() {
		return left;
	}

	public void setLeft(N left) {
		Preconditions.checkArgument(left.getValue().compareTo(getValue()) < 0, "new left value %s must be less than %s",
				left.getValue(), getValue());
		this.left = left;
	}

	public N getRight() {
		return right;
	}

	public void setRight(N right) {
		Preconditions.checkArgument(right.getValue().compareTo(getValue()) > 0, "new right value %s must be greater than %s",
				right.getValue(), getValue());
		this.right = right;
	}

	public T getValue() {
		return value;
	}
}
