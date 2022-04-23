package net.argus.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import net.argus.instance.Instance;
import net.argus.net.BanRegister;
import net.argus.net.server.room.Room;
import net.argus.net.socket.CardinalSocket;
import net.argus.system.Network;
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
					connect(socket, serverSocket.accept());
			}catch(IOException e) {}
			Debug.log("Thread of server connector was closed");
		});
		
		connect.setName("Server-Connector");
		
		Instance.startThread(connect);
		Debug.log("Server version: " + Server.VERSION);
		Debug.log("Server address: " + Network.getLocalHost());
		Debug.log("Server port: " + serverSocket.getLocalPort());
		Debug.log("Server opened");
	}
	
	public void connect(CardinalSocket socket, Socket client) throws IOException {
		new ServerConnection(new ServerProcess(null), defaultRoom, serverBanRegister).connect(socket.create(client));		
	}
	
	public void close() {closed = true;}

}
