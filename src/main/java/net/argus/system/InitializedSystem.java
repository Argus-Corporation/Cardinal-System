package net.argus.system;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InitializedSystem {
	
	public static boolean init;
	
	public static void initSystem(String[]args, InitializedSystemManager manager) {
		manager.preInit(args);
		System.setProperty("project.name", Argument.getArgument(args, "project.name"));
		
		try {System.setProperty("user", InetAddress.getLocalHost().getHostName());}
		catch(UnknownHostException e) {e.printStackTrace();}
		
		System.setProperty("arch", System.getProperty("os.arch").substring(3));
		System.setProperty("id", Argument.getArgument(args, "id"));
		manager.init(args);
		
		init = true;
		manager.postInit(args);
	}
	
	public static boolean isSystemInitialized() {return init;}

	
}
