package net.argus.event.change;

import net.argus.event.Event;

public class EventChange extends Event<ChangeListener> {
	
	public static final int VALUE_CHANGED = 0;

	@Override
	public void event(ChangeListener listener, int event, Object... objs) {
		switch(event) {
		case VALUE_CHANGED:
			listener.valueChanged((ChangeEvent) objs[0]);
			break;
		}
	}

}
