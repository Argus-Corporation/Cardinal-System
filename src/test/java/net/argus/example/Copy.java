package net.argus.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import net.argus.util.ArrayManager;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class Copy {
	
	private static float copied = 0;
	private static float size;
	
	public static void main(String[] args) {
		
		String[] exeptedPath = new String[] {/*"12 - Brel Parle (Interview 1971 À Knokke).mp3"*/};
		String defRep = "E:\\Jacques Brel - Knokke(1963)";
		String extAuth = "mp3";
		String pathCopy = "F:\\Jacques Brel\\Jacques Brel - Knokke(1963)\\";
						
		List<File> files = index(defRep, extAuth, exeptedPath);
		size = files.size();
		Debug.log("Start copying " + size + " file(s)");
		copys(files, pathCopy, defRep);
	}
	
	public static List<File> index(String defRep, String extAuth, String[] exeptedPath) {
		List<File> files = new ArrayList<File>();
		File file = new File(defRep);
		for(File f : file.listFiles()) {
			if(!accept(f.getPath(), exeptedPath))
				continue;
			
			if(f.isDirectory())
				ArrayManager.add(files, index(f.getAbsolutePath(), extAuth, exeptedPath));
			else if(f.getName().toUpperCase().endsWith(("." + extAuth).toUpperCase()))
				files.add(f);
				
		}
		
		return files;
	}
	
	public static boolean accept(String path, String[] exeptedPath) {
		for(String e : exeptedPath)
			if(path.toUpperCase().contains(e.toUpperCase()))
				return false;
		return true;
	}
	
	public static void copys(List<File> files, String pathCopy, String defRep) {
		for(File f : files) {
			while(Thread.activeCount() > 5)
				ThreadManager.sleep(1000);
			
			ThreadManager.startThread(new Thread(copy(f, pathCopy, defRep)));
		}
	}
	
	public static Runnable copy(File file, String pathCopy, String defRep) {
		return () -> {
			Path copied = Paths.get(pathCopy + file.getPath().substring(defRep.length()));
			Path original = file.toPath();

			try {
				if(!Files.exists(copied)) {
					Files.createDirectories(copied.getParent());
					Files.createFile(copied);
				}
				
				Files.copy(original, copied, StandardCopyOption.REPLACE_EXISTING);
				Copy.copied++;
				Debug.log((Copy.copied / size) * 100f + "% || File " + original + " is copied to + " + copied);
			}catch(IOException e) {
				Debug.log("File " + original + " encountered an error", Info.ERROR);
				e.printStackTrace();
			}
		};
	}
	
}
