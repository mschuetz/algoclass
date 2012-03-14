package es.wobbl.algoclass;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class InversionsTest {

	@Test
	public void testInversion() {
		System.err.println(InversionCounter.count(ImmutableList.of(1,5,2,3)).list);
		assertEquals(2, InversionCounter.count(ImmutableList.of(1,5,2,3)).inversions);
		assertEquals(0, InversionCounter.count(ImmutableList.of(1,2,3,5)).inversions);
		assertEquals(15, InversionCounter.count(ImmutableList.of(6,5,4,3,2,1)).inversions);
	}
}
