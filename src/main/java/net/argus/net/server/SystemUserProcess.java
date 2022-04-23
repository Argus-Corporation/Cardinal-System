package net.argus.net.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import net.argus.net.server.command.Command;
import net.argus.net.socket.CardinalSocket;
import net.argus.net.socket.system.SystemSocket;
import net.argus.util.debug.Debug;

public class SystemUserProcess implements Runnable {
	
	private ServerProcess process;
	
	private InputStream in;
	private PrintStream out;
	
	public SystemUserProcess(ServerProcess process, InputStream in, PrintStream out) {
		this.process = process;
		
		this.in = in;
		this.out = out;
		CardinalSocket cs = process.getCardinalSocket();
		if(cs instanceof SystemSocket) {
			SystemSocket socket = (SystemSocket) process.getCardinalSocket();
			socket.setOutput(out);
		}
		
	}
	
	@SuppressWarnings("resource")
	@Override
	public void run() {
		Thread.currentThread().setName("system-user");
		
		while(/*!process.getRoom().getParent().isStoped()*/process.getCardinalSocket().isConnected()) {
			String line = null;
			try {line = new Scanner(in).nextLine();}
			catch(NoSuchElementException e) {break;}
			
			if(line == null)
				continue;
				
			if(line != null && !line.equals("")) {
				String c = line.split(" ")[0].toUpperCase();
				if(!c.startsWith("/"))
					c = "/" + line;
				
				Command com = Command.getCommand(c);
				if(com == null)
					continue;
				
				try {
					com.execut(line, process);
				}catch(IOException e) {e.printStackTrace();}
			}
			
		}
		
		Debug.log("System user close");
	}
	
	public InputStream getInputStream() {return in;}
	public PrintStream getPrintStream() {return out;}

}
