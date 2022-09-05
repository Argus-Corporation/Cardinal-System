package net.argus.beta.net.process.client;

import net.argus.beta.net.process.ProcessRegister;

public class ClientProcessRegister extends ProcessRegister {
	
	public ClientProcessRegister() {}
	
	public void linkPathToProcess(ClientProcess process) {
		linkPathToProcess(process.getPath(), process);
	}
	
}
