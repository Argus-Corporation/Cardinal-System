package net.argus.event.com.server;

import net.argus.util.Listener;

public interface ServerListener extends Listener {
	
	public void newClient(ServerEvent e);

}
