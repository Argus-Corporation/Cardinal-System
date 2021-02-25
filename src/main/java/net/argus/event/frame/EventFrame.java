package net.argus.event.frame;

import java.util.List;

import net.argus.event.Event;

public class EventFrame extends Event<FrameListener> {
	
	public static final int FRAME_CLOSING = 0;
	public static final int FRAME_MINIMALIZED = 1;
	public static final int FRAME_RESIZING = 2;
	

	@Override
	public void event(FrameListener listener, int event, List<Object> objs) {
		switch(event) {
			case FRAME_CLOSING:
				listener.frameClosing((FrameEvent) objs.get(0));
				break;
				
			case FRAME_MINIMALIZED:
				listener.frameMinimalized((FrameEvent) objs.get(0));
				break;
				
			case FRAME_RESIZING:
				listener.frameResizing((FrameEvent) objs.get(0));
				break;
		}
	}

}
