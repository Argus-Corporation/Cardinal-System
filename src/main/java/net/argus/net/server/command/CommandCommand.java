package net.argus.net.server.command;

import java.io.IOException;

import net.argus.net.pack.PackagePrefab;
import net.argus.net.server.ServerProcess;
import net.argus.net.server.command.structure.KeyType;
import net.argus.net.server.command.structure.Structure;
import net.argus.net.server.command.structure.StructuredCommand;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class CommandCommand extends Command {

	public CommandCommand() {
		super("command", new Structure()
				.add("command")
				.add("activate", KeyType.BOOLEAN), 5);
	}

	@Override
	protected void run(StructuredCommand com, ServerProcess process) throws IOException {
		Command command = getCommand((String) com.get(0));
		
		if(command == null) {
			Debug.log("Command \"" + com.get(0) + "\" is not registered", Info.ERROR);
			process.send(PackagePrefab.genInfoPackage("Command \"" + com.get(0) + "\" is not registered"));
			return;
		}

		if(command.getRank() > process.getCardinalSocket().getProfile().getRole().getRank()) {
			Debug.log("Impossible to modify a command greater than its rank", Info.ERROR);
			process.send(PackagePrefab.genInfoPackage("Impossible to modify a command greater than its rank"));
			return;
		}
		
		if(command.getName().equals(getName())) {
			Debug.log("Impossible to modify this command", Info.ERROR);
			process.send(PackagePrefab.genInfoPackage("Impossible to modify this command"));
			return;
		}
		
		command.setActivate(Boolean.valueOf(com.get(1).toString()));
	}
	
	@Override
	public Command setActivate(boolean activate) {
		return this;
	}

}
