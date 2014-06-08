package es.wobbl.algoclass.tree;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

	private enum Position {
		LEFT, RIGHT, THIS, PAST_RIGHT;
	}

	private class DepthFirstSpliterator extends AbstractSpliterator<T> {

		private class State {
			Position pos;
			final BasicNode<T> node;

			public State(BasicNode<T> node) {
				this.pos = Position.LEFT;
				this.node = node;
			}

			@Override
			public String toString() {
				return "State [pos=" + pos + ", node=" + node.getValue() + "]";
			}

		}

		protected DepthFirstSpliterator() {
			super(Long.MAX_VALUE, Spliterator.DISTINCT);
		}

		final ArrayDeque<State> stack = new ArrayDeque<>();

		@Override
		public boolean tryAdvance(Consumer<? super T> action) {
			State state;
			if (stack.isEmpty()) {
				if (getRoot() == null)
					return false;
				state = new State(getRoot());
				stack.push(state);
			} else {
				state = stack.getFirst();
			}
			while (true) {
				switch (state.pos) {
				case LEFT:
					state.pos = Position.RIGHT;
					if (state.node.getLeft() != null) {
						stack.push(new State(state.node.getLeft()));
						state = stack.getFirst();
					}
					break;
				case RIGHT:
					state.pos = Position.THIS;
					if (state.node.getRight() != null) {
						stack.push(new State(state.node.getRight()));
						state = stack.getFirst();
					}
					break;
				case THIS:
					action.accept(state.node.getValue());
					stack.pop();
					return !stack.isEmpty();
				default:
					throw new IllegalStateException(
							"past_right or anything else must not occur within the depth first spliterator");
				}
			}
		}
	}

	private class InOrderSpliterator extends AbstractSpliterator<T> {

		private class State {
			Position pos;
			final BasicNode<T> node;

			public State(BasicNode<T> node) {
				this.pos = Position.LEFT;
				this.node = node;
			}

			@Override
			public String toString() {
				return "State [pos=" + pos + ", node=" + node.getValue() + "]";
			}

		}

		protected InOrderSpliterator() {
			super(Long.MAX_VALUE, Spliterator.DISTINCT | Spliterator.ORDERED | Spliterator.SORTED);
		}

		final ArrayDeque<State> stack = new ArrayDeque<>();

		@Override
		public boolean tryAdvance(Consumer<? super T> action) {
			State state;
			if (stack.isEmpty()) {
				if (getRoot() == null)
					return false;
				state = new State(getRoot());
				stack.push(state);
			} else {
				state = stack.getFirst();
			}
			while (true) {
				switch (state.pos) {
				case LEFT:
					state.pos = Position.THIS;
					if (state.node.getLeft() != null) {
						stack.push(new State(state.node.getLeft()));
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
						stack.push(new State(state.node.getRight()));
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
			return (o1, o2) -> {
				return o1.compareTo(o2);
			};
		}
	}

	@Override
	public Stream<T> stream() {
		return StreamSupport.stream(new InOrderSpliterator(), false);
	}

	public Stream<T> streamDepthFirst() {
		return StreamSupport.stream(new DepthFirstSpliterator(), false);
	}
}
