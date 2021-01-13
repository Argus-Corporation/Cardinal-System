package net.argus.system;

import java.util.Random;

import net.argus.Cardinal;

public class Temp {
	
	private static String tempDir;
	
	public static String getTempDir() {
		if(tempDir == null) {
			if(System.getProperty("temp") == null) {
				tempDir = System.getProperty("deployment.user.cachedir");
				
				String name = System.getProperty("name")!=null?System.getProperty("name"):Cardinal.NAME;
				
				if(tempDir == null || System.getProperty("os.name").startsWith("Win"))
					tempDir = System.getProperty("java.io.tmpdir");
				tempDir = String.valueOf(tempDir) + "/" + name + (new Random()).nextInt();
			}else {
				tempDir = System.getProperty("temp") + "/";
			}
		}
		return tempDir;
	}

}
