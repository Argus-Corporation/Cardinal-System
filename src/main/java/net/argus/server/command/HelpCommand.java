package net.argus.server.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.argus.exception.SecurityException;
import net.argus.server.ServerSocketClient;
import net.argus.util.pack.PackageType;

public class HelpCommand extends Command {

	public HelpCommand(String commandName, int rank) {
		super(commandName, rank);
	}
	
	@Override
	protected void run(String[] com, ServerSocketClient client) throws IOException, SecurityException {
		List<String> commands = new ArrayList<String>();
		for(Command command : coms)
			if(command.getRank() <= client.getRole().getRank() && command.isActivate())
				commands.add(command.getCommandName().toLowerCase());
		
		client.sendArray(PackageType.MESSAGE, (String[]) commands.toArray(new String[commands.size()]));
	}

}
