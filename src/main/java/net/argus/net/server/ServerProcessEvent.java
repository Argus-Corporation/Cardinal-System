package net.argus.net.server;

import net.argus.event.net.process.EventProcess;
import net.argus.event.net.process.ProcessEvent;
import net.argus.event.net.process.ProcessListener;

public class ServerProcessEvent {
	
	private static final EventProcess event = new EventProcess();
	
	public static void addProcessListener(ProcessListener listener) {event.addListener(listener);}
	
	public static void startProcessEvent(int event, ProcessEvent e) {ServerProcessEvent.event.startEvent(event, e);}

}
