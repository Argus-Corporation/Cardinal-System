package net.argus.serveur;

import net.argus.util.pack.Package;

public interface ServeurManager {
	
	public void receivePackage(Package pack, ProcessServeur thisObj);

}
