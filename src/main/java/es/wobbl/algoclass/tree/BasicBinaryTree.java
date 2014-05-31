package es.wobbl.algoclass.tree;

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
			root = new BasicNode<>(value, null, null, null);
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
		delete(root, value);
	}

	void delete(BasicNode<T> current, T value) {
		final int cmp = value.compareTo(current.getValue());
		if (cmp < 0) {
			if (current.getLeft() != null)
				delete(current.getLeft(), value);
		} else if (cmp > 0) {
			if (current.getRight() != null)
				delete(current.getRight(), value);
		} else {
			switch (current.childCount()) {
			case 0:
				if (current != getRoot())
					current.replaceInParent(null);
				else
					setRoot(null);
				break;
			case 1:
				final BasicNode<T> replacement = current.getRight() != null ? current.getRight() : current.getLeft();
				if (current != getRoot())
					current.replaceInParent(replacement);
				else
					setRoot(replacement);
				break;
			case 2:
				final BasicNode<T> successor = current.successor();
				current.setValue(successor.getValue());
				delete(successor, value);
				break;
			}
		}
	}
}
