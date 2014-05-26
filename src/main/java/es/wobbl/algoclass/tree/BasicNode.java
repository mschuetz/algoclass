package es.wobbl.algoclass.tree;

class BasicNode<T extends Comparable<T>> extends Node<BasicNode<T>, T> {
	BasicNode(T value, BasicNode<T> a, BasicNode<T> b) {
		super(value, a, b);
	}

	BasicNode<T> insert(T value) {
		final int rel = value.compareTo(getValue());
		if (rel == 0) {
			return this;
		} else if (rel < 0) {
			if (getLeft() == null) {
				final BasicNode<T> newNode = new BasicNode<>(value, null, null);
				setLeft(newNode);
				return newNode;
			} else {
				return getLeft().insert(value);
			}
		} else {
			if (getRight() == null) {
				final BasicNode<T> newNode = new BasicNode<>(value, null, null);
				setRight(newNode);
				return newNode;
			} else {
				return getRight().insert(value);
			}
		}
	}
}
