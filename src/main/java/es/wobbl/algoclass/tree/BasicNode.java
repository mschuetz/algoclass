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

	public interface Visitor<V, T extends Comparable<T>> {
		V visit(BasicNode<T> parent, BasicNode<T> current);
	}

	<V> V lookup(T value, Visitor<V, T> visitor) {
		final int rel = value.compareTo(getValue());
		if (rel == 0) {
			return visitor.visit(null, this);
		}

		final BasicNode<T> node = (rel < 0 ? getLeft() : getRight());

		if (node == null)
			return null;

		if (node.getValue().equals(value)) {
			return visitor.visit(this, node);
		}

		return node.lookup(value, visitor);
	}

	BasicNode<T> lookup(T value) {
		return lookup(value, (parent, current) -> {
			return current;
		});
	}
}
