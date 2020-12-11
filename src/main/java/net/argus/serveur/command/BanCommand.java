package net.argus.serveur.command;

import java.io.IOException;

import net.argus.exception.SecurityException;
import net.argus.serveur.ServeurSocketClient;
import net.argus.serveur.Users;

public class BanCommand extends Command {

	public BanCommand(String commandName, int rank) {
		super(commandName, rank);	
	}
	
	@Override
	protected void run(String[] com, ServeurSocketClient ssc) throws IOException, SecurityException {
		ServeurSocketClient ban = Users.getServeurSocketClient(com[1]);
		
		Users.getBanIpFile().wirter(ban.getIp().substring(1));
		
		String args = "";
		for(int i = 2; i < com.length; i++)
			args += com[i] + " ";
		
		args = args.substring(0, args.length() - 1);
		
		Commands.KICK.execut(("/kick " + com[1] + " " + args).split(" "), ssc);
	}

}
