package net.argus.net.server.command;

import java.io.IOException;

import net.argus.net.pack.PackagePrefab;
import net.argus.net.server.ServerProcess;
import net.argus.net.server.command.structure.KeyType;
import net.argus.net.server.command.structure.Structure;
import net.argus.net.server.command.structure.StructuredCommand;
import net.argus.util.debug.Debug;

public class UserInfoCommand extends Command {

	public UserInfoCommand() {
		super("userinfo", new Structure()
				.add("user_name", KeyType.STRING, false));
	}

	@Override
	protected void run(StructuredCommand com, ServerProcess process) throws IOException {
		ServerProcess target = process;
		if(com.length() > 0) {
			ServerProcess sp = process.getRoom().getClientByName((String) com.get(0));
			if(sp == null) {
				Debug.log("User \"" + com.get(0) + "\" is not connected");
				process.send(PackagePrefab.genInfoPackage("User \"" + com.get(0) + "\" is not connected"));
				return;
			}
				target = sp;
		}
		
		process.send(PackagePrefab.genUserInfoPackage(target.getCardinalSocket().getProfile()));
	}

}
