package net.argus.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import net.argus.exception.SecurityException;
import net.argus.security.Key;
import net.argus.util.ArrayManager;
import net.argus.util.Package;
import net.argus.util.PackageType;
import net.argus.util.debug.Debug;

public class SocketClient {
	
	private Socket socket;
	private BufferedReader msgRecei;
	private PrintWriter msgSend;
	
	private String host;
	private int port;
	
	private Key key = null;
	
	private boolean serverUseKey = false;
	private boolean connected;
	
	private String pseudo;
	private String password;
	
	public SocketClient(String host, int port, Key key) throws IOException {
		this.host = host;
		this.port = port;
		
		this.key = key;
	}
	
	public SocketClient(String host, int port) throws IOException {
		this.host = host;
		this.port = port;
	}
	
	public void connect() throws UnknownHostException, IOException {
		socket = new Socket(host, port);
		Debug.log("Connected to " + host);
		
		connected = true;
		
		msgRecei = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		msgSend = new PrintWriter(socket.getOutputStream());
	}
	
	public void init() throws IOException, SecurityException {
		msgSend.println(key!=null);
		msgSend.flush();
		
		serverUseKey = Boolean.valueOf(msgRecei.readLine());
	}
	
	public void sendPackage(Package pack) {
		if(ArrayManager.isExist(pack.getMessage().toCharArray(), 0) && pack.getMessage().toCharArray()[0] == '/') pack.setType(PackageType.COMMANDE);
		
		msgSend.println(serverUseKey&&key!=null?key.crypt(Integer.toString(pack.getType())):pack.getType());
		msgSend.flush();
		
		msgSend.println(serverUseKey&&key!=null?!pack.getMessage().equals("")?key.crypt(pack.getMessage()):pack.getMessage():pack.getMessage());
		msgSend.flush();
	}
	
	public void close(String msg) throws IOException {
		msgSend.close();
		msgRecei.close();
		socket.close();
		connected = false;
		
		Debug.log("You are disconnected: " + msg);
	}
	
	private int receiveIdPackage() throws SecurityException {
		try{return Integer.valueOf(receiveMessage());}
		catch(NumberFormatException e) {return -2;}
	}
	
	public Package receivePackage() throws SecurityException{
		Package pack = new Package();
		
		pack.setType(receiveIdPackage());
		pack.setMessage(receiveMessage());
		
		return pack;
	}
	
	private String receiveMessage() throws SecurityException {
		String msg = null;
		
		try{msg = msgRecei.readLine();}
		catch(IOException e) {return null;}
		
		return serverUseKey&&key!=null?!msg.equals("")?key.decrypt(msg):msg:msg;
	}
	
	public String[] receiveArray() throws SecurityException {
		int length = Integer.valueOf(receiveMessage());
		String[] array = new String[length];
		
		for(int i = 0; i < length; i++)
			array[i] = receiveMessage();
		
		return array;
	}
	
	public String getPseudo() {return pseudo;}
	public String getPassword() {return password;}
	
	public boolean isUseKey() {return key!=null;}
	public boolean isServerUseKey() {return serverUseKey;}
	public boolean isConnected() {return connected;}
	
	public SocketClient setKey(Key key) {this.key = key; return this;}
	public SocketClient setPseudo(String pseudo) {this.pseudo = pseudo; return this;}
	public SocketClient setPassword(String password) {this.password = password; return this;}

}
