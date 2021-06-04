package net.argus.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListenerManager<T extends Listener> implements Iterable<T> {
	
	private List<T> listeners = new ArrayList<T>();
	
	public void addListener(T listener) {listeners.add(listener);}
	public void removeListener(T listener) {listeners.remove(listener);}
	
	public T getListener(int index) {return listeners.get(index);}
	public List<T> getListeners() {return listeners;}

	@Override
	public Iterator<T> iterator() {return new Itr();}
	
	private class Itr implements Iterator<T> {
		
		private int index = 0;
		
		@Override
		public boolean hasNext() {
			boolean next = listeners.size() >= index + 1;
			if(!next)
				index = 0;
			
			return next;
		}

		@Override
		public T next() {
			T t = listeners.get(index);
			index++;
			return t;
			
		}
	}


}
