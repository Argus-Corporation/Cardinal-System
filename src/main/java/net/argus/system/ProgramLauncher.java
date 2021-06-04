package net.argus.system;

import net.argus.system.launcher.Launcher;

public class ProgramLauncher {
	
	public static void main(String[] args) throws Exception {
		Launcher l = new Launcher(args);
		
		System.exit(l.start());
	}

}
