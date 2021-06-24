package net.argus.event.gui.frame;

import net.argus.util.Listener;

public interface FrameListener extends Listener {
	
	public abstract void frameClosing(FrameEvent e);
	public abstract void frameMinimalized(FrameEvent e);
	public abstract void frameResizing(FrameEvent e);
	
}
