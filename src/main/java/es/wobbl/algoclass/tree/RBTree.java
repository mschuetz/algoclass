package es.wobbl.algoclass.tree;

import java.util.stream.Stream;

import org.apache.commons.lang3.NotImplementedException;

import com.google.common.base.Preconditions;

import es.wobbl.algoclass.tree.RBNode.Colour;

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
		if (root == null)
			return root = new RBNode<>(value, null, Colour.BLACK);

		final RBNode<T> n = root.insert(value);
		if (n.getParent().is(Colour.BLACK))
			return n;

		throw new NotImplementedException("not all cases implemented yet");
	}

	/**
	 * @return the number of black children
	 * @throws IllegalStateException
	 *             if rule 2, 4 or 5 are violated (root node must be black,
	 *             number of black children in path equal, red nodes have black
	 *             children only )
	 */
	public int validate() {
		Preconditions.checkState(root == null || root.is(Colour.BLACK), "the root must be black or be empty");
		return root.validate();
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
