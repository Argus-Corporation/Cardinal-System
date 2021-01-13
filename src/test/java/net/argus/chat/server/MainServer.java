package net.argus.chat.server;

import java.io.IOException;

import net.argus.server.Server;
import net.argus.server.role.Role;
import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;

public class MainServer {
	
	public static int PORT = 11066; 
	public static int MAX = 100;
	
	private static String PASSWORD = "rt";
	
	public MainServer() throws IOException {
		init();
		
		Server serv = new Server(MAX, PORT);
		
		new Role("admin").setPassword(PASSWORD).setRank(10).registry();
		
		serv.start();
	}
	
	public void init() {
		int port = UserSystem.getIntegerProperty("port");
		int max = UserSystem.getIntegerProperty("max");
		
		String password = UserSystem.getProperty("password");
		
		PORT = port!=-1?port:PORT;
		MAX = max!=-1?max:MAX;
		
		PASSWORD = password!=null?password:PASSWORD;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		InitializedSystem.initSystem(args, false);
		
		Thread.currentThread().setName("SERVER");

		new MainServer();
		
	}

}
