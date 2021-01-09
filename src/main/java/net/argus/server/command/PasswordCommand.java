package net.argus.server.command;

import java.io.IOException;

import net.argus.exception.SecurityException;
import net.argus.server.ServerSocketClient;
import net.argus.server.role.Role;
import net.argus.util.debug.Debug;

public class PasswordCommand extends Command {

	public PasswordCommand(String commandName, int rank) {
		super(commandName, rank);
	}
	
	@Override
	protected void run(String[] com, ServerSocketClient client) throws IOException, SecurityException {
		Role role = Role.getRole(com[1]);
		
		if(role != null) {
			client.setRole(role);
			client.getProcessServer().sendMessage("Your role has been updated to: " + role.getName());
		}else {
			client.getProcessServer().sendMessage("This password is not valid");
			Debug.log("Error: This password is not valid");
		}
	}

}
