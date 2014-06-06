package es.wobbl.algoclass.tree;

import java.awt.Color;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import es.wobbl.algoclass.tree.RBNode.Colour;

public class TreeUi<N extends Node<N, T>, T extends Comparable<T>> {

	enum Side {
		LEFT, RIGHT;
	}

	static {
		// System.setProperty("gs.ui.layout",
		// HierarchicalLayout.class.getCanonicalName());
	}

	private final Graph g;

	public TreeUi(Node<N, T> n) {
		g = new SingleGraph("tree starting at " + n.getValue());
		addNode(null, n, null);
	}

	public static <N extends Node<N, T>, T extends Comparable<T>> TreeUi<N, T> create(Node<N, T> n) {
		return new TreeUi<N, T>(n);
	}

	public void show() {
		g.display();
	}

	String id(Node<N, T> parent, Node<N, T> n, Side side) {
		if (n != null)
			return n.getValue().toString();

		return "null_" + side + "_" + parent.getValue().toString();
	}

	private org.graphstream.graph.Node addNode(Node<N, T> parent, Node<N, T> n, Side side) {
		final String id = id(parent, n, side);
		final org.graphstream.graph.Node gn = g.addNode(id);
		if (n != null) {
			setLabel(gn, n.getValue().toString());
			final org.graphstream.graph.Node left = addNode(n, n.getLeft(), Side.LEFT);
			final org.graphstream.graph.Node right = addNode(n, n.getRight(), Side.RIGHT);
			g.addEdge(id + "_left", gn, left, true);
			g.addEdge(id + "_right", gn, right, true);
		} else {
			setLabel(gn, "null");
		}
		setColor(gn, n);
		return gn;
	}

	private void setLabel(org.graphstream.graph.Node gn, String label) {
		gn.setAttribute("ui.label", label);
	}

	private void setColor(org.graphstream.graph.Node gn, Node<N, T> n) {
		if (n instanceof RBNode<?>) {
			final RBNode<?> rbnode = (RBNode<?>) n;
			gn.setAttribute("ui.color", rbnode == null || rbnode.is(Colour.BLACK) ? Color.BLACK : Color.RED);
		}
	}

	public static void main(String a[]) {
		final RBTree<Integer> t = new RBTree<>();
		t.insert(6);
		t.insert(5);
		t.insert(4);
		t.insert(7);
		create(t.getRoot()).show();
	}
}
