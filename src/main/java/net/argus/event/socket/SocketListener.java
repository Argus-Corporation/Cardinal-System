package net.argus.event.socket;

import net.argus.util.Listener;

public interface SocketListener extends Listener {
	
	public void connect(SocketEvent e);
	
	public void disconnect(SocketEvent e);
	
	public void errorConnection(SocketEvent e);

}
