package net.argus.client;

import net.argus.exception.SecurityException;
import net.argus.util.Listener;
import net.argus.util.pack.Package;

public interface ClientManager extends Listener {
	
	public void receivePackage(Package pack, ProcessClient thisObj) throws SecurityException;
	
	public void disconnected(String msg)  throws SecurityException;

}
