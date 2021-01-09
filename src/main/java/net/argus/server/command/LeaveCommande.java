package net.argus.server.command;

import java.io.IOException;

import net.argus.exception.SecurityException;
import net.argus.server.ServerSocketClient;
import net.argus.util.ErrorCode;

public class LeaveCommande extends Command {

	public LeaveCommande(String commandName, int rank) {
		super(commandName, rank);
	}
	
	@Override
	protected void run(String[] com, ServerSocketClient client) throws IOException, SecurityException {
		client.logOut("Leave", ErrorCode.leave);
		super.run(com, client);
	}

}
