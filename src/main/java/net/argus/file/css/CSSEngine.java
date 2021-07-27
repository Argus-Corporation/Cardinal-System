package net.argus.file.css;

import java.io.File;

import net.argus.instance.Instance;

public class CSSEngine {
	
	public static void run(File path) {
		new CSSFile(path).execut();
	}
	
	public static void run(String fileName, String rep, Instance instance) {
		new CSSFile(fileName, rep, instance).execut();
	}
	
	public static void run(String fileName, String rep) {
		new CSSFile(fileName, rep).execut();
	}
	
	

}
