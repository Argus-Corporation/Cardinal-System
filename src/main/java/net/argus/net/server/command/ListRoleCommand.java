package net.argus.net.server.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.argus.net.pack.PackagePrefab;
import net.argus.net.server.ServerProcess;
import net.argus.net.server.command.structure.StructuredCommand;
import net.argus.net.server.role.Role;
import net.argus.util.ArrayManager;

public class ListRoleCommand extends Command {

	public ListRoleCommand() {
		super("listrole");
	}

	@Override
	protected void run(StructuredCommand com, ServerProcess process) throws IOException {
		process.send(PackagePrefab.genInfoPackage(getRooms(process)));
	}
	
	private static String[] getRooms(ServerProcess process) {
		List<String> rooms = new ArrayList<String>();
		for(String r : Role.getNames())
			rooms.add(r + (process.getSocket().getProfile().getRole().getName().equals(r)?"    (current)":""));
		
		return ArrayManager.convert(rooms, new String[0]);
	}

}
