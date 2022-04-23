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
import net.argus.util.debug.Info;

public class CloseCommand extends Command {

	public CloseCommand() {
		super("close", new Structure()
				.add("room", KeyType.STRING, false),
				10);
	}

	@Override
	protected void run(StructuredCommand com, ServerProcess process) throws IOException {
		Room room = process.getRoom();
		if(com.length() > 0)
			room = RoomRegister.getRoom(com.get(0).toString());
		
		if(room == null) {
			Debug.log("The target room is not registered", Info.ERROR);
			process.send(PackagePrefab.genInfoPackage("The target room is not registered"));
			return;
		}
		
		if(room.equals(process.getRoom().getParent().getMainRoom()) && RoomRegister.length() > 1) {
			Debug.log("Impossible to close room \"" + room.getName() + "\" now", Info.ERROR);
			process.send(PackagePrefab.genInfoPackage("Impossible to close room \"" + room.getName() + "\" now"));
			return;
		}
			
		room.close("close command");
	}

}
