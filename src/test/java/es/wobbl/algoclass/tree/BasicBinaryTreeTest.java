package es.wobbl.algoclass.tree;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class BasicBinaryTreeTest {

	private BasicBinaryTree<String> tree;
	private BasicNode<String> root;

	@Before
	public void createTree() {
		tree = new BasicBinaryTree<>();
		root = tree.insert("M");
		tree.insert("Z");
		tree.insert("A");
		tree.insert("B");

	}

	@Test
	public void testSimpleInsert() {
		assertEquals("A", root.getLeft().getValue());
		assertEquals("Z", root.getRight().getValue());
		assertEquals("B", root.getLeft().getRight().getValue());
	}

	@Test
	public void testLookup() throws Exception {
		final BasicNode<String> node = tree.lookup("B");
		assertEquals("B", node.getValue());
	}
}
