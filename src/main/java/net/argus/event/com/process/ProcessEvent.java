package net.argus.event.com.process;

import net.argus.beta.com.pack.Package;
import net.argus.beta.com.process.Process;

public class ProcessEvent {
	
	private Process process;
	private Package pack;
	
	private String argument;
	
	public ProcessEvent(Process process, Package pack, String argument) {
		this.process = process;
		this.pack = pack;
		this.argument = argument;
	}
	
	public Process getProcess() {return process;}
	
	public Package getPackage() {return pack;}
	
	public String getArgument() {return argument;}
}
