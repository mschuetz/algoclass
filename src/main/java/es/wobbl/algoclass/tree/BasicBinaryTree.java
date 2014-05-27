package es.wobbl.algoclass.tree;

import java.util.function.Consumer;

public class BasicBinaryTree<T extends Comparable<T>> implements BinaryTree<BasicNode<T>, T> {

	private BasicNode<T> root;

	@Override
	public BasicNode<T> getRoot() {
		return root;
	}

	private void setRoot(BasicNode<T> root) {
		this.root = root;
	}

	@Override
	public BasicNode<T> insert(T value) {
		if (root == null) {
			root = new BasicNode<>(value, null, null);
			return root;
		}
		return root.insert(value);
	}

	@Override
	public BasicNode<T> lookup(T value) {
		if (root == null)
			return null;
		return root.lookup(value);
	}

	@Override
	public void delete(T value) {
		if (root == null)
			return;
		root.lookup(value, (parent, current) -> {
			final Consumer<BasicNode<T>> setter;
			if (parent == null)
				setter = this::setRoot;
			else
				setter = current == parent.getLeft() ? parent::setLeft : parent::setRight;

			switch (current.childCount()) {
			case 0:
				setter.accept(null);
				break;
			case 1:
				if (current.getRight() == null)
					setter.accept(current.getLeft());
				else
					setter.accept(current.getRight());
				break;
			case 2:
				setter.accept(current.getLeft());
				final BasicNode<T> newNode = insert(current.getRight().getValue());
				newNode.setLeft(current.getRight().getLeft());
				newNode.setRight(current.getRight().getRight());
				break;
			default:
				throw new IllegalArgumentException("not a binary tree node");
			}
			return null;
		});
	}
}
