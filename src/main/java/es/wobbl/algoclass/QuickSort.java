package es.wobbl.algoclass;

import java.io.File;
import java.io.IOException;
import java.util.AbstractList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import es.wobbl.algoclass.Util.StandardComparator;

public class QuickSort {

	public static class ListView<T> extends AbstractList<T> {

		final List<T> base;
		final int offset, length;

		public ListView(List<T> base, int offset, int length) {
			Preconditions.checkArgument(offset + length <= base.size());
			if (base instanceof ListView) {
				final ListView<T> lvBase = ((ListView<T>) base);
				this.offset = offset + lvBase.offset;
				this.base = lvBase.base;
			} else {
				this.offset = offset;
				this.base = base;
			}
			this.length = length;
		}

		@Override
		public T set(int i, T o) {
			return base.set(offset + i, o);
		}

		@Override
		public T get(int i) {
			return base.get(offset + i);
		}

		@Override
		public int size() {
			return length;
		}
	}

	static <T> void swap(List<T> list, int i1, int i2) {
		final T tmp = list.get(i1);
		list.set(i1, list.get(i2));
		list.set(i2, tmp);
	}

	public static class ListEntry<T> {
		int index;
		final T entry;

		public ListEntry(int index, T entry) {
			super();
			this.index = index;
			this.entry = entry;
		}
	}

	public static <T> void sort(List<T> arr, Comparator<T> cmp, Pivot<T> pivotFunc) {
		cmpCounter += arr.size() - 1;

		if (arr.size() <= 1)
			return;

		final ListEntry<T> pivotEntry = pivotFunc.apply(arr);

		if (pivotEntry.index != 0) {
			swap(arr, pivotEntry.index, 0);
			pivotEntry.index = 0;
		}

		final int i = partition(arr, pivotEntry, cmp);

		swap(arr, 0, i - 1);
		sort(new ListView<T>(arr, 0, i - 1), cmp, pivotFunc);
		sort(new ListView<T>(arr, i, arr.size() - i), cmp, pivotFunc);
	}

	public static <T> int partition(List<T> arr, ListEntry<T> pivotEntry, Comparator<T> cmp) {
		int i = 1, j = 1;
		while (j < arr.size()) {
			final T jth = arr.get(j);
			final int rel = cmp.compare(jth, pivotEntry.entry);
			if (rel < 0) {
				swap(arr, i, j);
				i++;
			}

			j++;
		}
		return i;
	}

	public static <T extends Comparable<T>> void sort(List<T> arr) {
		sort(arr, new StandardComparator<T>(), new RandomPivot<T>());
	}

	public static interface Pivot<T> extends Function<List<T>, ListEntry<T>> {
	}

	public static class PivotOnFirst<T> implements Pivot<T> {

		@Override
		public ListEntry<T> apply(List<T> input) {
			Preconditions.checkArgument(input.size() > 0);
			return new ListEntry<T>(0, input.get(0));
		}
	}

	public static class PivotOnLast<T> implements Pivot<T> {

		@Override
		public ListEntry<T> apply(List<T> input) {
			Preconditions.checkArgument(input.size() > 0);
			return new ListEntry<T>(input.size() - 1, input.get(input.size() - 1));
		}
	}

	public static class PivotOnMedianOfThree<T extends Comparable<T>> implements Pivot<T> {

		@Override
		public ListEntry<T> apply(List<T> input) {
			Preconditions.checkArgument(input.size() > 0);
			@SuppressWarnings("unchecked")
			final List<ListEntry<T>> three = Lists.newArrayList(new ListEntry<T>(0, input.get(0)), new ListEntry<T>(
					input.size() / 2, input.get(input.size() / 2)),
					new ListEntry<T>(input.size() - 1, input.get(input.size() - 1)));
			sort(three, new Comparator<ListEntry<T>>() {

				@Override
				public int compare(ListEntry<T> o1, ListEntry<T> o2) {
					return o1.entry.compareTo(o2.entry);
				}
			}, new PivotOnFirst<ListEntry<T>>());

			return three.get(1);
		}
	}

	public static class RandomPivot<T> implements Pivot<T> {

		@Override
		public ListEntry<T> apply(List<T> input) {
			Preconditions.checkArgument(input.size() > 0);
			final int index = RandomUtils.nextInt(0, input.size());
			return new ListEntry<T>(index, input.get(index));
		}
	}

	static long cmpCounter = 0;

	static List<Long> readNumbers(String fn) throws IOException {
		return Lists.newArrayList(Lists.transform(Files.readLines(new File(fn), Charsets.UTF_8), new Function<String, Long>() {

			@Override
			public Long apply(String input) {
				return new Long(input);
			}
		}));
	}

	static <T extends Comparable<T>> void assertSorted(List<T> arr) {
		for (int i = 0; i < arr.size() - 1; i++) {
			if (!(arr.get(i).compareTo(arr.get(i + 1)) < 1))
				throw new AssertionError("not sorted");
		}
	}

	public static void main(String args[]) throws IOException {
		final String fn = System.getenv("HOME") + File.separator + "QuickSort.txt";
		List<Long> numbers = readNumbers(fn);
		sort(numbers, new Util.StandardComparator<Long>(), new PivotOnFirst<Long>());
		assertSorted(numbers);
		System.out.println(cmpCounter);

		cmpCounter = 0;
		numbers = readNumbers(fn);
		sort(numbers, new Util.StandardComparator<Long>(), new PivotOnLast<Long>());
		assertSorted(numbers);
		System.out.println(cmpCounter);

		cmpCounter = 0;
		numbers = readNumbers(fn);
		sort(numbers, new Util.StandardComparator<Long>(), new PivotOnMedianOfThree<Long>());
		assertSorted(numbers);
		System.out.println(cmpCounter);
	}
}
