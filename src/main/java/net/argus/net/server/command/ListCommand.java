package net.argus.net.server.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.argus.net.pack.PackagePrefab;
import net.argus.net.server.ServerProcess;
import net.argus.net.server.command.structure.StructuredCommand;

public class ListCommand extends Command {

	public ListCommand() {
		super("list");
	}

	@Override
	protected void run(StructuredCommand com, ServerProcess process) throws IOException {
		List<String> userNames = new ArrayList<String>();
		
		for(ServerProcess sock : process.getRoom().getClients())
			userNames.add(sock.getCardinalSocket().getProfile().getName());
		
		process.send(PackagePrefab.genInfoPackage((String[]) userNames.toArray(new String[userNames.size()])));
	}

}
