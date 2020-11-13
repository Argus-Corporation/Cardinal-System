package net.argus.client;

import net.argus.exception.SecurityException;
import net.argus.util.Package;

public interface ClientManager {
	
	public void receivePackage(Package pack, ProcessClient thisObj) throws SecurityException;
	
	public void disconnected(String msg)  throws SecurityException;

}
