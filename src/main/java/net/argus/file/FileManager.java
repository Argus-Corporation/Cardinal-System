package net.argus.file;

import java.io.File;

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
		
		path = mainPath + File.separator + res;
		
		return path;
	}
	
	public static String getPathInJar(String res) {
		String path = "";
		if(res == null) res = "";
		
		path = AbstractFileSave.regulary(new FileManager().getClass().getResource("/" + res).getPath());
	
		return path;
	}
	
	public static void delete(String path) {
		delete(new File(path));
	}
	
	public static void delete(File f) {
		if(f.isDirectory()){
			if(f.list().length == 0)
				f.delete();
			else{
				String files[] = f.list();
				
				for(String tmp : files) {
					File file = new File(f, tmp);
					delete(file);
				}
				
				if(f.list().length == 0)
					f.delete();
			}
		}else
			f.delete();
	}

}
