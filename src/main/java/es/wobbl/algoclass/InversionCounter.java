package es.wobbl.algoclass;

import static es.wobbl.algoclass.Util.binarySplit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

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
		final int n = l1.size() + l2.size();
		for (int i = 0; i < n; i++) {
			final T v1 = j < l1.size() ? l1.get(j) : null;
			final T v2 = k < l2.size() ? l2.get(k) : null;
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
		calls++;
		if (list.size() <= 1)
			return result(list, 0L);

		final Result<T> a = count(binarySplit(list, 0));
		final Result<T> b = count(binarySplit(list, 1));
		final Result<T> c = countSplitInversions(a.list, b.list);
		return result(c.list, a.inversions + b.inversions + c.inversions);
	}

	static long calls = 0;

	public static void main(String[] args) throws IOException {
		final List<Integer> numbers = Lists.transform(
				Files.readLines(new File(System.getenv("HOME") + File.separator + "IntegerArray.txt"), Charsets.UTF_8),
				new Function<String, Integer>() {

			@Override
			public Integer apply(String input) {
				return new Integer(input);
			}
		});
		/*
		 * Profile.report("count inversions", TimeUnit.MILLISECONDS, 1, new
		 * Runnable() {
		 * 
		 * @Override public void run() { Result<Integer> inversions =
		 * count(numbers); System.out.println(inversions.inversions);
		 * System.out.println(calls); } });
		 */
	}
}
