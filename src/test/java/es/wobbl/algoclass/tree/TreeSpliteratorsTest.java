package es.wobbl.algoclass.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;

public class TreeSpliteratorsTest {

	private BasicBinaryTree<Integer> tree;
	private Set<Integer> expected;

	@Before
	public void createTree() {
		tree = new BasicBinaryTree<>();
		expected = new HashSet<>();
		IntStream.range(0, 16).forEach((i) -> {
			final int val = RandomUtils.nextInt(0, 65536);
			tree.insert(val);
			expected.add(val);
		});
		assertEquals(expected, toSet(tree));
	}

	static <T> Set<T> toSet(BasicBinaryTree<? extends T> tree) {
		return Sets.newHashSet(tree);
	}

	@Test
	public void testSplitDepthFirst() throws Exception {
		final Spliterator<Integer> s1 = TreeSpliterators.depthFirst(tree.getRoot());
		final Spliterator<Integer> s2 = s1.trySplit();

		final Set<Integer> actual = Sets.newHashSet();

		s1.forEachRemaining((i) -> {
			assertFalse("must not yet contain " + i, actual.contains(i));
			actual.add(i);
		});

		s2.forEachRemaining((i) -> {
			assertFalse("must not yet contain " + i, actual.contains(i));
			actual.add(i);
		});
		System.err.println(actual.size());
	}

	@Test
	public void testParallelDepthFirst() throws Exception {
		final Spliterator<Integer> s1 = TreeSpliterators.depthFirst(tree.getRoot());
		final ConcurrentMap<Integer, String> actual = new ConcurrentHashMap<>(1204);

		StreamSupport.stream(s1, true).forEach((i) -> {
			final String prev = actual.putIfAbsent(i, "present");
			assertNull(i + " was already present", prev);
		});

		assertEquals(expected, actual.keySet());
	}
}
