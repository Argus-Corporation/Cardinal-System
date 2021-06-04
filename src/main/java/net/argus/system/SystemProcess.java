package net.argus.system;

import java.io.IOException;

import net.argus.util.ArrayManager;

public class SystemProcess {
	
	private ProcessBuilder builder;
	
	public SystemProcess(String arg) {
		builder = new ProcessBuilder(ArrayManager.convert(arg.split(" ")));
	}
	
	public void redirectErrorStream(boolean b) {
		builder.redirectErrorStream(b);
	}
	
	public Process start() throws IOException {
		return builder.start();
	}
	
}
