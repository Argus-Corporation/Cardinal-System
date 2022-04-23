package net.argus.event.net.server;

import net.argus.event.Event;

public class EventServer extends Event<ServerListener> {
	
	public static final int OPEN = 0;
	public static final int ERROR = 1;
	public static final int STOP = 2;
	public static final int STOP_ACTION = 3;

	public static final int ROOM_CREATE = 4;
	public static final int ROOM_REMOVE = 5;
	public static final int USER_JOIN = 6;
	

	@Override
	public void event(ServerListener listener, int event, Object... objs) {
		switch(event) {
			case OPEN:
				listener.open((ServerEvent) objs[0]);
				break;
				
			case ERROR:
				listener.error((ServerEvent) objs[0]);
				break;
				
			case STOP_ACTION:
				listener.stopAction((ServerEvent) objs[0]);
				break;
				
			case STOP:
				listener.stop((ServerEvent) objs[0]);
				break;
				
			case ROOM_CREATE:
				listener.roomCreate((ServerEvent) objs[0]);
				break;
				
			case ROOM_REMOVE:
				listener.roomRemove((ServerEvent) objs[0]);
				break;
				
			case USER_JOIN:
				listener.userJoin((ServerEvent) objs[0]);
				break;
		}
	}

}
