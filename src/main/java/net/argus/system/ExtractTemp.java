package net.argus.system;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ExtractTemp {
	
	private JarFile jarFile;
	private Enumeration<JarEntry> entities;
	
	public ExtractTemp() throws IOException, URISyntaxException {
		jarFile = new JarFile(new File(ExtractTemp.class.getProtectionDomain().getCodeSource().getLocation().toURI()), false);
		entities = jarFile.entries();
	}
	
	public void copy(List<String> fileNames) throws IOException {
		List<String> extentions = new ArrayList<String>();
		for(String name : fileNames)
			if(name.contains("*."))
				extentions.add(name.substring(name.lastIndexOf('.') + 1));
				
		while(entities.hasMoreElements()) {
			JarEntry entry = entities.nextElement();
	    	String fileJarName = entry.getName();
	    	String fileExtention = fileJarName.substring(fileJarName.lastIndexOf('.') + 1);
	    	
	    	if(fileNames.contains(fileJarName))
	    		copy(entry);
	    	else if(extentions.contains(fileExtention))
	    		copy(entry);
		}
	}
	
	public void copy(String fileName) throws IOException {
		List<String> file = new ArrayList<String>();
		file.add(fileName);
		copy(file);
	}
	
	public void copy(JarEntry entry) throws IOException {
		Copy.copy(jarFile.getInputStream(jarFile.getEntry(entry.getName())), new FileOutputStream(new File(getTempDir(entry.getName()))));
	}
	
	public static String getTempDir(String rep) {
		String tempDir = Temp.getTempDir() + "/";
		
		if(rep.lastIndexOf('/') != -1) {
			tempDir += "/" + rep.substring(0, rep.lastIndexOf('/'));
		}
		
		File dir = new File(tempDir);
		
		if(!dir.exists())
			dir.mkdirs(); 
		
		if(rep.lastIndexOf('/') != -1)
			dir = new File(tempDir + "/" + rep.substring(rep.lastIndexOf('/') + 1));
		else
			dir = new File(tempDir + "/" + rep);
		
		return dir.getPath();
	}
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		InitializationSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		List<String> fileName = new ArrayList<String>();
		fileName.add("*.class");
		
		new ExtractTemp().copy(fileName);
	}

}
