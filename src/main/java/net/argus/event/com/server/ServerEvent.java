package net.argus.event.com.server;

import net.argus.beta.com.NetSocket;

public class ServerEvent {
	
	private NetSocket socket;
	private Object parent;
	
	public ServerEvent(NetSocket socket, Object parent) {
		this.socket = socket;
		this.parent = parent;
	}
	
	public NetSocket getSocket() {return socket;}
	
	public Object getParent() {return parent;}

}
