package net.argus.event.change;

import net.argus.util.Listener;

public interface ChangeListener extends Listener {
	
	public void valueChanged(ChangeEvent e);

}
