package net.argus.server.command;


import java.io.IOException;

import net.argus.exception.SecurityException;
import net.argus.server.ServerSocketClient;
import net.argus.server.Users;
import net.argus.util.debug.Debug;

public class UnBanCommand extends Command {

	public UnBanCommand(String commandName, int rank) {
		super(commandName, rank);
	}
	
	@Override
	protected void run(String[] com, ServerSocketClient client) throws IOException, SecurityException {
		int returnValue = Users.getBanIpFile().deleteValue(com[1]);
		
		if(returnValue == -1) {
			client.getProcessServer().sendMessage(com[1] + " is not banned");
			Debug.log("Error: this ip is not banned");
		}else {
			client.getProcessServer().sendMessage("this ip \"" + com[1] + "\"" + " is unbanned");
			Debug.log(com[1] + " is unbanned");
		}
	}

}
