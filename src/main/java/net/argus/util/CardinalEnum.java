package net.argus.util;

import java.util.ArrayList;
import java.util.List;

public class CardinalEnum<E> implements CardinalEnumeration<E> {
	
	private List<E> list = new ArrayList<E>();
	
	private int index = 0;
	
	public CardinalEnum(List<E> list) {
		this.list = list;
	}
	
	public CardinalEnum() {}
	
	@Override
	public void add(E e) {
		list.add(e);
	}
	
	@Override
	public void returnToStart() {
		index = 0;
	}

	@Override
	public boolean hasMoreElements() {
		return index<list.size()?true:false;
	}

	@Override
	public E nextElement() {
		return list.get(index++);
	}

	@Override
	public boolean content(E e) {
		return list.contains(e);
	}

	@Override
	public int size() {
		return list.size();
	}
	
}
