package es.wobbl.algoclass.tree;

import java.util.stream.Stream;

public class RBTree<T extends Comparable<T>> implements BinaryTree<RBNode<T>, T> {

	RBNode<T> root;

	@Override
	public RBNode<T> getRoot() {
		return root;
	}

	@Override
	public RBNode<T> lookup(T value) {
		if (root == null)
			return null;
		return root.lookup(value);
	}

	@Override
	public RBNode<T> insert(T value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(T value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Stream<T> stream() {
		// TODO Auto-generated method stub
		return null;
	}

}
