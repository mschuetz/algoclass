package es.wobbl.algoclass.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;

public class BasicBinaryTreeTest {

	private BasicBinaryTree<Integer> tree;

	private final Set<Integer> values = Sets.newHashSet();

	static void eachValue(Consumer<Integer> c) {
		c.accept(13);
		for (int i = 0, sign = 1; i < 5; i++, sign *= -1) {
			c.accept(8 + i * sign);
			c.accept(20 - i * sign);
		}
	}

	static <T extends Comparable<T>> void assertCanLookup(BasicBinaryTree<T> tree, T value) {
		final BasicNode<T> node = tree.lookup(value);
		assertEquals(value, node.getValue());
	}

	static <T extends Comparable<T>> void assertCanLookup(BasicBinaryTree<T> tree, Iterable<T> values) {
		for (final T value : values)
			assertCanLookup(tree, value);
	}

	@Before
	public void createTree() {
		tree = new BasicBinaryTree<>();
		eachValue((i) -> {
			tree.insert(i);
			values.add(i);
		});
	}

	@Test
	public void testSimpleInsert() {
		final BasicNode<Integer> root = tree.getRoot();
		assertEquals(8, root.getLeft().getValue().intValue());
		assertEquals(20, root.getRight().getValue().intValue());
		assertEquals(10, root.getLeft().getRight().getValue().intValue());
	}

	@Test
	public void testStream() {
		final Set<Object> actualValues = new HashSet<>(Arrays.asList(tree.stream().toArray()));
		assertEquals(values, actualValues);
	}

	@Test
	public void testLookup() throws Exception {
		eachValue((i) -> {
			assertCanLookup(tree, i);
		});
	}

	@Test
	public void testDeleteFullNode() throws Exception {
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
			if (i != 13)
				assertCanLookup(tree, i);
		});
	}

	@Test
	public void testDeleteAll() {
		IntStream.range(0, 32).parallel().forEach((j) -> {
			final BasicBinaryTree<Integer> tree = new BasicBinaryTree<>();
			final Set<Integer> values = Sets.newHashSet();
			for (int i = 0; i < 1024; i++) {
				final int n = RandomUtils.nextInt(0, 65536);
				values.add(n);
				tree.insert(n);
			}
			assertCanLookup(tree, values);
			final HashSet<Integer> values2 = new HashSet<>(values);
			for (final int n : values) {
				values2.remove(n);
				tree.delete(n);
				assertCanLookup(tree, values2);
			}
		});
	}
}
