package net.argus.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import net.argus.event.socket.EventSocket;
import net.argus.event.socket.SocketEvent;
import net.argus.event.socket.SocketListener;
import net.argus.exception.SecurityException;
import net.argus.security.Key;
import net.argus.util.ErrorCode;
import net.argus.util.debug.Debug;
import net.argus.util.pack.Package;
import net.argus.util.pack.PackagePareser;

public class SocketClient {
	
	private EventSocket event;
	
	private Socket socket;
	private BufferedReader in;
	private PrintStream out;
	
	private String host;
	private int port;
	
	private Key key = null;
	
	private boolean serverUseKey = false;
	private boolean connected;
	
	private String pseudo = "Client";
	private String password;
	
	public SocketClient(String host, int port, Key key) {
		this.host = host;
		this.port = port;
		
		this.key = key;
		
		event = new EventSocket();
	}
	
	public SocketClient(String host, int port) throws IOException {
		this(host, port, null);
	}
	
	public void connect() throws UnknownHostException, IOException {
		try {socket = new Socket(host, port);}
		catch(IOException e) {
			event.startEvent(EventSocket.ERROR_CONNECTION, new SocketEvent(e, host, port));
			
			if(e instanceof UnknownHostException) throw new UnknownHostException();
			else throw new IOException();
		}
		
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintStream(socket.getOutputStream());
		
		connected = true;
		
		try {init();}
		catch(SecurityException e) {e.printStackTrace();}
		
		validConnection();
		Debug.log("Connected to " + host + ":" + port);
	}
	
	public void validConnection() {
		while(true) {
			try {
				Package pack = nextPackage();
				if(pack.getType() == ProcessClient.CONNECTION || pack.getType() == ProcessClient.UNCONNECTION) {
					ErrorCode code;
					
					if(pack.getType() != ProcessClient.CONNECTION) {
						code = ErrorCode.valueOf(Integer.valueOf(pack.getValue("code")));
						event.startEvent(EventSocket.ERROR_CONNECTION, new SocketEvent(code.getName(), host, port));
						return;
					}
					
					event.startEvent(EventSocket.CONNECT, new SocketEvent(socket, host, port));
					return;
				}
			}catch(SecurityException e) {}
		}
	}
	
	public void init() throws IOException, SecurityException {
		out.println(key!=null);
		out.flush();
		
		serverUseKey = Boolean.valueOf(in.readLine());
	}
	
	public void sendPackage(Package pack) {
		send(pack);
	}
	
	public synchronized void send(Object obj) {
		out.println(serverUseKey&&key!=null?key.crypt(obj.toString()):obj.toString());
		out.flush();
	}
	
	public void close(String msg) throws IOException {
		out.close();
		in.close();
		socket.close();
		connected = false;
		
		event.startEvent(EventSocket.DISCONNECT, new SocketEvent(msg, host, port));
		
		Debug.log("You are disconnected: " + msg);
	}
	
	public Package nextPackage() throws SecurityException {
		String n = nextString();

		if(n != null)
			return PackagePareser.parse(n);
		
		return Package.getLeavePackage();
	}
	
	private String nextString() throws SecurityException {
		String msg = null;
		
		try{msg = in.readLine();}
		catch(IOException e) {return null;}
		if(msg != null)
			return serverUseKey&&key!=null?!msg.equals("")?key.decrypt(msg):msg:msg;
		else 
			return null;
	}
	
	public void addSocketListener(SocketListener listener) {event.addListener(listener);}
	
	public String getPseudo() {return pseudo;}
	public String getPassword() {return password;}
	
	public boolean isUseKey() {return key!=null;}
	public boolean isServerUseKey() {return serverUseKey;}
	public boolean isConnected() {return connected;}
	
	public SocketClient setOutputStream(OutputStream out) {
		this.out = new PrintStream(out);
		return this;
	}
	
	public SocketClient setKey(Key key) {this.key = key; return this;}
	public SocketClient setPseudo(String pseudo) {
		if(pseudo != null && !pseudo.equals("")) this.pseudo = pseudo;
		return this;
	}
	
	public SocketClient setPassword(String password) {this.password = password; return this;}

}
