package net.argus.server.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.argus.exception.SecurityException;
import net.argus.server.ServerSocketClient;
import net.argus.server.Users;
import net.argus.util.ArrayManager;
import net.argus.util.pack.PackageType;

public class ListCommand extends Command {

	public ListCommand(String commandName, int rank) {
		super(commandName, rank);
	}
	
	@Override
	protected void run(String[] com, ServerSocketClient ssc) throws IOException, SecurityException {
		List<String> pseudos = new ArrayList<String>();
		
		for(ServerSocketClient client : Users.getServerSocketClient())
			if(client != null)
				pseudos.add(client.getPseudo());
		
		ssc.sendArray(PackageType.PSEUDO, ArrayManager.convert(pseudos));
	}

}
