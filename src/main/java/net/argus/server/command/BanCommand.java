package net.argus.server.command;

import java.io.IOException;

import net.argus.exception.SecurityException;
import net.argus.server.ServerSocketClient;
import net.argus.server.Users;

public class BanCommand extends Command {

	public BanCommand(String commandName, int rank) {
		super(commandName, rank);	
	}
	
	@Override
	protected void run(String[] com, ServerSocketClient client) throws IOException, SecurityException {
		ServerSocketClient ban = Users.getServerSocketClient(com[1]);
		
		Users.getBanIpFile().wirter(ban.getIp().substring(1));
		
		String args = "";
		for(int i = 2; i < com.length; i++)
			args += com[i] + " ";
		
		args = args.substring(0, args.length() - 1);
		
		Commands.KICK.execut(("/kick " + com[1] + " " + args).split(" "), client);
	}

}
