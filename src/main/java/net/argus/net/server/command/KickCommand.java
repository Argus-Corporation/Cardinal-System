package net.argus.net.server.command;

import java.io.IOException;

import net.argus.net.pack.PackagePrefab;
import net.argus.net.server.ServerProcess;
import net.argus.net.server.command.structure.KeyType;
import net.argus.net.server.command.structure.Structure;
import net.argus.net.server.command.structure.StructuredCommand;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class KickCommand extends Command {

	public KickCommand() {
		super("kick", new Structure()
				.add("target")
				.add("argument", KeyType.STRING, false),
				5);
	}

	@Override
	protected void run(StructuredCommand com, ServerProcess process) throws IOException {
		String arg = "kick command";
		if(com.length() > 1)
			arg = com.get(1).toString();
		
		String userName = com.get(0).toString();
		
		ServerProcess target = process.getRoom().getClientByName(userName);
		
		if(target == process) {
			Debug.log("You can't self kick");
			process.send(PackagePrefab.genInfoPackage("You can't self kick"));
			return;
		}
		
		if(target == null) {
			Debug.log("Client target \"" + userName + "\" is not connected", Info.ERROR);
			process.send(PackagePrefab.genInfoPackage("User target \"" + userName + "\" is not connected"));
			return;
		}
		
		process.getRoom().logOut(target, arg);
	}

}
