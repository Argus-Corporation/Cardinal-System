package net.argus.system;

import net.argus.util.Listener;

public interface SystemListener extends Listener {
	
	public void systemInit(SystemEvent e);
	
}
