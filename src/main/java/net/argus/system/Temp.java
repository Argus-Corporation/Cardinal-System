package net.argus.system;

import java.util.Random;

public class Temp {
	
	private static String tempDir;
	
	public static String getTempDir() {
		if(tempDir == null) {
			tempDir = System.getProperty("deployment.user.cachedir");
			
			String projName = System.getProperty("project.name")!=null?System.getProperty("project.name"):"cardinal";
			
			if(tempDir == null || System.getProperty("os.name").startsWith("Win"))
				tempDir = System.getProperty("java.io.tmpdir");
			tempDir = String.valueOf(tempDir) + "/" + projName + (new Random()).nextInt();
		}
		return tempDir;
	}

}
