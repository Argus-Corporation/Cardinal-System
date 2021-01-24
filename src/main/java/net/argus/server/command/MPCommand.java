package net.argus.server.command;

import java.io.IOException;

import net.argus.exception.SecurityException;
import net.argus.server.ServerSocketClient;
import net.argus.server.Users;
import net.argus.util.debug.Debug;
import net.argus.util.pack.PackageType;

public class MPCommand extends Command {

	public MPCommand(String commandName, int rank) {
		super(commandName, rank);
	}
	
	@Override
	protected void run(String[] com, ServerSocketClient client) throws IOException, SecurityException {
		ServerSocketClient userTarget = Users.getServerSocketClient(com[1]);
		
		if(userTarget == null) {
			client.getProcessServer().sendMessage("Target client does existed");
			Debug.log("Error: this client does existed");
			return;
		}
		
		String msg = "";
		for(int i = 2; i < com.length; i++)
			msg += com[i] + " ";
		
		msg = msg.substring(0, msg.length() - 1);
		
		userTarget.getProcessServer().sendMessage(PackageType.MESSAGE, msg, client.getPseudo(), "MP");
	}

}
