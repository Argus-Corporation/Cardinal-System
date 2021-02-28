package net.argus.system;

import java.util.Scanner;

import net.argus.file.Loggeur;
import net.argus.file.cjson.CJSONFile;
import net.argus.system.update.AutoUpdate;
import net.argus.util.ListenerManager;
import net.argus.util.RunTime;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;
import net.argus.util.notify.DefaultNotify;
import net.argus.util.notify.Notify;

public class UserSystem {
	
	public static Loggeur log;
	public static AutoUpdate update;
	
	public static RunTime runTime = RunTime.getRunTime();
	public static Scanner in = new Scanner(System.in);
	public static Notify notify = new DefaultNotify();
	
	
	/**--INITIALIZATION--**/
	private static InitializedSystemManager manager = new InitializedSystemManager() {
		public void preInit(String[] args) {
			runTime.start();
			
			Debug.addBlackList(ThreadManager.UPDATE_UI);
		}
		
		public void init(String[] args) {
			
			if(getBooleanProperty("log"))
				log = new Loggeur("log");
			
			if(getBooleanProperty("update"))
				if(Network.isConnected())
					update = new AutoUpdate(new CJSONFile("manifest", "/"));
				
		}
		
		public void postInit(String[] args) {}
	};
	
	public static InitializedSystemManager getDefaultInitializedSystemManager() {return manager;}
	public static void setDefaultInitializedSystemManager(InitializedSystemManager manager) {UserSystem.manager = manager;}
	
	
	/**--SYSTEM LISTENER--**/
	private static ListenerManager<SystemListener> systemManager = new ListenerManager<SystemListener>();
	
	public static void addSystemListener(SystemListener listener) {
		systemManager.addListener(listener);
	}
	
	public static void systemEventInit(SystemEvent e) {
		for(SystemListener lis : systemManager)
			lis.systemInit(e);
	}
	
	
	/**--LIBRARY--**/
	public static final String LIBRARY_WINDOWS = "dll";
	public static final String LIBRARY_LINUX = "so";
	
	public static void loadLibrary(String name) {
		ThreadManager.setTemporaryName(ThreadManager.SYSTEM.getName());
		
		String extention = LIBRARY_WINDOWS;
		
		if(OS.getOS() == OS.LINUX)
			extention = LIBRARY_LINUX;
		
		String libFile = name + System.getProperty("os.arch").substring(3) + "." + extention;
		
		System.load(System.getProperty("java.library.path") + "/natives/" + name + System.getProperty("os.arch").substring(3) + "." + extention);
		
		Debug.log("Library " + libFile + " loaded");
		ThreadManager.restorOldParameter(0);
	}
	
	
	/**--PROPERTY--**/
	public static String getProperty(String key) {
		return System.getProperty(key);
	}
	
	public static boolean getBooleanProperty(String key) {
		return Boolean.valueOf(getProperty(key));
	}
	
	public static int getIntegerProperty(String key) {
		try {
			return Integer.valueOf(getProperty(key));
		}catch(NumberFormatException e) {
			return -1;
		}
	}
	
	public static void setProperty(String key, String value) {
		System.setProperty(key, value);
	}
	
	public static void setProperty(String key, boolean value) {
		setProperty(key, Boolean.toString(value));
	}
	
	public static void setProperty(String key, int value) {
		setProperty(key, Integer.toString(value));
	}
	
	public static void defineProperty(String key, String value) {
		if(getProperty(key) == null) 
			setProperty(key, value);
	}
	
	public static void defineProperty(String key, boolean value) {
		if(getProperty(key) == null) 
			setProperty(key, value);
	}
	
	public static void defineProperty(String key, int value) {
		if(getProperty(key) == null) 
			setProperty(key, value);
	}
	
	public static void exit(int status) {
		Debug.log("Program run in " + runTime.stop() + " milliseconde");
		System.exit(status);
	}

}
