package net.argus.serveur.command;

import java.io.IOException;

import net.argus.serveur.ServeurSocketClient;
import net.argus.serveur.Users;

public class UnBanCommand extends Command {

	public UnBanCommand(String commandName, int rank) {
		super(commandName, rank);
	}
	
	@Override
	protected void run(String[] com, ServeurSocketClient ssc) throws IOException, SecurityException {
		Users.getBanIpFile().deleteValue(com[1]);
	}

}
