package net.argus.net.server.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.argus.net.pack.PackagePrefab;
import net.argus.net.server.ServerProcess;
import net.argus.net.server.command.structure.StructuredCommand;
import net.argus.net.server.room.Room;
import net.argus.net.server.room.RoomRegister;
import net.argus.util.ArrayManager;

public class ListRoomCommand extends Command {

	public ListRoomCommand() {
		super("listroom");
	}

	@Override
	protected void run(StructuredCommand com, ServerProcess process) throws IOException {
		process.send(PackagePrefab.genInfoPackage(getRooms(process)));
	}
	
	private static String[] getRooms(ServerProcess process) {
		List<String> rooms = new ArrayList<String>();
		for(Room r : RoomRegister.getRooms())
			rooms.add(r.getName() + "  -->  " + (r.isPrivate()?"private":"public") + (process.getRoom().equals(r)?"    (current)":""));
		
		return ArrayManager.convert(rooms, new String[0]);
	}

}
