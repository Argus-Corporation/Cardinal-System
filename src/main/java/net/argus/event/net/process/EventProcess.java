package net.argus.event.net.process;

import net.argus.event.Event;

public class EventProcess extends Event<ProcessListener> {
	
	public static final int NEXT_PACKAGE = 0;

	@Override
	public void event(ProcessListener listener, int event, Object ... objs) {
		switch(event) {
			case NEXT_PACKAGE:
				listener.nextPackage((ProcessEvent) objs[0]);
				break;
		}
	}

}
