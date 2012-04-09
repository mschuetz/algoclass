package es.wobbl.algoclass;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.Lists;

import es.wobbl.algoclass.QuickSort.ListEntry;
import es.wobbl.algoclass.QuickSort.PivotOnFirst;

public class SelectTest {

	@Test
	public void testSelect() {
		ListEntry<Integer> entry = RSelect.select(Lists.newArrayList(1,2,3,4,5), 2,
				new Util.StandardComparator<Integer>(), new PivotOnFirst<Integer>());
		assertEquals(Integer.valueOf(3), entry.entry);
		entry = RSelect.select(Lists.newArrayList(1,2,3,4,5), 4,
				new Util.StandardComparator<Integer>(), new PivotOnFirst<Integer>());
		assertEquals(Integer.valueOf(5), entry.entry);
		entry = RSelect.select(Lists.newArrayList(3,5,1,4,2), 4,
				new Util.StandardComparator<Integer>(), new PivotOnFirst<Integer>());
		assertEquals(Integer.valueOf(5), entry.entry);
	}
}
