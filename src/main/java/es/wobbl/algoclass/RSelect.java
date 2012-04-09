package es.wobbl.algoclass;

import static es.wobbl.algoclass.QuickSort.partition;
import static es.wobbl.algoclass.QuickSort.swap;

import java.util.Comparator;
import java.util.List;

import es.wobbl.algoclass.QuickSort.ListEntry;
import es.wobbl.algoclass.QuickSort.ListView;
import es.wobbl.algoclass.QuickSort.Pivot;

public class RSelect {

	public static<T> ListEntry<T> select(List<T> arr, int k, Comparator<T> cmp, Pivot<T> pivot) {
		if (arr.size() < k)
			return null;
		
		ListEntry<T> pivotEntry = pivot.apply(arr);
		if (pivotEntry.index != 0) {
			swap(arr, pivotEntry.index, 0);
			pivotEntry.index = 0;
		}

		int i = partition(arr, pivotEntry, cmp);
		
		if (i == k) 
			return new ListEntry<T>(i, arr.get(i));
		
		if (i > k)
			return select(new ListView<T>(arr, 0, i - 1), k, cmp, pivot);
		
		//if (i < k)
		ListEntry<T> e2 = select(new ListView<T>(arr, i, arr.size() - i), k - i, cmp, pivot);
		e2.index += i;
		return e2;
	}
	
}
