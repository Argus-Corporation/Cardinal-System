package net.argus.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import net.argus.event.socket.EventSocket;
import net.argus.event.socket.SocketEvent;
import net.argus.event.socket.SocketListener;
import net.argus.exception.SecurityException;
import net.argus.security.Key;
import net.argus.server.role.Role;
import net.argus.server.role.Roles;
import net.argus.util.ErrorCode;
import net.argus.util.debug.Debug;
import net.argus.util.pack.Package;
import net.argus.util.pack.PackageBuilder;
import net.argus.util.pack.PackageObject;
import net.argus.util.pack.PackagePareser;
import net.argus.util.pack.PackageType;

public class ServerSocketClient {
	
	private EventSocket event;
	
	private Socket socket;
	private Server server;
	
	private Key key;
	
	private ProcessServer process;
	
	private BufferedReader msgRecei;
	private PrintWriter msgSend;
	
	private Role role = Roles.DEFAULT;
	
	private String pseudo = "";
	
	private boolean clientUseKey = false;
	
	private int userId;
	
	public ServerSocketClient(Server server, Socket socket, Key key, int userId) throws SecurityException, IOException, IllegalAccessException {
		this.server = server;
		this.socket = socket;
		this.userId = userId;
		this.key = key;
		
		event = new EventSocket();
		
		process = new ProcessServer(this, userId);
		msgRecei = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		msgSend = new PrintWriter(socket.getOutputStream());

		init();
		
		boolean valid = true;
		
		String msg = "You are connected";
		ErrorCode code = null;
		
		if(Users.isBanned(socket)) {
			valid = false;
			code = ErrorCode.ban;
		}
		
		if(clientUseKey != (key!=null)) {
			valid = false;
			code = ErrorCode.crypt;
		}
		
		if((Users.getClientConnected() + 1) > Users.getMaxClient()) {
			valid = false;
			code = ErrorCode.full;
				
		}
		
		sendConnectionPackage(valid, msg, code);
		if(valid)
			process.start();
		
	}
	
	public ServerSocketClient(Server server, Socket socket, int userId) throws SecurityException, IOException, IllegalAccessException {
		this(server, socket, null, userId);
	}
	
	public synchronized void init() {
		clientUseKey = isClientUseKey();
		msgSend.println(key!=null);
		msgSend.flush();	
	}
	
	public synchronized void sendConnectionPackage(boolean connection, String message, ErrorCode code) throws SecurityException, IOException {
		PackageType type = connection?PackageType.CONNECTION:PackageType.UNCONNECTION;
		
		PackageBuilder bui = new PackageBuilder(type);
		bui.addValue("message", message);
		if(code != null)
			bui.addValue("code", String.valueOf(code.getCode()));

		sendPackage(new Package(bui));
		
		if(type == PackageType.UNCONNECTION) {
			event.startEvent(EventSocket.ERROR_CONNECTION, new SocketEvent(code, getIpClient(), socket.getPort()));
			logOut(code);
		}else 
			event.startEvent(EventSocket.CONNECT, new SocketEvent(socket, getIpClient(), socket.getPort()));
		
	}
	
	public synchronized void sendPackage(Package pack) throws SecurityException {
		send(pack);
	}
	
	public synchronized void sendArray(PackageType contentArray, String[] array) throws SecurityException {sendArray(contentArray.getId(), array);}
	
	public synchronized void sendArray(int contentArray, String[] array) throws SecurityException {
		PackageBuilder bui = new PackageBuilder(PackageType.ARRAY.getId());
		PackageObject objArray = new PackageObject("value");
		
		objArray.addItem("type", String.valueOf(contentArray));
		objArray.addItemArray("array", array);
		
		bui.addValue(objArray);

		sendPackage(new Package(bui));
	}
	
	public synchronized void send(Object str) {
		msgSend.println(clientUseKey&&key!=null?key.crypt(str.toString()):str.toString());
		msgSend.flush();
	}
	
	@SuppressWarnings("deprecation")
	public Package nextPackage() throws SecurityException {
		String n = nextString();
		
		if(n != null)
			return PackagePareser.parse(n);
		
		Thread.currentThread().stop();
		return Package.getLeavePackage();
	}
	
	private String nextString() throws SecurityException {
		String msg = null;
		
		try{msg = msgRecei.readLine();}
		catch(IOException e) {return null;}
		
		return clientUseKey&&key!=null?!msg.equals("")?key.decrypt(msg):msg:msg;
	}
	
	public synchronized void close(String msg) throws IOException, SecurityException {
		process.sendMessageToAllCLient(PackageType.SYSTEM.getId(), getPseudo() + " just disconnected");
		process.setRunning(false);
		
		msgSend.close();
		msgRecei.close();
		socket.close();
		
		event.startEvent(EventSocket.DISCONNECT, new SocketEvent(msg, getIpClient(), socket.getPort()));
		
		Debug.log("Kicked argument: " + msg);
		Debug.log(getPseudo() + " is kicked");
		
		if(userId != -1)
			Users.getServerSocketClient()[userId] = null;
	}
	
	public synchronized void logOut(String msg, ErrorCode code) throws IOException, SecurityException {
		sendPackage(new Package(new PackageBuilder(PackageType.LOG_OUT.getId()).addValue("code", String.valueOf(code.getCode())).addValue("message", msg)));
		Debug.log("Request of Log Out sended to " + getPseudo());
		close(msg);
	}
	
	public synchronized void logOut(ErrorCode code) throws IOException, SecurityException {
		logOut("", code);
	}
	
	public synchronized boolean isClientUseKey() throws NullPointerException {
		try {return Boolean.valueOf(msgRecei.readLine());}
		catch(IOException e) {throw new NullPointerException();}
	}
	
	public void addSocketListener(SocketListener listener) {event.addListener(listener);}
	
	public String getPseudo() {return pseudo;}
	
	public synchronized int getUserId() {return userId;}
	public synchronized Server getServerParent() {return server;}
	public synchronized ProcessServer getProcessServer() {return process;}
	public synchronized Role getRole() {return role;}
	
	public synchronized String getIpClient() {return socket.getInetAddress().getHostAddress();}
	
	public synchronized boolean isUseKey() {return key!=null?true:false;}
	
	public synchronized void setRole(Role role) {
		this.role = role;
		Debug.log("Role Chaged to: " + role.getName());
	}
	
	public synchronized void setPseudo(String pseudo) {this.pseudo = pseudo;}

}
