package net.argus.system;

import net.argus.file.Loggeur;
import net.argus.util.RunTime;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;

public class UserSystem {
	
	public static Loggeur log;
	public static RunTime runTime = RunTime.getRunTime();
	
	private static InitializedSystemManager manager = new InitializedSystemManager() {
		public void preInit(String[] args) {runTime.start(); ThreadManager.SYSTEM.setTemporaryName();}
		public void init(String[] args) {log = new Loggeur("log");}
		public void postInit(String[] args) {Debug.log("System initialized"); ThreadManager.SYSTEM.restorOldParameter();}
	};
	
	public static InitializedSystemManager getDefaultInitializedSystemManager() {return manager;}
	
	public static void setDefaultInitializedSystemManager(InitializedSystemManager manager) {UserSystem.manager = manager;}
	
	public static void loadLibrary(String name) {System.loadLibrary("natives/" + name + System.getProperty("os.arch").substring(3));}
	
	public static void exit(int status) {
		Debug.log("Program run in " + runTime.stop() + " milliseconde");
		System.exit(status);
	}

}
