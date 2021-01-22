package net.argus.server.command;

import java.io.IOException;

import net.argus.exception.SecurityException;
import net.argus.server.ServerSocketClient;

public class NickCommand extends Command {

	public NickCommand(String commandName, int rank) {
		super(commandName, rank);
	}
	
	@Override
	protected void run(String[] com, ServerSocketClient client) throws IOException, SecurityException {
		String newPseudo = "";
		
		for(int i = 1; i < com.length; i++)
			newPseudo += com[i] + " ";
		
		newPseudo = newPseudo.substring(0, newPseudo.length() - 1);
		
		client.getProcessServer().setPseudo(newPseudo);
	}

}
