package net.argus.event.net.server;

import net.argus.net.server.Server;

public class ServerEvent {
	
	private Server server;
	
	public ServerEvent(Server server) {
		this.server = server;
	}
	
	public Server getServer() {return server;}

}
