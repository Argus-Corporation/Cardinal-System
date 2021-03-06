package net.argus.util.debug;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.argus.util.ThreadManager;

public class Debug {
	
	private static List<String> blackList = new ArrayList<String>();
	private static List<Logger> loggers = new ArrayList<Logger>();
	
	private static boolean enableBlackList = true;
	private static boolean enable = true;
	
	public static void log(Object text) {
		log(text, Info.INFO);
	}
	
	public static void log(Object text, Info info) {
		verify(text, info);
	}
	
	private static void verify(Object text, Info info) {
		if(enable && !enableBlackList) print(text, info);
		else if(enable && !blackList.contains(Thread.currentThread().getName().toUpperCase())) print(text, info);
	}
	
	private static synchronized void print(Object text, Info info) {
		String prefix =  "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + "[" + Thread.currentThread().getName() + "/" + info + "]: ";
		for(Logger logger : loggers)
			if(info == Info.INFO)
				logger.log(prefix + text);
			else
				logger.errorLog(prefix + text);

	}
	
	public static void setEnableBlackList(boolean enable) {Debug.enableBlackList = enable;}
	public static void setEnable(boolean enable) {Debug.enable = enable;}
	public static void addLoggeur(Logger logger) {Debug.loggers.add(logger);}
	
	public static void addBlackList(String s) {blackList.add(s.toUpperCase());}
	public static void addBlackList(Thread t) {addBlackList(t.getName());}
	public static void addBlackList(ThreadManager t) {addBlackList(t.getName());}
	
	public static void removeBlackList(String s) {blackList.remove(s);}
	public static void removeLogger(int index) {Debug.loggers.remove(index);}
	
	public static boolean isEnabled() {return enable;}
	public static boolean isEnabledBlacklist() {return enableBlackList;}
	
	public static void print(List<?> list) {
		for(Object c : list)
			System.out.print(c);
		System.out.println();
	}
	
	public static void print(List<?> list, char sep) {
		for(Object c : list)
			System.out.print(c + "" + sep);
		System.out.println();
	}
	
	public static void print(Object[] array) {
		for(Object c : array)
			System.out.print(c);
		System.out.println();
	}
	
	public static void println(List<?> list) {
		for(Object c : list)
			System.out.println(c);
	}
	
	public static void println(Object[] array) {
		for(Object c : array)
			System.out.println(c);
	}
	
	static {
		addLoggeur(new PrintLogger(System.out, System.err));
	}
	
}
