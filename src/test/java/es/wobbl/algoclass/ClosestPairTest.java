package es.wobbl.algoclass;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

import es.wobbl.algoclass.Point.Pair;

public class ClosestPairTest {

	@Test
	public void testInifitePair() {
		assertEquals(Double.POSITIVE_INFINITY, Point.Pair.withInfiniteDistance.distance, 0.0);
	}

	@Test
	public void testWithNonSplitClosestPairs() {
		ImmutableList<Point> points = ImmutableList.of(new Point(1, 1), new Point(1, 2), new Point(8, 10), new Point(20, 20));
		Point.closestPair(points);
	}

	@Test
	public void testWithSplitClosestPair() {
		ImmutableList<Point> points = ImmutableList.of(new Point(100, 100), new Point(1, 2), new Point(1, 1), new Point(20, 20));
		Pair expectedClosestPair = Point.Pair.of(points.get(1), points.get(2));
		assertEquals(expectedClosestPair, Point.closestPair(points));
	}

}
