package net.argus.file.css;

import java.io.File;

public class CSSEngine {
	
	private CSSFile css;

	public CSSEngine(String fileName, String rep) {
		css = new CSSFile(fileName, rep);
		css.execut();
	}
	
	public static void run(String fileName, File path) {
		new CSSFile(fileName, path).execut();
	}
	
	public static void run(String fileName, String rep) {
		new CSSFile(fileName, rep).execut();
	}
	
	

}
