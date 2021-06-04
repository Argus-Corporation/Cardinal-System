package net.argus.net.client;

import java.io.IOException;

import net.argus.net.Process;
import net.argus.net.pack.Package;
import net.argus.net.pack.PackageType;
import net.argus.net.socket.CardinalSocket;

public class ClientProcess extends Process {

	public ClientProcess() {}
	public ClientProcess(CardinalSocket socket) {super(socket);}

	@SuppressWarnings("deprecation")
	@Override
	public void newPackage(Package pack) throws IOException {
		PackageType type = pack.getType();
		
		if(type.equals(PackageType.LOG_OUT)) {
			socket.close(pack.getValue("Argument"));
			currentThread().stop();
		}
	}
	
}
