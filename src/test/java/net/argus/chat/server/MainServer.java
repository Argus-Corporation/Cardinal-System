package net.argus.chat.server;

import java.io.IOException;

import net.argus.security.Key;
import net.argus.server.Server;
import net.argus.server.role.Role;
import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;
import net.argus.util.CloseListener;

public class MainServer {
	
	private Server serv, servCrypt;
	
	public static int PORT = 11066; 
	public static int MAX = 100;
	
	private static String PASSWORD = "rt";
	
	public MainServer() throws IOException {
		init();
		
		serv = new Server(MAX, PORT);
		servCrypt = new Server(MAX, PORT + 1, new Key("$^ù**^$ùm$ùmefsd^mù6548#5{DSG3d47g4354j4ù4$*84mi1olukjhgf85j#[|'(--è_k45"));
		
		serv.addClostListener(getServerCloseListener());
		servCrypt.addClostListener(getServerCryptCloseListener());
		
		new Role("admin").setPassword(PASSWORD).setRank(10).registry();
		
		serv.start();
		servCrypt.start();
	}
	
	private void init() {
		int port = UserSystem.getIntegerProperty("port");
		int max = UserSystem.getIntegerProperty("max");
		
		String password = UserSystem.getProperty("password");
		
		PORT = port!=-1?port:PORT;
		MAX = max!=-1?max:MAX;
		
		PASSWORD = password!=null?password:PASSWORD;
	}
	
	private CloseListener getServerCloseListener() {
		return () -> servCrypt.stop(0);
	}
	
	private CloseListener getServerCryptCloseListener() {
		return () -> serv.stop(0);
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		InitializedSystem.initSystem(args, false);
		Thread.currentThread().setName("SERVER");

		new MainServer();
	}

}
