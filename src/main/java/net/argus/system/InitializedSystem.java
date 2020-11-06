package net.argus.system;

public class InitializedSystem {
	
	private static boolean init;
	
	public static void initSystem(String[]args, InitializedSystemManager manager) {
		manager.preInit(args);
		
		System.setProperty("project.name", Argument.getArgument(args, "project.name"));
		System.setProperty("id", Argument.getArgument(args, "id"));
		System.setProperty("arch", System.getProperty("os.arch").substring(3));
		manager.init(args);
		
		init = true;
		manager.postInit(args);
	}
	
	public static boolean isSystemInitialized() {return init;}

	
}
