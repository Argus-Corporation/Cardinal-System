package net.argus.util;

import java.util.ArrayList;
import java.util.List;

public class ListenerManager<T extends Listener> {
	
	private List<T> listeners = new ArrayList<T>();
	
	public void addListener(T listener) {listeners.add(listener);}
	
	public T getListener(int index) {return listeners.get(index);}
	public List<T> getListeners() {return listeners;}

}
