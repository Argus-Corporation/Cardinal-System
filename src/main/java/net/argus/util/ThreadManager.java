package net.argus.util;

import static java.lang.Thread.currentThread;

import java.util.ArrayList;
import java.util.List;

public class ThreadManager {
	
	public static List<Thread> threads = new ArrayList<Thread>();
	
	public static final ThreadManager THREAD_MANAGER = new ThreadManager("thread manager");
	
	public static final ThreadManager UPDATE_UI = new ThreadManager("update-ui");
	public static final ThreadManager PROGRESSE = new ThreadManager("progresse");
	public static final ThreadManager DOWNLOAD = new ThreadManager("download");
	public static final ThreadManager SPLASH = new ThreadManager("splash");
	public static final ThreadManager SYSTEM = new ThreadManager("system");
	
	
	private static String oldNameStatic;
	
	private String name;
	private String oldName;
	
	private Thread thread;
	
	public ThreadManager(String name) {
		this.name = name;
	}
	
	public void start(Runnable run) {
		start(new Thread(run));
	}
	
	public void start(Thread run) {		
		this.thread = run;
		
		thread.setName(name);
		thread.start();
		
		addThread(thread);
	}
	
	public static Thread startThread(Thread run) {
		Thread th = new Thread(run);
		th.start();
		return th;
	}
	
	public static Thread createThread(String name, Runnable run) {
		Thread th  = new Thread(run);
		if(name != null)
			th.setName(name);
		
		return th;
	}
	
	public static Thread createThread(Runnable run) {
		return createThread(null, run);
	}
	
	public void setTemporaryName() {oldName = currentThread().getName(); currentThread().setName(name);}
	public void restorOldParameter() {currentThread().setName(oldName);}
	
	public static void setTemporaryName(String name) {oldNameStatic = currentThread().getName(); currentThread().setName(name);}
	public static void restorOldParameter(int nul) {currentThread().setName(oldNameStatic);}
	
	public static void addThread(Thread thread) {threads.add(thread);}
	public static void addThread(ThreadManager thread) {addThread(new Thread(thread.name));}
	
	public static void sleep(long millis) {try {Thread.sleep(millis);}catch(InterruptedException e) {e.printStackTrace();}}
	
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
	
	public Thread getThread() {return thread;}
	
	public String getName() {return name;}
	
	@SuppressWarnings("deprecation")
	public static void stop(Thread thread) {
		threads.remove(thread);
		
		thread.stop();
	}
	
	public static int getTheadNumber() {return threads.size();}
	
}
