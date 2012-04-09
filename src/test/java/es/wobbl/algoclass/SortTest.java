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

import es.wobbl.algoclass.QuickSort.ListView;

public class SortTest {

	private static final Logger log = LoggerFactory.getLogger(SortTest.class);

	static List<Long> random(int length) {
		List<Long> arr = Lists.newArrayListWithCapacity(length);
		for (int i = 0; i < length; i++)
			arr.add(RandomUtils.nextLong() % 10L);
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

	@Test
	public void testQuickSort() {
		for (int i=0;i<10;i++) {
			List<Long> list = random(10000);
			QuickSort.sort(list, new Util.StandardComparator<Long>(), new QuickSort.PivotOnLast<Long>());
			assertSorted(list);
		}
		List<Long> list = random(11);
		QuickSort.sort(list, new Util.StandardComparator<Long>(), new QuickSort.PivotOnLast<Long>());
		assertSorted(list);
	}

	@Test
	public void testListView() {
		ArrayList<Integer> list = Lists.newArrayList(ImmutableList.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		ListView<Integer> l1 = new QuickSort.ListView<Integer>(list, 7, 3);
		System.out.println(l1);
		assertEquals(Integer.valueOf(7), l1.get(0));
	}
}
