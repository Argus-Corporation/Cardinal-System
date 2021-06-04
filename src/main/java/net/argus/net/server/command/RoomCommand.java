package net.argus.net.server.command;

import java.io.IOException;

import net.argus.net.pack.PackagePrefab;
import net.argus.net.server.ServerProcess;
import net.argus.net.server.command.structure.StructuredCommand;

public class RoomCommand extends Command {

	public RoomCommand() {
		super("room");
	}

	@Override
	protected void run(StructuredCommand com, ServerProcess process) throws IOException {
		process.send(PackagePrefab.genInfoPackage("Current room: " + process.getRoom().getName()));
	}

}
