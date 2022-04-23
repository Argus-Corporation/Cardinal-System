package net.argus.event.net.web;

import net.argus.event.Event;

public class EventWeb extends Event<WebListener> {
	
	public static final int ACCEPT = 0;

	@Override
	public void event(WebListener listener, int event, Object... objs) {
		switch(event) {
			case ACCEPT:
				listener.accept((WebEvent) objs[0]);
				break;
		}
	}

}
