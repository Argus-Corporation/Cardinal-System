package net.argus.client;

import net.argus.util.Listener;

public interface ProcessListener extends Listener {
	
	public void addMessage(String[] value);
	public void addSystemMessage(String[] value);

}
