package net.argus.event.notify;

import net.argus.util.Listener;

public interface NotifyListener extends Listener {
	
	public void show(NotifyEvent e);
	
	public void hide(NotifyEvent e);

}
