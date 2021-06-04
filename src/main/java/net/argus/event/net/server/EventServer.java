package net.argus.event.net.server;

import net.argus.event.Event;

public class EventServer extends Event<ServerListener> {
	
	public static final int OPEN = 0;
	public static final int ERROR = 1;
	public static final int STOP = 2;
	

	@Override
	public void event(ServerListener listener, int event, Object... objs) {
		switch(event) {
			case OPEN:
				listener.open((ServerEvent) objs[0]);
				break;
				
			case ERROR:
				listener.error((ServerEvent) objs[0]);
				break;
				
			case STOP:
				listener.stop((ServerEvent) objs[0]);
				break;
		}
	}

}
