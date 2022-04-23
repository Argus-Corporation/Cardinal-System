package net.argus.event.program;

import net.argus.event.Event;

public class EventProgram extends Event<ProgramListener> {
	
	public static final int INPUT = 0;
	public static final int OUTPUT = 1;

	@Override
	public void event(ProgramListener listener, int event, Object... objs) {
		switch(event) {
			case INPUT:
				listener.input((ProgramEvent) objs[0]);
				break;
	
			case OUTPUT:
				listener.output((ProgramEvent) objs[0]);
				break;
		}
	}

}
