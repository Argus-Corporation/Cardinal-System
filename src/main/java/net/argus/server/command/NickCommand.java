package net.argus.server.command;

import java.io.IOException;

import net.argus.exception.SecurityException;
import net.argus.server.Regular;
import net.argus.server.ServerSocketClient;
import net.argus.server.Users;
import net.argus.util.debug.Debug;

public class NickCommand extends Command {

	public NickCommand(String commandName, int rank) {
		super(commandName, rank);
	}
	
	@Override
	protected void run(String[] com, ServerSocketClient client) throws IOException, SecurityException {
		ServerSocketClient target = client;
		if(Regular.isExist(com[1]))
			switch(Regular.getRegular(com[1])) {
				case a:
					client.getProcessServer().sendMessage("You can't use \"@a\" for this command");
					Debug.log("Error: @a doesn't work with this command");
					return;
					
				case s:
					target = client;
					break;
			}
		else
			try {target = Users.getServerSocketClient(com[1]);}
			catch(NullPointerException e) {
				client.getProcessServer().sendMessage("Target client does existed");
				Debug.log("Error: this client does existed");
				return;
			}
		
		target.getProcessServer().setPseudo(com[2]);
	}

}
