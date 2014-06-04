package es.wobbl.algoclass.tree;

public class RBNode<T extends Comparable<T>> extends Node<RBNode<T>, T> {

	public enum Colour {
		RED, BLACK
	}

	private Colour colour;

	RBNode(T value, RBNode<T> parent, RBNode<T> a, RBNode<T> b) {
		super(value, parent, a, b);
	}

	/*
	 * construct a node with two black null leaves
	 */
	RBNode(T value, RBNode<T> parent, Colour c) {
		super(value, parent, null, null);
		// setLeft(new RBNode<>(null, this, null, null, Colour.BLACK));
		// setRight(new RBNode<>(null, this, null, null, Colour.BLACK));
	}

	RBNode(T value, RBNode<T> parent, RBNode<T> a, RBNode<T> b, Colour c) {
		super(value, parent, a, b);
		this.setColour(c);
	}

	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}

	RBNode<T> grandparent() {
		final RBNode<T> parent = getParent();
		if (parent == null)
			return null;
		return parent.getParent();
	}

	RBNode<T> uncle() {
		final RBNode<T> gp = grandparent();
		if (gp == null)
			return null;
		if (gp.getLeft() == getParent())
			return gp.getRight();
		return gp.getLeft();
	}

	RBNode<T> insert(T value) {
		final int rel = value.compareTo(getValue());
		if (rel == 0) {
			return this;
		} else if (rel < 0) {
			if (getLeft() == null) {
				final RBNode<T> newNode = new RBNode<>(value, this, null, null, Colour.RED);
				setLeft(newNode);
				return newNode;
			} else {
				return getLeft().insert(value);
			}
		} else {
			if (getRight() == null) {
				final RBNode<T> newNode = new RBNode<>(value, this, null, null, Colour.RED);
				setRight(newNode);
				return newNode;
			} else {
				return getRight().insert(value);
			}
		}
	}

	public interface Visitor<V, T extends Comparable<T>> {
		V visit(RBNode<T> parent, RBNode<T> current);
	}

	<V> V lookup(T value, Visitor<V, T> visitor) {
		final int rel = value.compareTo(getValue());
		if (rel == 0) {
			return visitor.visit(null, this);
		}

		final RBNode<T> node = (rel < 0 ? getLeft() : getRight());

		if (node == null)
			return null;

		if (node.getValue().equals(value)) {
			return visitor.visit(this, node);
		}

		return node.lookup(value, visitor);
	}

	public RBNode<T> lookup(T value) {
		return lookup(value, (parent, current) -> {
			return current;
		});
	}
}
