package es.wobbl.algoclass.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;

public class BasicBinaryTreeTest {

	private BasicBinaryTree<Integer> tree;
	private BasicNode<Integer> root;

	static void eachValue(Consumer<Integer> c) {
		for (int i = 0, sign = 1; i < 5; i++, sign *= -1) {
			c.accept(8 + i * sign);
			c.accept(20 - i * sign);
		}
	}

	static <T extends Comparable<T>> void assertCanLookup(BasicBinaryTree<T> tree, T value) {
		final BasicNode<T> node = tree.lookup(value);
		assertEquals(value, node.getValue());
	}

	@Before
	public void createTree() {
		tree = new BasicBinaryTree<>();
		root = tree.insert(13);
		eachValue((i) -> {
			tree.insert(i);
		});
	}

	@Test
	public void testSimpleInsert() {
		assertEquals(8, root.getLeft().getValue().intValue());
		assertEquals(20, root.getRight().getValue().intValue());
		assertEquals(10, root.getLeft().getRight().getValue().intValue());
	}

	@Test
	public void testLookup() throws Exception {
		eachValue((i) -> {
			assertCanLookup(tree, i);
		});
	}

	@Test
	public void testDelete() throws Exception {
		tree.delete(8);
		assertNull(tree.lookup(8));
		eachValue((i) -> {
			if (i != 8)
				assertCanLookup(tree, i);
		});
	}

	@Test
	public void testDeleteLeaf() throws Exception {
		tree.delete(12);
		assertNull(tree.lookup(12));
		eachValue((i) -> {
			if (i != 12)
				assertCanLookup(tree, i);
		});
	}

	@Test
	public void testDeleteHalfNode() throws Exception {
		tree.delete(7);
		assertNull(tree.lookup(7));
		eachValue((i) -> {
			if (i != 7)
				assertCanLookup(tree, i);
		});
	}

	@Test
	public void testDeleteRoot() throws Exception {
		tree.delete(13);
		assertNull(tree.lookup(13));
		eachValue((i) -> {
			if (i != 10)
				assertCanLookup(tree, i);
		});
	}
}
