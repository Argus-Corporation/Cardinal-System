package net.argus.util;

import net.argus.system.UserSystem;

public class RunTime {
	
	private long start;
	
	private static RunTime runTime = new RunTime();
	
	public native long getTime();
	
	public void start() {start = getTime();}
	
	public long stop() {
		long time = getTime();
		return time - start;
	}
	
	public static RunTime getRunTime() {return runTime;}
	
	static {UserSystem.loadLibrary("runtime");}
	

}
