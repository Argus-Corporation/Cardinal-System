package net.argus.gui;

import net.argus.util.Listener;

public interface FrameListener extends Listener {
	
	public abstract void frameClosing();
	public abstract void frameMinimalized();
	public abstract void frameResizing();
	
}
