package net.argus.net.server;

import java.io.IOException;

import net.argus.event.net.server.EventServer;
import net.argus.event.net.server.ServerEvent;
import net.argus.instance.Instance;
import net.argus.net.BanRegister;
import net.argus.net.Connection;
import net.argus.net.StatusConnection;
import net.argus.net.pack.Package;
import net.argus.net.pack.PackageBuilder;
import net.argus.net.pack.PackageType;
import net.argus.net.server.role.Role;
import net.argus.net.server.room.Room;
import net.argus.net.server.room.RoomRegister;
import net.argus.net.socket.CardinalSocket;
import net.argus.util.Version;
import net.argus.util.Version.State;

public class ServerConnection extends Connection {
	
	private BanRegister ban;
	
	private Room defaultRoom;
			
	public ServerConnection(ServerProcess process, Room defaultRoom, BanRegister ban) {
		super(process);
		this.defaultRoom = defaultRoom;
		
		this.ban = ban;
	}
	
	@Override
	public StatusConnection check(CardinalSocket client) throws IOException {
		boolean security = ServerSecurity.check(client);
		
		if(security == false)
			return new StatusConnection(false, "socket");
		
		/**--VERSION--**/
		Package version = client.nextPackage();
		
		Version.State state = Server.VERSION.getState(new Version(version.getValue("Version")));
		PackageBuilder versionBuilder = new PackageBuilder(PackageType.CONNECTION);
		versionBuilder.addKey("Valid-Version", Boolean.toString(state==State.EQUALS));
		versionBuilder.addKey("Version", Server.VERSION.getVersion());
		
		client.send(versionBuilder.genPackage());
		
		if(state != Version.State.EQUALS){
			return new StatusConnection(false, "version");
		}
		
		/**--MORE_INFO--**/
		Package moreInfo = client.nextPackage();
		
		String name = moreInfo.getValue("Profile-Name");
		if(name != null)
			client.getProfile().setName(name);
		
		String password = moreInfo.getValue("Password");
		if(password != null)
			client.getProfile().setRole(Role.getRoleByPassword(password));
		
		/**--CHECK_SECURITY--**/
		StatusConnection sec = checkSecutity(client);
		
		/**--SEND--**/		
		PackageBuilder builder = new PackageBuilder(PackageType.CONNECTION);
		builder.addKey("Accept-Connection", sec.isConnected());
		builder.addKey("Argument", sec.getArgument());
		
		client.send(builder.genPackage());

		return sec;
	}
	
	private StatusConnection checkSecutity(CardinalSocket client) {
		String ipclient = client.getInetAddress().getHostAddress();
		if(ban.isBanned(ipclient))
			return new StatusConnection(false, "serverban");
		
		if(defaultRoom.getBanRegister().isBanned(ipclient))
				return new StatusConnection(false, "ban");
 		
		if(defaultRoom.isFull())
			return new StatusConnection(false, "full");
		
		if(RoomRegister.isUserExist(client.getProfile().getName()))
			return new StatusConnection(false, "username");
		
		return new StatusConnection(true, "connected");
	}
	
	@Override
	protected void connectionAccepted(CardinalSocket socket) {
		ServerProcess p = (ServerProcess) getProcess();
		p.setCardinalSocket(socket);

		defaultRoom.join(p, null);
		
		super.connectionAccepted(socket);
		
		defaultRoom.getParent().getEvent().startEvent(EventServer.USER_JOIN, new ServerEvent(p, defaultRoom.getParent()));
	}
	
	/*@Override
	protected void connectionRefused(CardinalSocket socket, String arg) {
	/*	try {socket.logOut(true, arg);}
		catch(IOException e) {Debug.log("Error when logouting the client", Info.ERROR);}*/
/*	}
	*/
	@Override
	public void connect(CardinalSocket socket) {
		Thread connection = new Thread(() -> super.connect(socket));
		connection.setName("Server-Connection");
		
		Instance.startThread(connection);
	}

}
