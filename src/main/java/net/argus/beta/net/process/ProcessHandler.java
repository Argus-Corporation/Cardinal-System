package net.argus.beta.net.process;

public class ProcessHandler {
	
	private String path;
	private Process process;

	public ProcessHandler(String path, Process process) {
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
