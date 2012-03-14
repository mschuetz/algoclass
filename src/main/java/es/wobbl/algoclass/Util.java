package es.wobbl.algoclass;

import java.util.Iterator;
import java.util.List;

public class Util {
	public static <T> List<T> split(List<T> in, int index) {
		int partitionSize = in.size() / 2;
		int from = partitionSize * index;
		int to = index == 0 ? partitionSize : in.size();
		return in.subList(from, to);
	}

	public static <T> T nextOrNull(final Iterator<T> it2) {
		return it2.hasNext() ? it2.next() : null;
	}
}
