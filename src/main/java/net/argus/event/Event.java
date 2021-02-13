package net.argus.event;

import java.util.List;

import net.argus.util.ArrayManager;
import net.argus.util.Listener;
import net.argus.util.ListenerManager;

public abstract class Event<T extends Listener> {
	
	private ListenerManager<T> listenerManager = new ListenerManager<T>();
	
	public void addListener(T listener) {listenerManager.addListener(listener);}
	
	public void startEvent(int event, Object obj) {
		startEvent(event, new Object[] {obj});
	}
	
	public void startEvent(int event, Object[] objs) {
		startEvent(event, new ArrayManager<Object>().convert(objs));
	}
	
	public void startEvent(int event, List<Object> objs) {
		for(T lis : listenerManager)
			event(lis, event, objs);
	}
	
	public abstract void event(T listener, int event, List<Object> objs);
	
}
