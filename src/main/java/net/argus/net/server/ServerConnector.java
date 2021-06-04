package net.argus.net.server;

import java.io.IOException;
import java.net.ServerSocket;

import net.argus.instance.Instance;
import net.argus.net.BanRegister;
import net.argus.net.server.room.Room;
import net.argus.net.socket.CardinalSocket;
import net.argus.util.debug.Debug;

public class ServerConnector {
	
	private BanRegister serverBanRegister;
	private ServerSocket serverSocket;
	private Room defaultRoom;
	
	private boolean closed;
	
	public ServerConnector(ServerSocket serverSocket, Room defaultRoom, BanRegister serverBanRegister) {
		this.serverBanRegister = serverBanRegister;
		this.serverSocket = serverSocket;
		this.defaultRoom = defaultRoom;
	}
	
	public void open(CardinalSocket socket) {
		Thread connect = new Thread(() ->  {
			try {
				while(!closed) 
					new ServerConnection(new ServerProcess(null), defaultRoom, serverBanRegister).connect(socket.create(this.serverSocket.accept()));
			}catch(IOException e) {}
			Debug.log("Thread of server connection was closed");
		});
		
		connect.setName("Server-Connector");
		
		Instance.startThread(connect);
		Debug.log("Server version: " + Server.VERSION);
		Debug.log("Server opened");
	}
	
	public void close() {closed = true;}

}
