package net.argus.file.css;

import java.io.FileNotFoundException;

public class CSSEngine {
	
	private CSSFile css;

	public CSSEngine(String fileName, String rep) throws FileNotFoundException {
		css = new CSSFile(fileName, rep);
		css.execut();
	}
	
	public static void engine(String fileName, String rep) throws FileNotFoundException {
		new CSSFile(fileName, rep).execut();
	}
	
	

}
