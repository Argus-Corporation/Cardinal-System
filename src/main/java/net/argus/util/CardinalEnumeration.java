package net.argus.util;

import java.util.Enumeration;

public interface CardinalEnumeration<E> extends Enumeration<E> {
	
	public void returnToStart();
	
	public boolean content(E e);
	
	public void add(E e);
	
}
