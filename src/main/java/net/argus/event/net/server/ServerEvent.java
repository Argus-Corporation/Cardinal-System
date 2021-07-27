package net.argus.event.net.server;

import net.argus.net.server.Server;

public class ServerEvent {
	
	private Object obj;
	private Server server;
	
	public ServerEvent(Object obj, Server server) {
		this.obj = obj;
		this.server = server;
	}
	
	public Object getObject() {return obj;}
	public Server getServer() {return server;}

}
