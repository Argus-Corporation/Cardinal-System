package net.argus.event.frame;

import net.argus.event.Event;

public class EventFrame extends Event<FrameListener> {
	
	public static final int FRAME_CLOSING = 0;
	public static final int FRAME_MINIMALIZED = 1;
	public static final int FRAME_RESIZING = 2;
	

	@Override
	public void event(FrameListener listener, int event, Object ... objs) {
		switch(event) {
			case FRAME_CLOSING:
				listener.frameClosing((FrameEvent) objs[0]);
				break;
				
			case FRAME_MINIMALIZED:
				listener.frameMinimalized((FrameEvent) objs[0]);
				break;
				
			case FRAME_RESIZING:
				listener.frameResizing((FrameEvent) objs[0]);
				break;
		}
	}

}
