package net.argus.event.socket;

import java.util.List;

import net.argus.event.Event;

public class EventSocket extends Event<SocketListener> {
	
	public static final int CONNECT = 0;
	public static final int DISCONNECT = 1;
	public static final int ERROR_CONNECTION = 2;

	@Override
	public void event(SocketListener listener, int event, List<Object> objs) {
		switch(event) {
			case CONNECT:
				listener.connect((SocketEvent) objs.get(0));
				break;
				
			case DISCONNECT:
				listener.disconnect((SocketEvent) objs.get(0));
				break;
				
			case ERROR_CONNECTION:
				listener.errorConnection((SocketEvent) objs.get(0));
				break;
		}
	}
}
