package net.argus.server.command;

import java.io.IOException;

import net.argus.server.ServerSocketClient;
import net.argus.server.Users;

public class UnBanCommand extends Command {

	public UnBanCommand(String commandName, int rank) {
		super(commandName, rank);
	}
	
	@Override
	protected void run(String[] com, ServerSocketClient client) throws IOException, SecurityException {
		Users.getBanIpFile().deleteValue(com[1]);
	}

}
