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
		// ensure the spliterator can be split at least once
		insert(32768);
		insert(16384);
		insert(49152);
		IntStream.range(0, 1024 - 3).forEach((i) -> {
			final int val = RandomUtils.nextInt(0, 65536);
			insert(val);
		});
		assertEquals(expected, Sets.newHashSet(tree));
	}

	private void insert(final int val) {
		tree.insert(val);
		expected.add(val);
	}

	@Test
	public void testDepthFirst() throws Exception {
		final Spliterator<Integer> s1 = TreeSpliterators.depthFirst(tree.getRoot());

		final Set<Integer> actual = Sets.newHashSet();

		s1.forEachRemaining((i) -> {
			assertFalse("must not yet contain " + i, actual.contains(i));
			actual.add(i);
		});
		assertEquals(expected, actual);
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
		assertEquals(expected, actual);
	}

	@Test
	public void testParallelDepthFirst() throws Exception {
		final Spliterator<Integer> s1 = TreeSpliterators.depthFirst(tree.getRoot());
		// use a map instead of e.g. ConcurrentSkipListSet because the latter
		// doesn't have an atomic operation equivalent to ConcurrentHashMap's
		// putIfAbsent
		final ConcurrentMap<Integer, Boolean> actual = new ConcurrentHashMap<>(1024);

		StreamSupport.stream(s1, true).parallel().forEach((i) -> {
			final Boolean prev = actual.putIfAbsent(i, true);
			assertNull(i + " was already present", prev);
		});

		assertEquals(expected, actual.keySet());
	}
}
