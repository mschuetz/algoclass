package es.wobbl.algoclass.tree;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import es.wobbl.algoclass.tree.RBNode.Colour;

public class RBTreeTest {

	@Test
	public void testGrandparent() throws Exception {
		final RBNode<Integer> root = new RBNode<>(6, null, new RBNode<>(3, null, new RBNode<>(2, null, Colour.BLACK), null,
				Colour.BLACK), null, Colour.BLACK);
		assertEquals(root.getLeft().getLeft().grandparent(), root);
	}

	@Test
	public void testUncle() throws Exception {
		final RBNode<Integer> root = new RBNode<>(6, null, new RBNode<>(3, null, new RBNode<>(2, null, Colour.BLACK), null,
				Colour.BLACK), new RBNode<>(7, null, null), Colour.BLACK);
		assertEquals(root.getLeft().getLeft().uncle(), root.getRight());
	}

	@Test
	public void testInsert() throws Exception {
		final RBNode<Integer> root = new RBNode<>(6, null, null);
		root.insert(5);
		root.insert(4);
		root.insert(7);
		assertEquals(root.getLeft().getLeft().uncle(), root.getRight());
	}

}
