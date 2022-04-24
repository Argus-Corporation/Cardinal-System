package net.argus;

import net.argus.util.Version;

public final class Cardinal {
	
	public static final Version VERSION = new Version("1.5.3b");
	public static final String NAME = "Cardinal-System";
	
	public static final String WEB = "https://argus.alwaysdata.net/";
	public static final String GIT = "https://github.com/Argus-corporation/Cardinal-System/";
	
	public static void printInfo() {
		System.out.println(NAME + " version " + VERSION);
	}
	
}
