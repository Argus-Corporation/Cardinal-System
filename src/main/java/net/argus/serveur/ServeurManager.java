package net.argus.serveur;

import net.argus.util.Package;

public interface ServeurManager {
	
	public void receivePackage(Package pack, ProcessServeur thisObj);

}
