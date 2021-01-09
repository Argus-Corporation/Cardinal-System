package net.argus.example;

import java.io.IOException;

import net.argus.server.Server;
import net.argus.server.role.Role;
import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;

public class ExampleServer {
	
	public static final int PORT = 11066; 
	public static final int MAX = 100;
	
	public ExampleServer() throws IOException {
		int max = Integer.valueOf(System.getProperty("max"));
		int port = Integer.valueOf(System.getProperty("port"));
		
		Server serv = new Server(max!=-1?max:MAX, port!=-1?port:PORT);
		
		new Role("admin").setPassword("rt").setRank(10).registry();
		
		serv.start();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		InitializedSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		new ExampleServer();
	}

}
