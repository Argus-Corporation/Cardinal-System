package net.argus.net.server;

import java.io.IOException;

import net.argus.net.pack.Package;
import net.argus.net.pack.PackageBuilder;
import net.argus.net.pack.PackageType;
import net.argus.net.socket.CardinalSocket;
import net.argus.util.debug.Debug;

public class ServerSecurity {
	
	public static boolean check(CardinalSocket client) throws IOException {
		Package pack = client.nextPackage();
		
		boolean match = true;
		
		/**--SOCKET--**/
		String socket = pack.getValue("Socket").toString();
		if(!socket.equals(client.getClass().getName())) {
			match = false;
			Debug.log("Socket \"" + socket + "\" is not supported on this server");
		}
		
		/**--LIST--**/
		Object[] clientInfos = pack.getArray("Socket-Info");
		if(clientInfos != null)
			client.setInfos(clientInfos);
		
		/**--SEND--**/
		PackageBuilder builder = new PackageBuilder(PackageType.CONNECTION);
		builder.addKey("Match-Socket", match);
		if(match) {
			Object[] infos = client.getInfos();
			if(infos != null)
				builder.addKey("Socket-Info", infos);
		}
			
		client.send(builder.genPackage());
		client.setMatchSocket(match);
		
		return match;
	}

}
