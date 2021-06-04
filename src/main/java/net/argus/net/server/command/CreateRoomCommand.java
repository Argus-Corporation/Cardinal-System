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

public class CreateRoomCommand extends Command {

	public CreateRoomCommand() {
		super("createroom", new Structure()
				.add("room name")
				.add("size", KeyType.INT, false)
				.add("password", KeyType.STRING, false));
	}

	@Override
	protected void run(StructuredCommand com, ServerProcess process) throws IOException {
		int size = Room.DEFUALT_ROOM_SIZE;
		String password = null;
	
		if(com.length() > 1)
			size = (int) com.get(1);
		
		if(com.length() > 2)
			password = (String) com.get(2);
		
		if(RoomRegister.isExist(com.get(0).toString())) {
			Debug.log("Room \"" + com.get(0).toString() + "\" already exist");
			process.send(PackagePrefab.genInfoPackage("Room \"" + com.get(0).toString() + "\" already exist"));
			return;
		}
		
		Room room = new Room(com.get(0).toString(), size, password, process.getRoom().getParent());
		process.getRoom().move(process, password, room);
	}

}
