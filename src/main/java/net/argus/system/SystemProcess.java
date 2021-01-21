package net.argus.system;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.argus.util.ArrayManager;

public class SystemProcess {
	
	private ProcessBuilder builder;
	private Process process;
	
	public SystemProcess(String arg) {
		builder = new ProcessBuilder(new ArrayManager<String>().convert(arg.split(" ")));
	}
	
	public OutputStream getOutputStream() {
		return process.getOutputStream();
	}
	
	public InputStream getInputStream() {
		return process.getInputStream();
	}
	
	public void redirectErrorStream(boolean b) {
		builder.redirectErrorStream(b);
	}
	
	public Process start() throws IOException {
		return process = builder.start();
	}
	
}
