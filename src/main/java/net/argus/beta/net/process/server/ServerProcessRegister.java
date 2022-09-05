package net.argus.beta.net.process.server;

import net.argus.beta.net.ctp.CtpServer;
import net.argus.beta.net.process.ProcessRegister;

public class ServerProcessRegister extends ProcessRegister {
	
	private CtpServer parent;
	
	public ServerProcessRegister() {}
	
	public void linkPathToProcess(ServerProcess process) {
		linkPathToProcess(process.getPath(), process);
	}
	
	public CtpServer getParent() {
		return parent;
	}
	
	public void setParent(CtpServer parent) {
		if(parent == null)
			throw new NullPointerException();
		this.parent = parent;
	}
	
}
