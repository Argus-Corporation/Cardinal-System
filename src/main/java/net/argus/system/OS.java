package net.argus.system;

public enum OS {
	
	WINDOWS, LINUX, OSX;
	
	public static OS currentOS() {
		String os = System.getProperty("os.name").toUpperCase();
		
		if(os.startsWith("WIN")) return WINDOWS;
		else if(os.startsWith("LINUX")) return LINUX;
		else if(os.startsWith("MAC")) return OSX;
					
		return null;
	}

}
