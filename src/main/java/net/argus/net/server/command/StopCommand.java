package net.argus.net.server.command;

import java.io.IOException;

import net.argus.net.server.ServerProcess;
import net.argus.net.server.command.structure.KeyType;
import net.argus.net.server.command.structure.Structure;
import net.argus.net.server.command.structure.StructuredCommand;

public class StopCommand extends Command {

	public StopCommand() {
		super("stop", new Structure()
				.add("arguments", KeyType.STRING, false),
				10);
	}

	@Override
	protected void run(StructuredCommand com, ServerProcess process) throws IOException {
		String arg = "stop command";
		if(com.length() > 0)
			arg = com.get(0).toString();
		
		process.getRoom().getParent().stop(arg);
	}

}
