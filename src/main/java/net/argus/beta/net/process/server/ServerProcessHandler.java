package net.argus.beta.net.process.server;

import net.argus.beta.net.process.Process;

public class ServerProcessHandler {
	
	private String path;
	private Process process;

	protected ServerProcessHandler(String path, Process process) {
		this.path = path;
		this.process = process;
	}
		
	public String getPath() {
		return path;
	}
	
	public Process getProcess() {
		return process;
	}
	
	@Override
	public String toString() {
		return path + "@" + process.getClass().getCanonicalName();
	}
	
}
