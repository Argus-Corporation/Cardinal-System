package net.argus.net.client;

import java.io.IOException;

import net.argus.net.Connection;
import net.argus.net.Process;
import net.argus.net.StatusConnection;
import net.argus.net.pack.Package;
import net.argus.net.pack.PackagePrefab;
import net.argus.net.pack.PackageType;
import net.argus.net.socket.CardinalSocket;

public class ClientConnection extends Connection {
	
	public ClientConnection(Process process) {super(process);}

	@Override
	protected StatusConnection check(CardinalSocket socket) {
		try {
			/**--SEND_CONNECTION--**/
			Package connectionPack = PackagePrefab.genConnectionPackage(socket);
			socket.send(connectionPack);
			
			/**--REPLY--**/
			Package reply = socket.nextPackage();

			if(reply.getType().equals(PackageType.CONNECTION))
				if(reply.getValue("Match-Socket").equals("true"))
					socket.setMatchSocket(true);
				else
					return new StatusConnection(false, "socket");
			else if(reply.getType().equals(PackageType.LOG_OUT))
				return new StatusConnection(false, reply.getValue("Argument"));
			
			Object[] infos = reply.getArray("Socket-Info");
			if(infos != null)
				socket.setInfos(infos);
			
			/**--VERSION--**/
			Package version = PackagePrefab.genVersionPackage(Client.VERSION);
			socket.send(version);
			
			Package replyVersion = socket.nextPackage();
			
			if(!Boolean.valueOf(replyVersion.getValue("Valid-Version")))
				return new StatusConnection(false, "version");
						
			/**--SEND_MORE_INFO--**/
			Package moreInfo = PackagePrefab.genMoreInformationConnectionPackage(socket, socket.getProfile().getPassword());
			socket.send(moreInfo);
			
			socket.getProfile().setPassword(null);
			
			/**--ACCEPT--**/
			Package accept = socket.nextPackage();

			return new StatusConnection(accept.getValue("Accept-Connection").equals("true"), accept.getValue("Argument"));
		}catch(IOException e) {return new StatusConnection(false, "exception");}
	}

}
