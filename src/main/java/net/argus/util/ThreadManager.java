package net.argus.util;

import static java.lang.Thread.*;

import java.util.ArrayList;
import java.util.List;

public class ThreadManager {
	
	public static final ThreadManager PROGRESSE = new ThreadManager("progresse");
	public static final ThreadManager SYSTEM = new ThreadManager("system");
	
	public static List<Thread> threads = new ArrayList<Thread>();
	
	private static String oldNameStatic;
	
	private String name;
	private String oldName;
	
	public ThreadManager(String name) {this.name = name;}
	
	public void setTemporaryName() {oldName = currentThread().getName(); currentThread().setName(name);}
	public void restorOldParameter() {currentThread().setName(oldName);}
	
	public static void setTemporaryName(String name) {oldNameStatic = currentThread().getName(); currentThread().setName(name);}
	public static void restorOldParameter(int nul) {currentThread().setName(oldNameStatic);}
	
	public static void addThread(Thread thread) {threads.add(thread);}
	
	public static Thread getThread(String name) {
		for(int i = 0; i < threads.size(); i++) {
			if(threads.get(i).getName().equals(name)) {
				return threads.get(i);
			}
		}
		return null;
	}
	
	public static Thread getThread(Thread thread) {
		for(int i = 0; i < threads.size(); i++) {
			if(threads.get(i).equals(thread)) {
				return threads.get(i);
			}
		}
		return null;
	}
	
	public String getName() {return name;}
	
	@SuppressWarnings("deprecation")
	public static void stop(Thread thread) {
		threads.remove(thread);
		thread.stop();
	}
	
	public static int getTheadNumber() {return threads.size();}
	
}
