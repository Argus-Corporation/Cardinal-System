package net.argus.event.mouse;

import java.awt.event.MouseEvent;

import net.argus.util.Listener;

public interface MouseTrackListener extends Listener {
	
	public void mouseEntered(MouseEvent e);
	public void mouseExited(MouseEvent e);
	public void mouseClicked(MouseEvent e);
	
}
