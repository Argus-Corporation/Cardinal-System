package net.argus.net.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import net.argus.event.net.socket.EventSocket;
import net.argus.event.net.socket.SocketEvent;
import net.argus.event.net.socket.SocketListener;
import net.argus.net.Profile;
import net.argus.net.pack.Package;
import net.argus.net.pack.PackagePrefab;
import net.argus.net.server.role.Role;
import net.argus.util.debug.Debug;

public abstract class CardinalSocket {
	
	protected EventSocket event = new EventSocket();
	
	protected Socket socket;
	protected Stream stream;
	
	protected boolean socketMatch;
	protected boolean connect;
	protected boolean close;
	
	protected final Profile PROFILE = new Profile("user");
	
	public CardinalSocket() {}
	
	public CardinalSocket(Socket socket) throws IOException {
		setSocket(socket);
	}
	
	protected void setSocket(Socket socket) throws IOException {
		this.socket = socket;
		this.stream = new Stream(socket);
	}
	
	public abstract void connect(String host, int port) throws UnknownHostException, IOException;
	
	public abstract Object[] getInfos();
	
	public abstract void setInfos(Object[] infos);
	
	public abstract Package nextPackage() throws IOException;
	
	public abstract void send(Object pack) throws IOException;
	
	public abstract CardinalSocket create() throws IOException;
	public abstract CardinalSocket create(Socket socket) throws IOException;
	
	public void logOut(String arg) throws IOException {
		logOut(false, arg);
	}
	
	public void logOut(boolean error, String arg) throws IOException {
		if(isClose())
			return;
		
		Debug.log("Log out argument: " + arg);
		
		send(PackagePrefab.genLogOutPackage(arg));
		Debug.log("Package of log out sended");
		
		close(error, arg);
	}
	
	public void close(String arg) throws IOException {
		close(false, arg);
	}
	
	public synchronized void close(boolean error, String arg) throws IOException {
		if(socket.isClosed())
			return;

		close = true;
		
		stream.close();
		socket.close();
		
		setConnected(false, error, arg, SocketStatus.CLOSE);
	}
	
	public void setMatchSocket(boolean match) {this.socketMatch = match;}
	
	public void setConnected(boolean connected, String arg, SocketStatus status) {
		setConnected(connected, false, arg, status);
	}
	
	public void setConnected(boolean connected, boolean error, String arg, SocketStatus status) {
		this.connect = connected;
		
		SocketEvent sockEvent = null;
		if(socket != null)
			sockEvent = new SocketEvent(socket, socket.getInetAddress().getHostAddress(), socket.getPort(), arg, status);
		else
			sockEvent = new SocketEvent(socket, null, -1, arg, status);
		
		if(connected) {
			event.startEvent(EventSocket.CONNECT, sockEvent);
			Debug.log(PROFILE.getName() + " is connected: " + arg);
		}else if(error) {
			event.startEvent(EventSocket.CONNECTION_REFUSED, sockEvent);
			Debug.log("Connection was refused: " + arg);
		}else {
			event.startEvent(EventSocket.DISCONNECT, sockEvent);
			Debug.log(PROFILE.getName() + " is disconected: " + arg);
		}
			
	}
	
	public void setRole(Role role) {
		PROFILE.setRole(role);
		try {send(PackagePrefab.genSystemPackage("Role Changed to \"" + role.getName() + "\""));}
		catch(IOException e) {
			Debug.log("Error on send system info");
		}
	}
	
	public InetAddress getInetAddress() {return socket.getInetAddress();}
	
	public boolean isSocketMatch() {return socketMatch;}
	public boolean isConnected() {return connect;}
	public boolean isClose() {return close;}
	
	public Profile getProfile() {return PROFILE;}
	
	public void addSocketListener(SocketListener listener) {event.addListener(listener);}

}
