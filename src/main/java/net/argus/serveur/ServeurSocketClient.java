package net.argus.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import net.argus.exception.SecurityException;
import net.argus.security.Key;
import net.argus.serveur.role.Role;
import net.argus.serveur.role.Roles;
import net.argus.util.debug.Debug;
import net.argus.util.pack.Package;
import net.argus.util.pack.PackageBuilder;
import net.argus.util.pack.PackageObject;
import net.argus.util.pack.PackagePareser;
import net.argus.util.pack.PackageType;

public class ServeurSocketClient {
	
	private Socket socket;
	private Serveur serveur;
	
	private Key key;
	
	private ProcessServeur process;
	
	private BufferedReader msgRecei;
	private PrintWriter msgSend;
	
	private Role role = Roles.DEFAULT;
	
	private String pseudo = "";
	
	private boolean clientUseKey = false;
	
	private int userId;
	
	public ServeurSocketClient(Serveur serveur, Socket socket, Key key, int userId) throws SecurityException, IOException, IllegalAccessException {
		this.serveur = serveur;
		this.socket = socket;
		this.userId = userId;
		this.key = key;
		
		process = new ProcessServeur(this, userId);
		msgRecei = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		msgSend = new PrintWriter(socket.getOutputStream());
		
		init();
		
		boolean valid = true;
		
		if(Users.isBanned(socket)) {
			valid = false;
			logOut("You are banned");
			throw new IllegalAccessException();
		}
		
		if((Users.getClientConnected() + 1) > Users.getMaxClient()) {
			valid = false;
			logOut("This serveur is full");
			throw new IllegalAccessException();
		}
		
		if(valid) process.start();
	}
	
	public ServeurSocketClient(Serveur serveur, Socket socket, int userId) throws SecurityException, IOException, IllegalAccessException {
		this.serveur = serveur;
		this.socket = socket;
		this.userId = userId;
		
		process = new ProcessServeur(this, userId);
		msgRecei = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		msgSend = new PrintWriter(socket.getOutputStream());
		
		init();
		
		boolean valid = true;
		
		if(Users.isBanned(socket)) {
			valid = false;
			logOut("You are banned");
			throw new IllegalAccessException();
		}
		//System.out.println((Users.getClientConnected() + 1) + " >  " + Users.getMaxClient());
		if((Users.getClientConnected() + 1) > Users.getMaxClient()) {
			valid = false;
			logOut("This serveur is full");
			throw new IllegalAccessException();
		}
		
		if(valid) process.start();
	}
	
	public synchronized void init() {
		isClientUseKey();
		msgSend.println(key!=null);
		msgSend.flush();	
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
	
	/*public synchronized void sendFile(Package packageFile) throws SecurityException {
		PackageBuilder bui = new PackageBuilder(PackageType.FILE.getId());
		PackageObject objFile = new PackageObject("value");
		
		objFile.addItem("file", file);
		
		bui.addValue(objFile);
		
		sendPackage(new Package(bui));
	}*/
	
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
		return null;
	}
	
	private String nextString() throws SecurityException {
		String msg = null;
		
		try{msg = msgRecei.readLine();}
		catch(IOException e) {return null;}
		
		return clientUseKey&&key!=null?!msg.equals("")?key.decrypt(msg):msg:msg;
	}
	
	public synchronized void close(String msg) throws IOException {
		process.setRunning(false);
		msgSend.close();
		msgRecei.close();
		socket.close();
		Debug.log("Kicked argument: " + msg);
		Debug.log(process.getPseudo() + " is kicked");
		Users.getServeurSocketClient()[userId] = null;
	}
	
	public synchronized void logOut(String msg) throws IOException, SecurityException {
		sendPackage(new Package(new PackageBuilder(PackageType.LOG_OUT.getId()).addValue("message", msg)));
		Debug.log("Request of Log Out sended to " + process.getPseudo());
		close(msg);
	}
	
	public synchronized void logOut(int status) throws IOException, SecurityException {
		logOut(status==-1?"error":"log out");
	}
	
	public synchronized boolean isClientUseKey() {
		try {clientUseKey = Boolean.valueOf(msgRecei.readLine()); return clientUseKey;}
		catch(IOException e) {return false;}
	}
	
	
	public synchronized String getPseudo() {return pseudo;}
	
	public synchronized int getUserId() {return userId;}
	public synchronized Serveur getServeurParent() {return serveur;}
	public synchronized ProcessServeur getProcessServeur() {return process;}
	public synchronized Role getRole() {return role;}
	
	public synchronized String getIp() {return socket.getInetAddress().toString();}
	
	public synchronized boolean isUseKey() {return key!=null?true:false;}
	
	public synchronized void setRole(Role role) {
		this.role = role;
		Debug.log("Role Chaged to: " + role.getName());
	}
	
	public synchronized void setPseudo(String pseudo) {this.pseudo = pseudo;}

}
