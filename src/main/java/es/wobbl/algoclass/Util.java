package es.wobbl.algoclass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class Util {
	static <T> List<T> split(List<T> in, int index) {
		int partitionSize = in.size() / 2;
		int from = partitionSize * index;
		int to = index == 0 ? partitionSize : in.size();
		return in.subList(from, to);
	}

	public static <T extends Comparable<T>> List<T> merge(List<T> l1, List<T> l2) {
		final ArrayList<T> out = Lists.newArrayListWithCapacity(l1.size() + l2.size());
		final Iterator<T> it1 = l1.iterator();
		final Iterator<T> it2 = l2.iterator();
		T val1 = it1.next();
		T val2 = it2.next();
		do {
			if (val2 == null || (val1 != null && val1.compareTo(val2) < 0)) {
				out.add(val1);
				val1 = nextOrNull(it1);
			} else {
				out.add(val2);
				val2 = nextOrNull(it2);
			}
		} while (val1 != null || val2 != null);
		return out;
	}

	private static <T> T nextOrNull(final Iterator<T> it2) {
		return it2.hasNext() ? it2.next() : null;
	}
}
