package net.argus.client;

import net.argus.exception.SecurityException;

public interface ClientManager {
	
	public void receiveMessage(int msgType) throws SecurityException;
	
	public void disconnected(String msg)  throws SecurityException;

}
