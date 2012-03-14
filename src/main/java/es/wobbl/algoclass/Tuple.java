package es.wobbl.algoclass;

import java.util.Collection;
import java.util.Iterator;

import com.google.common.base.Preconditions;

public class Tuple<T> {

	public final T e1, e2;

	public Tuple(Collection<T> elements) {
		Preconditions.checkArgument(elements.size() == 2, "need exactly two arguments");
		Iterator<T> it = elements.iterator();
		e1 = it.next();
		e2 = it.next();
	}

	public Tuple(T e1, T e2) {
		super();
		this.e1 = e1;
		this.e2 = e2;
	}
	
}
