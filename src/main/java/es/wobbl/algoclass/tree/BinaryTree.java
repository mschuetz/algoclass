package es.wobbl.algoclass.tree;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.sun.istack.internal.Nullable;

public interface BinaryTree<N extends Node<N, T>, T extends Comparable<T>> {

	@Nullable
	public N getRoot();

	public N insert(T value);

	public N lookup(T value);

	public void delete(T value);

	public default Stream<T> stream() {
		return StreamSupport.stream(TreeSpliterators.inOrder(getRoot()), false);
	}
}
