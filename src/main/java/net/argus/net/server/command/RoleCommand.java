package net.argus.net.server.command;

import java.io.IOException;

import net.argus.net.pack.PackagePrefab;
import net.argus.net.server.ServerProcess;
import net.argus.net.server.command.structure.KeyType;
import net.argus.net.server.command.structure.Structure;
import net.argus.net.server.command.structure.StructuredCommand;
import net.argus.net.server.role.Role;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class RoleCommand extends Command {

	public RoleCommand() {
		super("role", new Structure()
				.add("role name")
				.add("password", KeyType.STRING, false));
	}

	@Override
	protected void run(StructuredCommand com, ServerProcess process) throws IOException {
		String password = null;
		
		if(com.length() > 1)
			password = (String) com.get(1);
		
		Role role = Role.getRole((String) com.get(0), password);
		if(role == null) {
			Debug.log("The role \"" + com.get(0) + "\" with this password doesn't exist", Info.ERROR);
			process.send(PackagePrefab.genInfoPackage("The role \"" + com.get(0) + "\" with this password doesn't exist"));
			return;
		}
		
		if(process.getCardinalSocket().getProfile().getRole().equals(Role.SYSTEM)) {
			Debug.log("The \"SYSTEM\" role prohibits role switching", Info.ERROR);
			process.send(PackagePrefab.genInfoPackage("The \"SYSTEM\" role prohibits role switching"));
			return;
		}
			
		
		process.getCardinalSocket().setRole(role);
	}

}
