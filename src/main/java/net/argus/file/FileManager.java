package net.argus.file;

import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;
import net.argus.util.Identifiant;

public class FileManager {
	
	public static String getMainPath() {
		String mainPath = System.getProperty("user.dir");
		if(System.getProperty("id").equals(Identifiant.DEV.getId())) mainPath = mainPath.substring(0, mainPath.lastIndexOf('\\'));
		return mainPath;
	}
	
	public static String getPath(String res) {
		String mainPath = getMainPath();
		String path = "";
		
		if(res == null) res = "";
		
		path = mainPath + "\\" + res;
		
		return path;
	}
	
	public static String getPathInJar(String res) {
		String path = "";
		if(res == null) res = "";
		
		path = AbstractFileSave.regulary(new FileManager().getClass().getResource("/" + res).getPath(), null);
	
		return path;
	}
	
	public static void main(String[] args) {
		InitializedSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		
		System.out.println(getPath("images/bar.png"));
	}

}
