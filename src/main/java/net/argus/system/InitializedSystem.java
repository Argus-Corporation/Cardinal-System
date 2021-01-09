package net.argus.system;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InitializedSystem {
	
	public static boolean init;
	
	public static void initSystem(String[] args, InitializedSystemManager manager) {
		manager.preInit(args);
		
		for(int i = 0; i < args.length; i += 2) 
			System.setProperty(args[i].substring(1), Argument.getArgument(args, args[i].substring(1)));
			
		try {System.setProperty("user", InetAddress.getLocalHost().getHostName());}
		catch(UnknownHostException e) {e.printStackTrace();}
		
		System.setProperty("arch", System.getProperty("os.arch").substring(3));
		manager.init(args);
		
		init = true;
		manager.postInit(args);
	}
	
	public static boolean isSystemInitialized() {return init;}

	
}
