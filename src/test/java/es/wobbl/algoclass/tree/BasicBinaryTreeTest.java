package es.wobbl.algoclass.tree;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BasicBinaryTreeTest {

	@Test
	public void testSimpleInsert() {
		final BasicBinaryTree<String> tree = new BasicBinaryTree<>();
		final BasicNode<String> root = tree.insert("M");
		tree.insert("Z");
		tree.insert("A");
		tree.insert("B");
		assertEquals("A", root.getLeft().getValue());
		assertEquals("Z", root.getRight().getValue());
		assertEquals("B", root.getLeft().getRight().getValue());
	}

}
