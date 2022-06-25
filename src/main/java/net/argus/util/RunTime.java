package net.argus.util;

public class RunTime {
	
	private long start;
	
	private static RunTime runTime = new RunTime();
	
	public long getTime() {
		return System.currentTimeMillis();
	}
	
	public void start() {start = getTime();}
	
	public long stop() {
		long time = getTime();
		return time - start;
	}
	
	public static RunTime getRunTime() {return runTime;}	

}
