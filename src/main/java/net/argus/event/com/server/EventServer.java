package net.argus.event.com.server;

import net.argus.event.Event;

public class EventServer extends Event<ServerListener> {
	
	public static final int NEW_CLIENT = 0;

	@Override
	public void event(ServerListener listener, int event, Object... objs) {
		switch(event) {
			case NEW_CLIENT:
				listener.newClient((ServerEvent) objs[0]);
				break;
		}
	}

}
