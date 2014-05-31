package es.wobbl.algoclass.tree;


class BasicNode<T extends Comparable<T>> extends Node<BasicNode<T>, T> {
	BasicNode(T value, BasicNode<T> parent, BasicNode<T> a, BasicNode<T> b) {
		super(value, parent, a, b);
	}

	BasicNode<T> insert(T value) {
		final int rel = value.compareTo(getValue());
		if (rel == 0) {
			return this;
		} else if (rel < 0) {
			if (getLeft() == null) {
				final BasicNode<T> newNode = new BasicNode<>(value, this, null, null);
				setLeft(newNode);
				return newNode;
			} else {
				return getLeft().insert(value);
			}
		} else {
			if (getRight() == null) {
				final BasicNode<T> newNode = new BasicNode<>(value, this, null, null);
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

	public BasicNode<T> predecessor() {
		BasicNode<T> cur = getLeft();
		if (cur != null)
			while (cur.getRight() != null)
				cur = cur.getRight();
		return cur;
	}

	public BasicNode<T> successor() {
		BasicNode<T> cur = getRight();
		if (cur != null)
			while (cur.getLeft() != null)
				cur = cur.getLeft();
		return cur;
	}

	void replaceInParent(BasicNode<T> replacement) {
		final BasicNode<T> parent = getParent();
		if (parent.getLeft() == this) {
			parent.setLeft(replacement);
		} else {
			parent.setRight(replacement);
		}
	}
}
