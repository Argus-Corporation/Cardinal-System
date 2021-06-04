package net.argus.net.server.command;

import java.io.IOException;

import net.argus.net.pack.PackagePrefab;
import net.argus.net.server.ServerProcess;
import net.argus.net.server.command.structure.KeyType;
import net.argus.net.server.command.structure.Structure;
import net.argus.net.server.command.structure.StructuredCommand;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class BanCommand extends Command {

	public BanCommand() {
		super("ban", new Structure()
				.add("target")
				.add("argument", KeyType.STRING, false),
				10);
	}

	@Override
	protected void run(StructuredCommand com, ServerProcess process) throws IOException {
		String arg = "ban command";
		if(com.length() > 1)
			arg = com.get(1).toString();
		
		String userName = com.get(0).toString();
		
		ServerProcess target = process.getRoom().getClientByName(userName);
		
		if(target == process) {
			Debug.log("You can't self ban");
			process.send(PackagePrefab.genInfoPackage("You can't self ban"));
			return;
		}
		
		if(target == null) {
			Debug.log("User target \"" + userName + "\" is not connected", Info.ERROR);
			process.send(PackagePrefab.genInfoPackage("User target \"" + userName + "\" is not connected"));
			return;
		}
		
		process.getRoom().ban(target, arg);
		
		Debug.log("User \"" + target.getCardinalSocket().getProfile().getName() +  "\" is banned by " + process.getSocket().getProfile().getName());
		process.send(PackagePrefab.genSystemPackage("User \"" + target.getCardinalSocket().getProfile().getName() +  "\" is banned"));
		process.sendToAll(PackagePrefab.genSystemPackage("User \"" + target.getCardinalSocket().getProfile().getName() +  "\" is banned by " + process.getSocket().getProfile().getName()));
	}

}
