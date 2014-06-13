package es.wobbl.algoclass.tree;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;

public class TreeSpliterators {

	public static <N extends Node<N, T>, T extends Comparable<T>> Spliterator<T> inOrder(N root) {
		if (root == null)
			return Spliterators.emptySpliterator();
		return new TreeSpliterators.InOrderSpliterator<>(root);
	}

	public static <N extends Node<N, T>, T extends Comparable<T>> Spliterator<T> depthFirst(N root) {
		if (root == null)
			return Spliterators.emptySpliterator();
		return new TreeSpliterators.DepthFirstSpliterator<>(root);
	}

	private enum Position {
		LEFT, RIGHT, THIS, PAST_RIGHT;
	}

	private static class State<N extends Node<N, T>, T extends Comparable<T>> {
		Position pos;
		final N node;

		public State(N node) {
			this.pos = Position.LEFT;
			this.node = node;
		}

		@Override
		public String toString() {
			return "State [pos=" + pos + ", node=" + node.getValue() + "]";
		}

	}

	static class DepthFirstSpliterator<N extends Node<N, T>, T extends Comparable<T>> extends AbstractSpliterator<T> {

		private N root;
		private final ArrayDeque<N> stack = new ArrayDeque<>();
		private final ArrayDeque<T> skippedRoots = new ArrayDeque<>();

		protected DepthFirstSpliterator(N root) {
			super(Long.MAX_VALUE, Spliterator.DISTINCT);
			initialize(root);
		}

		private void initialize(N root) {
			this.root = root;
			stack.clear();
			if (root != null)
				stack.push(root);
		}

		@Override
		public boolean tryAdvance(Consumer<? super T> action) {
			if (!skippedRoots.isEmpty()) {
				action.accept(skippedRoots.pop());
				return true;
			}
			if (stack.isEmpty()) {
				return false;
			}
			final N node = stack.pop();
			if (node.getLeft() != null) {
				stack.push(node.getLeft());
			}
			if (node.getRight() != null) {
				stack.push(node.getRight());
			}
			action.accept(node.getValue());
			return !stack.isEmpty();
		}

		@Override
		public Spliterator<T> trySplit() {
			// if initial state and splittable
			if (stack.size() == 1 && stack.getFirst() == root && root.childCount() == 2) {
				final N oldRoot = root;
				initialize(oldRoot.getLeft());
				skippedRoots.push(oldRoot.getValue());
				return depthFirst(oldRoot.getRight());
			}
			return null;
		}
	}

	static class InOrderSpliterator<N extends Node<N, T>, T extends Comparable<T>> extends AbstractSpliterator<T> {

		private final N root;

		protected InOrderSpliterator(N root) {
			super(Long.MAX_VALUE, Spliterator.DISTINCT | Spliterator.ORDERED | Spliterator.SORTED);
			this.root = root;
		}

		final ArrayDeque<State<N, T>> stack = new ArrayDeque<>();

		@Override
		public boolean tryAdvance(Consumer<? super T> action) {
			State<N, T> state;
			if (stack.isEmpty()) {
				if (root == null)
					return false;
				state = new State<>(root);
				stack.push(state);
			} else {
				state = stack.getFirst();
			}
			while (true) {
				switch (state.pos) {
				case LEFT:
					state.pos = Position.THIS;
					if (state.node.getLeft() != null) {
						stack.push(new State<N, T>(state.node.getLeft()));
						state = stack.getFirst();
					}
					break;
				case THIS:
					state.pos = Position.RIGHT;
					action.accept(state.node.getValue());
					return true;
				case RIGHT:
					state.pos = Position.PAST_RIGHT;
					if (state.node.getRight() != null) {
						stack.push(new State<N, T>(state.node.getRight()));
						state = stack.getFirst();
						break;
					}
				case PAST_RIGHT:
					stack.pop();
					if (!stack.isEmpty()) {
						state = stack.getFirst();
					} else {
						return false;
					}
				}
			}
		}

		@Override
		public Comparator<? super T> getComparator() {
			return null;
		}
	}
}
