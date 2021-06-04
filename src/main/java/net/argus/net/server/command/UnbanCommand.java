package net.argus.net.server.command;

import java.io.IOException;

import net.argus.net.pack.PackagePrefab;
import net.argus.net.server.ServerProcess;
import net.argus.net.server.command.structure.KeyType;
import net.argus.net.server.command.structure.Structure;
import net.argus.net.server.command.structure.StructuredCommand;
import net.argus.net.server.room.Room;
import net.argus.net.server.room.RoomRegister;
import net.argus.util.debug.Debug;

public class UnbanCommand extends Command {

	public UnbanCommand() {
		super("unban", new Structure()
				.add("ip")
				.add("room", KeyType.STRING, false),
				10);
	}

	@Override
	protected void run(StructuredCommand com, ServerProcess process) throws IOException {
		String ip = com.get(0).toString();
		
		Room room = process.getRoom();
		if(com.length() > 1)
			room = RoomRegister.getRoom(com.get(1).toString());
		
		room.unban(ip);
		
		Debug.log("Ip \"" + ip +  "\" is unbanned by " + process.getSocket().getProfile().getName());
		process.send(PackagePrefab.genSystemPackage("Ip \"" + ip +  "\" is unbanned"));
	}

}
