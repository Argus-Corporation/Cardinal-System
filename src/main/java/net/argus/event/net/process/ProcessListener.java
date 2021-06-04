package net.argus.event.net.process;

import net.argus.util.Listener;

public interface ProcessListener extends Listener {
	
	public void nextPackage(ProcessEvent e);

}
