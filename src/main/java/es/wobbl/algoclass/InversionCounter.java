package es.wobbl.algoclass;

import static es.wobbl.algoclass.Util.split;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

public class InversionCounter {

	public static class Result<T extends Comparable<T>> {
		public final List<T> list;
		public final long inversions;

		public Result(List<T> list, long inversions) {
			super();
			this.list = list;
			this.inversions = inversions;
		}
	}

	private static <T extends Comparable<T>> Result<T> result(List<T> list, long inversions) {
		return new Result<T>(list, inversions);
	}

	private static <T extends Comparable<T>> Result<T> countSplitInversions(List<T> l1, List<T> l2) {
		long inversions = 0;
		final ArrayList<T> out = Lists.newArrayListWithCapacity(l1.size() + l2.size());

		int j = 0, k = 0;
		int n = l1.size() + l2.size();
		for (int i = 0; i < n; i++) {
			T v1 = j < l1.size() ? l1.get(j) : null;
			T v2 = k < l2.size() ? l2.get(k) : null;
			if (v1 != null && (v2 == null || v1.compareTo(v2) < 0)) {
				out.add(v1);
				j++;
			} else if (v2 != null && (v1 == null || v2.compareTo(v1) < 0)) {
				out.add(v2);
				k++;
				inversions += l1.size() - j;
			}
		}
		return result(out, inversions);
	}

	public static <T extends Comparable<T>> Result<T> count(List<T> list) {
		if (list.size() <= 1)
			return result(list, 0L);

		Result<T> a = count(split(list, 0));
		Result<T> b = count(split(list, 1));
		Result<T> c = countSplitInversions(a.list, b.list);
		return result(c.list, a.inversions + b.inversions + c.inversions);
	}
}
