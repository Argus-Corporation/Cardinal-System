package net.argus.system;

public interface InitializedSystemManager {
	
	public void preInit(String[] args);
	
	public void init(String[] args);
	
	public void postInit(String[] args);

}
