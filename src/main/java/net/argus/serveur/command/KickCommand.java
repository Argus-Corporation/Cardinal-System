package net.argus.serveur.command;

import java.io.IOException;

import net.argus.exception.SecurityException;
import net.argus.serveur.Regular;
import net.argus.serveur.ServeurSocketClient;
import net.argus.serveur.Users;
import net.argus.util.ArrayManager;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;

public class KickCommand extends Command {

	public KickCommand(String commandName, int rank) {
		super(commandName, rank);
	}
	
	@Override
	protected void run(String[] com, ServeurSocketClient client) throws SecurityException, IOException {
		String arg = null;
		ServeurSocketClient userTarget = null;
		
		if(Regular.isRegular(com[1])) {
			switch(Regular.getRegular(com[1])) {
				case a:
					client.getServeurParent().getUsers().closeAll(client.getUserId());
					return;
					
				case s:
					userTarget = client;
					break;
			}
		}else {
			try {userTarget = Users.getServeurSocketClient(com[1]);}
			catch(NullPointerException e) {
				client.getProcessServeur().sendMessage("Target client does existed");
				Debug.log("Error: this client does existed");
				return;
			}
		}
		
		if(userTarget == null) {
			client.getProcessServeur().sendMessage(com[1] + " not found");
			Debug.log(com[1] + " not found");
			return;
		}
		
		if(ArrayManager.isExist(com, 2)) {
			arg = "";
			for(int i = 2; i < com.length; i++)
				arg += com[i] + " ";
			
			arg = arg.substring(0, arg.length() - 1);
		}
		
		if(userTarget.getRole().getRank() <= client.getRole().getRank()) {
			userTarget.getProcessServeur().setprocessLogOuting(true);
			userTarget.logOut("You are kicked by " + client.getPseudo() + (arg!=null?": " + arg:" "));
			ThreadManager.stop(userTarget.getProcessServeur());
		}else {
			client.getProcessServeur().sendMessage("You do not have permission to kicked this client");
			Debug.log("Error: request refused");
			return;
		}
	}
	
}
