package net.argus.event.mouse;

import java.awt.event.MouseEvent;
import java.util.List;

import net.argus.event.Event;

public class EventMouse extends Event<MouseTrackListener> {
	
	public static final int MOUSE_ENTERED = 0;
	public static final int MOUSE_EXITED = 1;
	public static final int MOUSE_CLICKED = 2;

	@Override
	public void event(MouseTrackListener listener, int event, List<Object> objs) {
		switch(event) {
			case MOUSE_ENTERED:
				listener.mouseEntered((MouseEvent) objs.get(0));
				break;
				
			case MOUSE_EXITED:
				listener.mouseExited((MouseEvent) objs.get(0));
				break;
				
			case MOUSE_CLICKED:
				listener.mouseClicked((MouseEvent) objs.get(0));
				break;
		}
	}

}
