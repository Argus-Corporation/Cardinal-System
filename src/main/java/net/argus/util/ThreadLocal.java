package net.argus.util;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocal <V> {
	
	private Map<Thread, V> map = new HashMap<Thread, V>();
	
	public void set(V value) {set(Thread.currentThread(), value);}
	public void set(Thread thread, V value) {map.put(thread, value);}
	
	public V get() {return get(Thread.currentThread());}
	public V get(Thread thread) {return map.get(thread);}

}
