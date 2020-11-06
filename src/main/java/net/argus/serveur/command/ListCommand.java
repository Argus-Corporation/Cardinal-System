package net.argus.serveur.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.argus.serveur.ServeurSocketClient;
import net.argus.serveur.Users;
import net.argus.util.ArrayManager;
import net.argus.util.PackageType;

public class ListCommand extends Command {

	public ListCommand(String commandName, int rank) {
		super(commandName, rank);
	}
	
	@Override
	protected void run(String[] com, ServeurSocketClient ssc) throws IOException, SecurityException {
		List<String> pseudos = new ArrayList<String>();
		
		for(ServeurSocketClient client : Users.getServeurSocketClient())
			if(client != null)
				pseudos.add(client.getPseudo());
		
		ssc.sendArray(PackageType.PSEUDO, ArrayManager.convert(pseudos));
	}

}
