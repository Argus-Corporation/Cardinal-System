package net.argus.server.command;

import java.io.IOException;

import net.argus.exception.SecurityException;
import net.argus.server.Regular;
import net.argus.server.ServerSocketClient;
import net.argus.server.Users;
import net.argus.util.ArrayManager;
import net.argus.util.ErrorCode;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;

public class KickCommand extends Command {

	public KickCommand(String commandName, int rank) {
		super(commandName, rank);
	}
	
	@Override
	protected void run(String[] com, ServerSocketClient client) throws SecurityException, IOException {
		String arg = null;
		ServerSocketClient userTarget = null;
		
		if(Regular.isExist(com[1])) {
			switch(Regular.getRegular(com[1])) {
				case a:
					client.getServerParent().getUsers().closeAll(client.getUserId());
					return;
					
				case s:
					userTarget = client;
					break;
			}
		}else
			userTarget = Users.getServerSocketClient(com[1]);
		
		if(userTarget == null) {
			client.getProcessServer().sendMessage("Target client does existed");
			Debug.log("Error: this client does existed");
			return;
		}
		
		if(ArrayManager.isExist(com, 2)) {
			arg = "";
			for(int i = 2; i < com.length; i++)
				arg += com[i] + " ";
			
			arg = arg.substring(0, arg.length() - 1);
		}
		
		if(userTarget.getRole().getRank() <= client.getRole().getRank()) {
			userTarget.getProcessServer().setprocessLogOuting(true);
			userTarget.logOut("You are kicked by " + client.getPseudo() + (arg!=null?": " + arg:" "), ErrorCode.kick);
			ThreadManager.stop(userTarget.getProcessServer());
		}else {
			client.getProcessServer().sendMessage("You do not have permission to kicked this client");
			Debug.log("Error: request refused");
			return;
		}
	}
	
}
