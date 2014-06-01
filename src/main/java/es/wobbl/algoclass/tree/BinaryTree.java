package es.wobbl.algoclass.tree;

import java.util.function.Consumer;
import java.util.stream.Stream;

import com.sun.istack.internal.Nullable;

public interface BinaryTree<N extends Node<N, T>, T extends Comparable<T>> {

	@Nullable
	public N getRoot();

	public N insert(T value);

	public N lookup(T value);

	public void delete(T value);

	void each(Consumer<T> consumer);

	Stream<T> stream();
}
