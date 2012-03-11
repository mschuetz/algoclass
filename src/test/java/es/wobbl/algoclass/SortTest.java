package es.wobbl.algoclass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class SortTest {

	private static final Logger log = LoggerFactory.getLogger(SortTest.class);

	static List<Long> random(int length) {
		List<Long> arr = Lists.newArrayListWithCapacity(length);
		for (int i = 0; i < length; i++)
			arr.add(RandomUtils.nextLong() % 100L);
		return arr;
	}

	static <T extends Comparable<T>> void assertSorted(List<T> arr) {
		for (int i = 0; i < arr.size() - 1; i++) {
			assertTrue(arr.get(i).compareTo(arr.get(i + 1)) < 1);
		}
	}

	static <T extends Comparable<T>> void assertCanSort(List<T> in) {
		log.info("in :" + in);
		List<T> res = MergeSort.sort(in);
		log.info("out:" + res);
		assertSorted(res);
		assertEquals(in.size(), res.size());
	}

	@Test
	public void testMergeSort() {
		assertSorted(ImmutableList.of(1L, 2L, 3L, 4L));

		assertCanSort(random(10));
		assertCanSort(ImmutableList.of(1L, 2L, 3L, 4L));
		assertCanSort(ImmutableList.of(1L, 4L, 2L, 3L, 4L));
		assertCanSort(ImmutableList.of(1L));
		assertCanSort(new ArrayList<Long>());
	}
}
