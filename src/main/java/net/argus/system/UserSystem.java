package net.argus.system;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import net.argus.cjson.CJSON;
import net.argus.cjson.CJSONParser;
import net.argus.file.CJSONFile;
import net.argus.file.FileLogger;
import net.argus.file.FileManager;
import net.argus.instance.Instance;
import net.argus.lang.DefaultLangValue;
import net.argus.util.ListenerManager;
import net.argus.util.RunTime;
import net.argus.util.ThreadManager;
import net.argus.util.Version;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Logger;

public class UserSystem {
	
	public static Logger log;
	private static AutoUpdate update;
	
	private static CJSON manifest;
		
	public final static RunTime runTime = RunTime.getRunTime();
	public final static Scanner in = new Scanner(System.in);
	//public final static Notify notify = new DefaultNotify();
	
	
	/**--INITIALIZATION--**/
	private static InitializedSystemManager manager = new InitializedSystemManager() {
		public void preInit(String[] args) {
			runTime.start();
			
			DefaultLangValue.applyDefault();
			
			Debug.addBlackList(ThreadManager.UPDATE_UI);
		}
		
		public void init(String[] args) {
			if(getBooleanProperty("log") && log == null) {
				log = new FileLogger("log");
				Debug.addLoggeur(log);
			}
			
			CJSONFile man = new CJSONFile("manifest", "/", Instance.SYSTEM);
			if(man.exists()) {
				manifest = CJSONParser.getCJSON(man);
				
				if(getBooleanProperty("update"))
					if(Network.isConnected())
						update = new AutoUpdate();
			}
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
		
		if(OS.currentOS() == OS.LINUX)
			extention = LIBRARY_LINUX;
		
		String libFile = name + System.getProperty("os.arch").substring(3) + "." + extention;
		
		try {
			String lib = getLibrary(libFile);
			
			if(lib != null)
				System.load(lib);
		}catch(IOException e) {return;}
		
		Debug.log("Library " + libFile + " loaded");
		ThreadManager.restorOldParameter(0);
	}
	
	private static String getLibrary(String name) throws IOException {
		String[] paths = getProperty("java.library.path").split(Launcher.getSeperator());
		
		File file = null;
		for(String path : paths) {
			file = new File(new File(path).getCanonicalPath() + "/" + name);
			if(file.exists())
				return file.getCanonicalPath();
		}
		
		return null;
	}
	
	public static Version getCurrentVersion() {
		return new Version(manifest.getString("manifest.version"));
	}
	
	public static Version getCurrentDebug() {
		return new Version(manifest.getString("manifest.debug"));
	}
	
	public static String getCurrentStringVersion() {
		return getCurrentVersion() + "." + getCurrentDebug();
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
	
	public static AutoUpdate getUpdate() {return update;}
	public static CJSON getManifest() {return manifest;}
	
	public static void exit(int status) {
		FileManager.delete(Temp.getTempDir() + "/");
		Debug.log("Program run in " + runTime.stop() + " milliseconde");
		System.exit(status);
	}
	
	public static void exit(ExitCode status) {
		exit(status.getCode());
	}

}
