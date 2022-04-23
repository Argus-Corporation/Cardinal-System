package net.argus.event.net.web;

import net.argus.util.Listener;

public interface WebListener extends Listener {
	
	public void accept(WebEvent e);

}
