package net.argus.net.server;

import java.io.InputStream;
import java.io.PrintStream;

import net.argus.instance.Instance;
import net.argus.net.server.role.Role;
import net.argus.net.server.room.RoomRegister;
import net.argus.net.socket.system.SystemSocket;

public class SystemUser {
	
	private ServerProcess process;
	
	public SystemUser() {
		process = new ServerProcess(RoomRegister.getRoom(0));
		process.setCardinalSocket(new SystemSocket());
		process.getCardinalSocket().setRole(Role.SYSTEM);
		process.getCardinalSocket().getProfile().setName("system");
	}
	
	public void start(InputStream in, PrintStream out) {
		Instance.startThread(new Thread(new SystemUserProcess(process, in, out)));
	}
	
}
