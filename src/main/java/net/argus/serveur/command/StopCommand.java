package net.argus.serveur.command;

import java.io.IOException;

import net.argus.serveur.ServeurSocketClient;
import net.argus.serveur.Users;

public class StopCommand extends Command {

	public StopCommand(String commandName, int rank) {
		super(commandName, rank);
	}
	
	@Override
	protected void run(String[] com, ServeurSocketClient ssc) {
		try {Users.getServeurSocketClient(ssc.getUserId()).getServeurParent().stop(ssc.getUserId());}
		catch(IOException | SecurityException e) {e.printStackTrace();}
	}

}
