package es.wobbl.algoclass.tree;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import es.wobbl.algoclass.tree.RBNode.Colour;

public class RBTreeTest {

	@Test
	public void testGrandparent() throws Exception {
		final RBNode<Integer> root = new RBNode<>(6, null, null, new RBNode<>(3, null, null, new RBNode<>(2, null, null,
				Colour.BLACK), null, Colour.BLACK), null, Colour.BLACK);
		assertEquals(root.getLeft().getLeft().grandparent(), root);
	}

	@Test
	public void testUncle() throws Exception {
		final RBNode<Integer> root = new RBNode<>(6, null, null, new RBNode<>(3, null, null, new RBNode<>(2, null, null,
				Colour.BLACK), null, Colour.BLACK), new RBNode<>(7, null, null, null), Colour.BLACK);
		assertEquals(root.getLeft().getLeft().uncle(), root.getRight());
	}

	@Test
	public void testTreeInsert() throws Exception {
		final RBTree<Integer> t = new RBTree<>();
		t.insert(6);
		t.insert(5);
		t.insert(4);
		t.insert(7);
		t.insert(9);
		t.insert(1);
		t.insert(10);
		t.validate();
	}

	@Test
	public void testTreeInsertRandomNodesLong() throws Exception {
		final RBTree<Integer> t = new RBTree<>();
		for (int i = 0; i < 1024; i++) {
			final int v = RandomUtils.nextInt(0, 1024);
			t.insert(v);
			t.validate();
		}
	}

	@Test
	public void testStream() {
		final RBTree<Integer> tree = new RBTree<>();
		tree.insert(5);
		tree.insert(2);
		tree.insert(1);
		tree.insert(4);
		tree.insert(3);
		tree.insert(9);
		tree.insert(10);
		tree.insert(6);
		tree.insert(7);
		tree.insert(8);
		assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, tree.stream().toArray());
	}
}
