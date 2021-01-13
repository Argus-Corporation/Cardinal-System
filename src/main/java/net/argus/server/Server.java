package net.argus.server;

import java.io.IOException;
import java.net.ServerSocket;

import net.argus.exception.SecurityException;
import net.argus.security.Key;
import net.argus.server.role.Role;
import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;;

public class Server extends Thread {
	
	private ServerSocket server;
	private Users users;
	
	private boolean running;
	
	private static final int SERVER_VERSION = 130121100;
	
	private int port;
	
	public Server(int maxClient, int port, Key key) throws IOException {
		ThreadManager.addThread(this);
		this.port = port;
		
		server = new ServerSocket(port);
		Debug.log("Server version: " + SERVER_VERSION);
		Debug.log("Server port: " + port);
		Debug.log("Server slot: " + maxClient);
		Debug.log("Server launched");
		
		users = new Users(maxClient, key);
	}
	
	public Server(int maxClient, int port) throws IOException {
		this(maxClient, port, null);
	}
	
	public Server(String maxClient, String port) throws IOException {
		this(Integer.valueOf(maxClient), Integer.valueOf(port));
	}
	
	public void run() {
		setName("SERVER");
		running = true;
		
		loop();
	}
	
	private void loop() {
		try {
			while(running)
				users.addUser(this, server.accept());
			
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
		server.close();
		exit();
	}
	
	private void exit() {
		Debug.log("Server Stopped");
		ThreadManager.stop(this);
		ThreadManager.stop(currentThread());
	}
	
	public int getVersion() {return SERVER_VERSION;}
	
	public boolean isRunning() {return running;}
	
	public int getPort() {return port;}
	public Users getUsers() {return users;}
	public ServerSocket getServerSocket() {return server;}
	
		
	public static void main(String[] args) throws IOException {
		InitializedSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
	//	Key key = new Key("$^ù$$;mm^$^dmsf$^sdµdPµ^mm$µMPµ;p:,$^;m:$^,;:877687^$ù*%µMPµ%m");
		
		Server server = new Server(10, 11066);
		
		new Role("admin").setPassword("re").setRank(10).registry();
		new Role("admin").setPassword("r5e").setRank(1000).registry();
		
		server.start();
	}

}
