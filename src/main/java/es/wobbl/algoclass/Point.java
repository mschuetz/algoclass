package es.wobbl.algoclass;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.primitives.Doubles;

public class Point {

	final double x, y;

	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	public static class Pair extends Tuple<Point> implements Comparable<Pair> {

		@Override
		public int hashCode() {
			// makes hash code position independent
			return Objects.hashCode(e1.x > e2.x ? e1 : e2);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pair other = (Pair) obj;
			return (Objects.equal(this.e1, other.e1) && Objects.equal(this.e2, other.e2))
					&& (Objects.equal(this.e1, other.e2) && Objects.equal(this.e2, other.e1));
		}

		public Pair(Collection<Point> elements) {
			super(elements);
			distance = Math.sqrt(Math.pow(e1.x - e2.x, 2) + Math.pow(e1.y - e2.y, 2));
		}

		public final double distance;

		public Pair(Point e1, Point e2) {
			super(e1, e2);
			distance = Math.sqrt(Math.pow(e1.x - e2.x, 2) + Math.pow(e1.y - e2.y, 2));
		}

		public static final Pair withInfiniteDistance = new Pair(new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY),
				new Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));

		public static Pair of(Point p1, Point p2) {
			return new Pair(p1, p2);
		}

		public static Pair of(List<Point> points) {
			return new Pair(points);
		}

		@Override
		public int compareTo(Pair other) {
			return Double.compare(distance, other.distance);
		}

		public static Pair maxDistance(Pair... pairs) {
			Preconditions.checkArgument(pairs.length > 0, "must pass at least one pair of points");

			Pair maxPair = pairs[0];
			double max = maxPair.distance;
			for (Pair p : pairs)
				if (p.distance > max)
					maxPair = p;
			return maxPair;
		}
	}

	public static Tuple<Point> closestPair(List<Point> points) {
		Preconditions.checkNotNull(points);
		Preconditions.checkArgument(points.size() > 1, "need at least two points");
		List<Point> pointsx = MergeSort.sort(points, new Comparator<Point>() {
			@Override
			public int compare(Point p0, Point p1) {
				return Double.compare(p0.x, p1.x);
			}
		});
		List<Point> pointsy = MergeSort.sort(points, new Comparator<Point>() {
			@Override
			public int compare(Point p0, Point p1) {
				return Double.compare(p0.y, p1.y);
			}
		});
		return closestPair(pointsx, pointsy);
	}

	private static Pair closestPair(List<Point> pointsx, List<Point> pointsy) {
		Preconditions.checkArgument(pointsx.size() == pointsy.size(), "px and py must be equal in size");

		if (pointsx.size() < 2)
			return Pair.withInfiniteDistance;

		if (pointsx.size() == 2)
			return Pair.of(pointsx);

		// left half
		final List<Point> qx = Util.binarySplit(pointsx, 0);
		final Point rightMost = qx.get(qx.size() - 1);
		final List<Point> qy = ImmutableList.copyOf(Iterables.filter(pointsy, new Predicate<Point>() {
			@Override
			public boolean apply(Point p) {
				return p.x <= rightMost.x;
			}
		}));
		Pair leftPair = closestPair(qx, qy);

		// right half
		final List<Point> rx = Util.binarySplit(pointsx, 1);
		final Point leftMost = rx.get(0);
		final List<Point> ry = ImmutableList.copyOf(Iterables.filter(pointsy, new Predicate<Point>() {
			@Override
			public boolean apply(Point p) {
				return p.x >= leftMost.x;
			}
		}));
		Pair rightPair = closestPair(rx, ry);
		
		double delta = Doubles.min(leftPair.distance, rightPair.distance);

		Pair splitPair = closestSplitPair(pointsx, pointsy, delta);

		return Pair.maxDistance(leftPair, rightPair, splitPair);
	}

	private static Pair closestSplitPair(List<Point> pointsx, List<Point> pointsy, double delta) {
		return Pair.withInfiniteDistance;
	}

}
