package net.argus.event.net.process;

import net.argus.net.Process;
import net.argus.net.pack.Package;

public class ProcessEvent {
	
	private Process process;
	private Package pack;
	
	public ProcessEvent(Process process, Package pack) {
		this.process = process;
		this.pack = pack;
	}
	
	public Process getProcess() {return process;}
	
	public Package getPackage() {return pack;}

}
