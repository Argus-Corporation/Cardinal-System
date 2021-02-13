package net.argus.server.command;

import java.io.IOException;

import net.argus.exception.SecurityException;
import net.argus.server.ServerSocketClient;
import net.argus.server.Users;
import net.argus.util.ArrayManager;
import net.argus.util.debug.Debug;

public class BanCommand extends Command {

	public BanCommand(String commandName, int rank) {
		super(commandName, rank);
	}

	@Override
	protected void run(String[] com, ServerSocketClient client) throws IOException, SecurityException {
		ServerSocketClient target = Users.getServerSocketClient(com[1]);

		if(target != null) {
			Users.getBanIpFile().wirter(target.getIpClient());
			String args = "";
			if(ArrayManager.isExist(com, 2)) {
				for(int i = 2; i < com.length; i++)
					args += com[i] + " ";

				args = args.substring(0, args.length() - 1);
			}

			Commands.KICK.execut(("/kick " + com[1] + " " + args).split(" "), client);
		}else {
			client.getProcessServer().sendMessage("Target client does existed");
			Debug.log("Error: this client does existed");
			return;
		}
	}

}
