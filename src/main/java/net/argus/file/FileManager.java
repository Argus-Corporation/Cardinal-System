package net.argus.file;

import java.io.File;
import java.net.URISyntaxException;

import net.argus.Cardinal;
import net.argus.system.UserSystem;
import net.argus.util.Identifiant;

public class FileManager {
	
	public static String getMainPath() {
		String mainPath = System.getProperty("user.dir");
		
		String id = UserSystem.getProperty("id");
		if(id != null && id.equals(Identifiant.DEV.getId())) mainPath = mainPath.substring(0, mainPath.lastIndexOf('\\'));
		return mainPath;
	}
	
	public static String getPath(String res) {
		String mainPath = getMainPath();
		String path = "";
		
		if(res == null) res = "";
		
		path = mainPath + "/" + res;
		
		return path;
	}
	
	public static String getPathInJar(String res) {
		String path = "";
		if(res == null) res = "";
		
		path = CardinalFile.valueOf(new FileManager().getClass().getResource("/" + res).getPath());
	
		return path;
	}
	
	public static File getCodeSourceLocation() {
		try {return new File(Cardinal.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		}catch(URISyntaxException e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public static String getFileName(File file) {
		if(file == null)
			return null;
		
		int index = file.getName().lastIndexOf('.');
		
		if(index == -1)
			return file.getName();
		
		return file.getName().substring(0, index);
	}
	
	public static String getFileSuffix(File file) {
		if(file == null)
			return null;
		
		int index = file.getName().lastIndexOf('.');
		
		if(index == -1)
			return file.getName();
		
		return file.getName().substring(index + 1);
	}
	
	public static boolean isExists(String path) {
		return new File(path).exists();
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
