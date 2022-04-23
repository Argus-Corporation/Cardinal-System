package net.argus.event.program;

import net.argus.util.Listener;

public interface ProgramListener extends Listener {
	
	public void input(ProgramEvent e);
	
	public void output(ProgramEvent e);

}
