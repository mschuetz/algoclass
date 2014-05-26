package es.wobbl.algoclass.tree;

public class BasicBinaryTree<T extends Comparable<T>> implements BinaryTree<BasicNode<T>, T> {

	private BasicNode<T> root;

	@Override
	public BasicNode<T> getRoot() {
		return root;
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
		return null;
	}

	@Override
	public void delete(BasicNode<T> node) {
	}
}
