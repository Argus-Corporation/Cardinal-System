package net.argus.server;

import net.argus.util.pack.Package;

public interface ServerManager {
	
	public void receivePackage(Package pack, ProcessServer thisObj);

}
