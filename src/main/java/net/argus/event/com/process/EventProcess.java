package net.argus.event.com.process;

import net.argus.event.Event;

public class EventProcess extends Event<ProcessListener> {
	
	public static final int ACCEPT = 0;
	public static final int REFUSE = 1;
	public static final int CLOSE = 2;
	
	public static final int NEW_PACKAGE = 3;

	@Override
	public void event(ProcessListener listener, int event, Object... objs) {
		switch(event) {
			case ACCEPT:
				listener.accept((ProcessEvent) objs[0]);
				break;
			case REFUSE:
				listener.refuse((ProcessEvent) objs[0]);
				break;
			case CLOSE:
				listener.close((ProcessEvent) objs[0]);
				break;
				
			case NEW_PACKAGE:
				listener.newPackage((ProcessEvent) objs[0]);
				break;
		}
	}

}
