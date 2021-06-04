package net.argus.event.net.socket;

import net.argus.event.Event;

public class EventSocket extends Event<SocketListener> {
	
	public static final int CONNECT = 0;
	public static final int DISCONNECT = 1;
	public static final int CONNECTION_REFUSED = 2;

	@Override
	public void event(SocketListener listener, int event, Object ... objs) {
		switch(event) {
			case CONNECT:
				listener.connect((SocketEvent) objs[0]);
				break;
				
			case DISCONNECT:
				listener.disconnect((SocketEvent) objs[0]);	
				break;
				
			case CONNECTION_REFUSED:
				listener.connectionRefused((SocketEvent) objs[0]);
				break;
		}
	}
}
