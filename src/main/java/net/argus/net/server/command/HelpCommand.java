package net.argus.net.server.command;

import java.io.IOException;
import java.util.List;

import net.argus.net.pack.PackagePrefab;
import net.argus.net.server.ServerProcess;
import net.argus.net.server.command.structure.StructuredCommand;

public class HelpCommand extends Command {

	public HelpCommand() {
		super("help");
	}

	@Override
	protected void run(StructuredCommand com, ServerProcess process) throws IOException {
		List<Command> coms = getCommands(process.getCardinalSocket().getProfile().getRole());
		process.send(PackagePrefab.genInfoPackage((Command[]) coms.toArray(new Command[coms.size()])));
	}

}
