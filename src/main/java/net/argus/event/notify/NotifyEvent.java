package net.argus.event.notify;

import net.argus.util.notify.NotifyInfo;

public class NotifyEvent {
	
	private Object source;
	private NotifyInfo info;
	
	public NotifyEvent(Object source, NotifyInfo info) {
		this.source = source;
		this.info = info;
	}
	
	public Object getSource() {return source;}
	public NotifyInfo getInfo() {return info;}

}
