package es.wobbl.algoclass;

import static es.wobbl.algoclass.Util.nextOrNull;
import static es.wobbl.algoclass.Util.binarySplit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class MergeSort {

	private static <T> List<T> merge(List<T> l1, List<T> l2, Comparator<T> cmp) {
		final ArrayList<T> out = Lists.newArrayListWithCapacity(l1.size() + l2.size());
		final Iterator<T> it1 = l1.iterator();
		final Iterator<T> it2 = l2.iterator();
		T val1 = it1.next();
		T val2 = it2.next();
		do {
			if (val2 == null || (val1 != null && cmp.compare(val1, val2) < 0)) {
				out.add(val1);
				val1 = nextOrNull(it1);
			} else {
				out.add(val2);
				val2 = nextOrNull(it2);
			}
		} while (val1 != null || val2 != null);
		return out;
	}

	public static <T> List<T> sort(List<T> input, Comparator<T> cmp) {
		if (input.size() <= 1) {
			return input;
		}

		return merge(sort(binarySplit(input, 0), cmp), sort(binarySplit(input, 1), cmp), cmp);
	}

	public static <T extends Comparable<T>> List<T> sort(List<T> input) {
		return sort(input, new StandardComparator<T>());
	}
	
	private static class StandardComparator<T extends Comparable<T>> implements Comparator<T> {
		@Override
		public int compare(T o1, T o2) {
			return o1.compareTo(o2);
		}
	}
}
