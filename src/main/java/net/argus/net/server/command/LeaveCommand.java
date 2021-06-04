package net.argus.net.server.command;

import java.io.IOException;

import net.argus.net.server.ServerProcess;
import net.argus.net.server.command.structure.KeyType;
import net.argus.net.server.command.structure.Structure;
import net.argus.net.server.command.structure.StructuredCommand;

public class LeaveCommand extends Command {

	public LeaveCommand() {
		super("leave", new Structure()
				.add("arguments", KeyType.STRING, false));
	}

	@Override
	protected void run(StructuredCommand com, ServerProcess process) throws IOException {
		String arg = "leave command";
		if(com.length() > 0)
			arg = com.get(0).toString();
		
		process.close();
		process.getRoom().logOut(process, arg);
	}

}
