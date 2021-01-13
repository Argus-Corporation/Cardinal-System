package net.argus.server.command;

import net.argus.server.ServerSocketClient;

public interface CommandListener {

	public abstract void execute(String[] com, ServerSocketClient ssc) throws CommandExeption, SecurityException;
	
}
