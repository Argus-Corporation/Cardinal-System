package net.argus.server.command;

import net.argus.server.ServerSocketClient;
import net.argus.server.Users;

public class StopCommand extends Command {

	public StopCommand(String commandName, int rank) {
		super(commandName, rank);
	}
	
	@Override
	protected void run(String[] com, ServerSocketClient client) {
		Users.getServerSocketClient(client.getUserId()).getServerParent().stop(client.getUserId());
	}

}
