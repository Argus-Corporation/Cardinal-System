package net.argus.net.server;

import java.io.IOException;

import net.argus.event.net.process.EventProcess;
import net.argus.event.net.process.ProcessEvent;
import net.argus.net.Process;
import net.argus.net.pack.Package;
import net.argus.net.pack.PackagePrefab;
import net.argus.net.pack.PackageType;
import net.argus.net.server.command.Command;
import net.argus.net.server.room.Room;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class ServerProcess extends Process {
		
	private Room room;
	
	public ServerProcess(Room room) {this.room = room;}
	
	public void sendToAll(Package pack) throws IOException {
		room.sendToAll(pack, socket);
	}
	
	public void send(Package pack) throws IOException {
		socket.send(pack);
	}
	
	@Override
	public void newPackage(Package pack) throws IOException {
		if(room.isClosed()) {
			running = false;
			return;
		}
		packageParser(pack);
		
		ServerProcessEvent.startProcessEvent(EventProcess.NEXT_PACKAGE, new ProcessEvent(this, pack));
	}
	
	@Override
	public boolean isError() {
		return !room.isClosed();
				
	}
	
	private void packageParser(Package pack) throws IOException {
		PackageType type = pack.getType();
		if(type.equals(PackageType.LOG_OUT)) {
			String arg = pack.getValue("Argument");
			
			Debug.log("Log out argument: " + arg);

			socket.close(arg);
			close();
		}else if(type.equals(PackageType.COMMAND)) {
			String command = pack.getValue("Command");
			String comName;
			
			if(command == null) {
				Debug.log("Error: key \"Command\" equal null", Info.ERROR);
				return;
			}
			comName = command.split(" ")[0].toUpperCase();
			
			Command com = Command.getCommand(command);
			
			if(com == null) {
				Debug.log("Command \"" + comName + "\" is not registered", Info.ERROR);
				send(PackagePrefab.genInfoPackage("Command \"" + comName + "\" is not registered"));
				return;
			}
			
			com.execut(pack.getValue("Command"), this);
		}else if(type.equals(PackageType.PROFILE)) {
			String name = pack.getValue("Profile-Name");
			
			socket.getProfile().setName(name);
		}
		
	}
	
	@Override
	public void logOut(String arg) {
		room.logOut(this, arg);
	}
	
	public void setRoom(Room room) {this.room = room;}
	public Room getRoom() {return room;}

}
