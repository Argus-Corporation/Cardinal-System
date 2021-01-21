package net.argus.system;

<<<<<<< Updated upstream
public class InitializedSystem {
	
	private static boolean init;
	
	public static void initSystem(String[]args, InitializedSystemManager manager) {
		manager.preInit(args);
=======
import java.net.InetAddress;
import java.net.UnknownHostException;

import net.argus.util.ListenerManager;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;

public class InitializedSystem {
	
	private static boolean init;
	
	private static ListenerManager<InitializedUI> uiManager = new ListenerManager<InitializedUI>();
	
	public static void initSystem(String[] args) {
		initSystem(args, null);
	}
	
	public static void initSystem(String[] args, boolean ui) {
		initSystem(args, ui, null);
	}
	
	public static void initSystem(String[] args, InitializedSystemManager manager) {
		initSystem(args, true, manager);
	}
	
	public static void initSystem(String[] args, boolean ui, InitializedSystemManager manager) {
		InitializedSystemManager systemManager = UserSystem.getDefaultInitializedSystemManager();
		addInitializedUI(new InitializedUI());
		
		ThreadManager.SYSTEM.setTemporaryName();
		
		UserSystem.defineProperty("info", true);
		
		preInit(args);
		if(ui) preInitUi(args);
		if(systemManager != null) systemManager.preInit(args);
		if(manager != null) manager.preInit(args);
>>>>>>> Stashed changes
		
		System.setProperty("project.name", Argument.getArgument(args, "project.name"));
		System.setProperty("id", Argument.getArgument(args, "id"));
		System.setProperty("arch", System.getProperty("os.arch").substring(3));
		manager.init(args);
		
<<<<<<< Updated upstream
		init = true;
		manager.postInit(args);
	}
	
=======
		postInit(args);
		if(ui) postInitUi(args);
		if(systemManager != null) systemManager.postInit(args);
		if(manager != null) manager.postInit(args);
		
		ThreadManager.SYSTEM.restorOldParameter();
	}
	
	private static void preInit(String[] args) {
		for(int i = 0; i < args.length; i += 2) 
			System.setProperty(args[i].substring(1), Argument.getArgument(args, args[i].substring(1)));
		
		if(UserSystem.getBooleanProperty("info"))
			showInfo();
	}
	
	private static void init(String[] args) {
			
		try {UserSystem.setProperty("user", InetAddress.getLocalHost().getHostName());}
		catch(UnknownHostException e) {e.printStackTrace();}
		
		UserSystem.setProperty("arch", System.getProperty("os.arch").substring(3));
		UserSystem.defineProperty("log", true);
	}
	
	private static void postInit(String[] args) {
		init = true;
		if(UserSystem.getBooleanProperty("update"))
			if(UserSystem.update != null)
				UserSystem.update.check();
		
		Debug.log("System initialized");
	}
	
	private static void preInitUi(String[] args) {
		for(InitializedUI ui : uiManager)
			ui.preInit(args);
	}
	
	private static void initUi(String[] args) {
		for(InitializedUI ui : uiManager)
			ui.init(args);
	}
	
	private static void postInitUi(String[] args) {
		for(InitializedUI ui : uiManager)
			ui.postInit(args);
	}
	
	private static void showInfo() {
		Debug.log("OS: " + UserSystem.getProperty("os.name"));
		Debug.log("Arch: " + UserSystem.getProperty("os.arch"));
		Debug.log("Network: " + (Network.isConnected()?"connected":"unconnected"));
		
	}
	
	public static void addInitializedUI(InitializedUI ui) {uiManager.addListener(ui);}
	
>>>>>>> Stashed changes
	public static boolean isSystemInitialized() {return init;}

	
}
