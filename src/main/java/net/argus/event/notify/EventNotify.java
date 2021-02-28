package net.argus.event.notify;

import java.util.List;

import net.argus.event.Event;

public class EventNotify extends Event<NotifyListener> {
	
	public static final int SHOW = 0;
	public static final int HIDE = 1;

	@Override
	public void event(NotifyListener listener, int event, List<Object> objs) {
		switch(event) {
			case SHOW:
				listener.show((NotifyEvent) objs.get(0));
				break;
	
			case HIDE:
				listener.hide((NotifyEvent) objs.get(0));
				break;
		}
	}

}
