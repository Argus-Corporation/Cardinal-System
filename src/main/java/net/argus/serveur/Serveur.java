package net.argus.serveur;

import java.io.IOException;
import java.net.ServerSocket;

import net.argus.security.Key;
import net.argus.serveur.role.Role;
import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;
import net.argus.exception.SecurityException;;

public class Serveur extends Thread {
	
	private ServerSocket serveur;
	private Users users;
	
	private boolean running;
	
	private static final int SERVEUR_VERSION = 011220;
	
	private int port;
	
	public Serveur(int maxClient, int port, Key key) throws IOException {
		ThreadManager.addThread(this);
		this.port = port;
		
		serveur = new ServerSocket(port);
		Debug.log("Serveur version: " + SERVEUR_VERSION);
		Debug.log("Server launched");
		
		users = new Users(maxClient, key);
	}
	
	public Serveur(int maxClient, int port) throws IOException {
		ThreadManager.addThread(this);
		this.port = port;
		
		serveur = new ServerSocket(port);
		Debug.log("Serveur version: " + SERVEUR_VERSION);
		Debug.log("Server launched");
		
		users = new Users(maxClient);
	}
	
	public void run() {
		setName("SERVEUR");
		running = true;
		loop();
		
	}
	
	private void loop() {
		try {
			while(running)
				users.addUser(this, serveur.accept());
			
		}catch(IOException e) {
			Debug.log("Error: IOExeption");
		}catch(SecurityException e) {
			Debug.log("Error: SecurityExeption");
		}
		Debug.log("Server Stopped");
	}
	
	public void stop(int userId) throws IOException, SecurityException {
		running = false;
		users.closeAll(userId);
		serveur.close();
		exit();
	}
	
	private void exit() {
		Debug.log("Server Stopped");
		ThreadManager.stop(this);
		ThreadManager.stop(currentThread());
	}
	
	public int getVersion() {return SERVEUR_VERSION;}
	
	public boolean isRunning() {return running;}
	
	public int getPort() {return port;}
	public Users getUsers() {return users;}
	public ServerSocket getServeurSocket() {return serveur;}
	
		
	public static void main(String[] args) throws IOException {
		InitializedSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
	//	Key key = new Key("$^ù$$;mm^$^dmsf$^sdµdPµ^mm$µMPµ;p:,$^;m:$^,;:877687^$ù*%µMPµ%m");
		
		Serveur serveur = new Serveur(10, 11066);
		
		new Role("admin").setPassword("re").setRank(10).registry();
		new Role("admin").setPassword("r5e").setRank(1000).registry();
		
		serveur.start();
	}

}
