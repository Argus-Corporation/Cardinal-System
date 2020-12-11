package net.argus.serveur.command;

import java.io.IOException;

import net.argus.exception.SecurityException;
import net.argus.serveur.ServeurSocketClient;
import net.argus.serveur.Users;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;

public class KickCommand extends Command {

	public KickCommand(String commandName, int rank) {
		super(commandName, rank);
	}
	
	@Override
	protected void run(String[] com, ServeurSocketClient ssc) throws SecurityException, IOException {
		String arg = "";
		ServeurSocketClient userTarget;
		
		userTarget = Users.getServeurSocketClient(com[1]);
		
		if(userTarget == null) {
			ssc.getProcessServeur().sendMessage(com[1] + " not found");
			Debug.log(com[1] + " not found");
			return;
		}
		
		for(int i = 2; i < com.length; i++)
			arg += com[i] + " ";
		
		arg = arg.substring(0, arg.length() - 1);
		
		if(userTarget.getRole().getRank() <= ssc.getRole().getRank()) {
			userTarget.getProcessServeur().setprocessLogOuting(true);
			userTarget.logOut(arg);
			ThreadManager.stop(userTarget.getProcessServeur());
		}else {
			ssc.getProcessServeur().sendMessage("You do not have permission to kicked this client");
			Debug.log("Error: request refused");
		}
	}
	
}
