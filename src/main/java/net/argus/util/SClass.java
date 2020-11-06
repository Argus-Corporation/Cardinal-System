package net.argus.util;

import java.io.File;

import net.argus.exception.ClassPathException;
import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;

public class SClass {
	
	public static final int OUT_JAR = 0;
	public static final int IN_JAR = 1;
	
	private String path;
	private File filePath;
	
	private SClass(String res, int id) throws ClassPathException {
		switch(id) {
			case OUT_JAR:
				this.path = System.getProperty("user.dir");
				if(System.getProperty("id").equals(Identifiant.DEV.getId())) this.path = path.substring(0, path.lastIndexOf(System.getProperty("project.name")));
				else if(System.getProperty("id").equals(Identifiant.JAR.getId())) this.path = path + "\\";
				
				if(res == null) res = "";
				this.path = path + res;
				this.filePath = new File(path);
				break;
				
			case IN_JAR:
				if(res == null) res = "";
				
				this.path = System.getProperty("user.dir");
				if(System.getProperty("id").equals(Identifiant.DEV.getId())) {
					this.path += "\\bin" + res;
					
				
				}else if(System.getProperty("id").equals(Identifiant.JAR.getId())) {
					this.path = path + "\\" + System.getProperty("project.name") + ".jar!" + res;
				}
				
				break;
				
			default:
				throw new ClassPathException("fatal error in " + Thread.currentThread());
		}
	}	
	
	public static String getPath(String res) {try{return new SClass(res, OUT_JAR).path;}catch(ClassPathException e){e.printStackTrace();return null;}}
	public static File getFilePath(String res) {try{return new SClass(res, OUT_JAR).filePath;}catch(ClassPathException e){e.printStackTrace();return null;}}
	
	public static String getPathJar(String res) {try{return new SClass(res, IN_JAR).path;}catch(ClassPathException e){e.printStackTrace();return null;}}
	public static File getFilePathJar(String res) {try{return new SClass(res, IN_JAR).filePath;}catch(ClassPathException e){e.printStackTrace();return null;}}
	
	public static void main(String[] args) {
		InitializedSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		System.out.println(SClass.class.getResource("/assets/images/google.png"));
		File f = new File(getPathJar("assets/images/google.png"));
		System.out.println(f);
		System.out.println(f.exists());
	}
	

}
