package es.wobbl.algoclass.tree;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

public interface BinaryTree<N extends Node<N, T>, T extends Comparable<T>> extends Iterable<T> {

	@Nullable
	public N getRoot();

	public N insert(T value);

	public N lookup(T value);

	public void delete(T value);

	public default Stream<T> stream() {
		return StreamSupport.stream(spliterator(), false);
	}

	@Override
	public default Spliterator<T> spliterator() {
		return TreeSpliterators.inOrder(getRoot());
	}

	@Override
	public default Iterator<T> iterator() {
		return Spliterators.iterator(spliterator());
	}
}
