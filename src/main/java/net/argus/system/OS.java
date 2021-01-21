package net.argus.system;

public enum OS {
	
	WINDOWS, LINUX;
	
	public static OS getOS() {
		String os = System.getProperty("os.name");
		
		if(os.startsWith("Win")) return WINDOWS;
		else if(os.startsWith("Linux")) return LINUX;
					
		return null;
	}

}
