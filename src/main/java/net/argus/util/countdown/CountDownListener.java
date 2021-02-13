package net.argus.util.countdown;

import net.argus.util.Listener;

public interface CountDownListener extends Listener {
	
	public void valueChange(CountDownEvent e);
	
	public void finish(CountDownEvent e);

}
