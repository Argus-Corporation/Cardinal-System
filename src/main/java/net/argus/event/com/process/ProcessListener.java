package net.argus.event.com.process;

import net.argus.util.Listener;

public interface ProcessListener extends Listener {
	
	public void accept(ProcessEvent e);
	public void refuse(ProcessEvent e);
	public void close(ProcessEvent e);
	
	public void newPackage(ProcessEvent e);

}
