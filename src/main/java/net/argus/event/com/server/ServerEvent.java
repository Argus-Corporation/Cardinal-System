package net.argus.event.com.server;

import net.argus.beta.com.CardinalSocket;

public class ServerEvent {
	
	private CardinalSocket socket;
	private Object parent;
	
	public ServerEvent(CardinalSocket socket, Object parent) {
		this.socket = socket;
		this.parent = parent;
	}
	
	public CardinalSocket getSocket() {return socket;}
	
	public Object getParent() {return parent;}

}
