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

public class JoinRoomCommand extends Command {

	public JoinRoomCommand() {
		super("joinroom", new Structure()
				.add("room name")
				.add("password", KeyType.STRING, false));
	}

	@Override
	protected void run(StructuredCommand com, ServerProcess process) throws IOException {
		Room room = RoomRegister.getRoom(com.get(0).toString());
		if(room == null) {
			Debug.log("Room \"" + com.get(0) + "\" doesn't exist");
			process.send(PackagePrefab.genInfoPackage("Room \"" + com.get(0) + "\" doesn't exist"));
			return;
		}
		
		String password = null;
		
		if(com.length() > 1)
			password = (String) com.get(1);
		
		process.getRoom().move(process, password, room);
	}

}
