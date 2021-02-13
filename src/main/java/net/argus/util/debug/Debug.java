package net.argus.util.debug;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.argus.file.Loggeur;
import net.argus.system.InitializationSystem;
import net.argus.system.UserSystem;
import net.argus.util.ThreadManager;

public class Debug {
	
	private static List<String> blackList = new ArrayList<String>();
	private static Loggeur logger;
	
	private static boolean enableBlackList = true;
	private static boolean init;
	
	public static void log(Object text) {
		try {
			if(init) {
				verify(text);
			}else {
				init = true;
				verify(text);
			}
		}catch(IOException e) {e.printStackTrace();}
	}
	
	private static void verify(Object text) throws IOException {
		if(!enableBlackList) print(text);
		else if(!blackList.contains(Thread.currentThread().getName().toUpperCase())) print(text);
	}
	
	private static void print(Object text) throws IOException {
		String prefix =  "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + "[thread/" + Thread.currentThread().getName().toUpperCase() + "]: ";
		
		if(InitializationSystem.isSystemInitialized() && UserSystem.getBooleanProperty("log"))
			if(logger != null)
				logger.addLog(prefix + text);
			else if(UserSystem.log != null)
				UserSystem.log.addLog(prefix + text);
		
		System.out.println(prefix + text);
	}
	
	public static boolean isInitialized() {return init;}
	
	public static void setEnableBlackList(boolean enable) {Debug.enableBlackList = enable;}
	public static void setInitialized(boolean init) {Debug.init = init;}
	public static void setLoggeur(Loggeur loggeur) {Debug.logger = loggeur;}
	
	public static void addBlackList(String s) {blackList.add(s.toUpperCase());}
	public static void addBlackList(Thread t) {addBlackList(t.getName());}
	public static void addBlackList(ThreadManager t) {addBlackList(t.getName());}
	
	public static void removeBlackList(String s) {blackList.remove(s);}
	public static void removeLoggeur() {Debug.logger = null;}
	
}
