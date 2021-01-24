package net.argus.system;

import net.argus.util.Listener;

public interface InitializedSystemManager extends Listener {
	
	public void preInit(String[] args);
	
	public void init(String[] args);
	
	public void postInit(String[] args);

}
