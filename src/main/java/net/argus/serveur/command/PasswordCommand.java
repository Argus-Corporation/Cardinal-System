package net.argus.serveur.command;

import java.io.IOException;

import net.argus.serveur.ServeurSocketClient;
import net.argus.serveur.role.Role;
import net.argus.util.debug.Debug;

public class PasswordCommand extends Command {

	public PasswordCommand(String commandName, int rank) {
		super(commandName, rank);
	}
	
	@Override
	protected void run(String[] com, ServeurSocketClient ssc) throws IOException, SecurityException {
		Role role = Role.getRole(com[1]);
		
		if(role != null) {
			ssc.setRole(role);
			ssc.getProcessServeur().sendMessage("Your role has been updated to: " + role.getName());
		}else {
			ssc.getProcessServeur().sendMessage("This password is not valid");
			Debug.log("Error: This password is not valid");
		}
	}

}
