package net.argus.serveur.command;

import java.io.IOException;

import net.argus.exception.SecurityException;
import net.argus.serveur.ServeurSocketClient;

public class KickAllCommand extends Command {

	public KickAllCommand(String commandName, int rank) {
		super(commandName, rank);
	}
	
	@Override
	protected void run(String[] com, ServeurSocketClient ssc) throws IOException, SecurityException {
		ssc.getServeurParent().getUsers().closeAll(ssc.getUserId());
	}

}
