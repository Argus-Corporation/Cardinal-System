package net.argus.event.net.server;

import net.argus.util.Listener;

public interface ServerListener extends Listener {
	
	public void open(ServerEvent e);
	
	public void error(ServerEvent e);
	
	public void stop(ServerEvent e);

}
