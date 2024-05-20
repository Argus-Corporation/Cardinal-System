package net.argus.system;

import java.io.File;

import net.argus.Cardinal;

public class Temp {
	
	private static String tempDir;
	
	public static String getTempDir() {
		if(tempDir == null) {
			String name = (System.getProperty("name")!=null&&!System.getProperty("name").isEmpty())?System.getProperty("name"):Cardinal.NAME;
			File file = new File("./temp-" + name.toLowerCase());
			if(file.exists())
				deleteDirectory(file);
			file.mkdirs();

			tempDir = file.getAbsolutePath();
		}
		return tempDir;
	}
	
	 public static void deleteDirectory(File directory) {
		 File[] files = directory.listFiles();
		 
		 if(files != null) 
			 for(File file : files) 
				 if(file.isDirectory()) 
					 deleteDirectory(file);
				 else
					 file.delete();
		 
		 directory.delete();
	 }

}
