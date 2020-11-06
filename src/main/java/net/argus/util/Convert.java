package net.argus.util;

import java.util.List;

public class Convert<E, T> {
	
	@SuppressWarnings("unchecked")
	public T[] toArray(List<E> list) {
		Object[] o = new Object[list.size()];
		for(int i = 0; i < list.size(); i++)
			o[i] = list.get(i);
		return (T[]) o;
	}
	
}
